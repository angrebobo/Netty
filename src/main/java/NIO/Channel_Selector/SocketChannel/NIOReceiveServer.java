package NIO.Channel_Selector.SocketChannel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author: HuangSiBo
 * @Description: 文件传输，Server端
 * @Data: Created in 17:44 2022/4/6
 */
public class NIOReceiveServer {

    /**
     * @Description 服务器端保存的客户对象
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Client{
        //文件名称
        String fileName;
        //文件长度
        Long fileLength;
        //文件开始传输时间
        Long startTime;
        //客户端地址
        InetSocketAddress address;
        //输出的文件通道
        FileChannel outChannel;
    }

    private Charset charset = StandardCharsets.UTF_8;
    private ByteBuffer buffer = ByteBuffer.allocate(10240);
    HashMap<SelectableChannel, Client> clientMap = new HashMap<>();
    String path = "resource";

    //服务端开启服务
    public void startServer() throws IOException {
        //获取Selector连接器
        Selector selector = Selector.open();
        //获取通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //设置为非阻塞
        serverChannel.configureBlocking(false);
        //绑定链接
        serverChannel.bind( new InetSocketAddress(8080) );
        //将通道注册到选择器上，并注册IO事件为“接收”
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("serverChannel is listening...");
        //轮询感兴趣的IO就绪事件
        while (selector.select() > 0){
            //获取选择键集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();

                //若key是”新连接“事件,那就获取客户端连接
                if(key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    if(socketChannel == null) continue;
                    socketChannel.configureBlocking(false);
                    //将客户端连接注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    //记录下客户端
                    Client client = new Client();
                    client.setAddress( (InetSocketAddress) socketChannel.getRemoteAddress() );
                    clientMap.put(socketChannel, client);
                    System.out.println(socketChannel.getRemoteAddress() + "连接成功");
                }
                //若key是”可读“事件,那就处理客户的数据
                else if(key.isReadable()){
                    processData(key);
                }
                //如果不删除，下一次select()该事件还会被选中
                iterator.remove();
            }
        }
    }

    public void processData(SelectionKey key) throws IOException {
        Client client = clientMap.get( key.channel() );
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num;
        try {
            while ((num=socketChannel.read(buffer)) > 0){
                buffer.flip();

                if(client.fileName == null){
                    String fileName = charset.decode(buffer).toString();

                    File directory = new File(path);
                    if(!directory.exists()){
                        directory.mkdir();
                    }
                    client.setFileName( fileName );
                    String fullName = directory.getAbsolutePath() + File.separatorChar + fileName;
                    System.out.println("传输目标文件" + fullName);

                    File file = new File(fullName);
                    if(!file.exists()){
                        file.createNewFile();
                    }

                    client.setOutChannel( new FileOutputStream(file).getChannel() );
                }
                else if(client.fileLength == null){
                    client.setFileLength( buffer.getLong() );
                    client.startTime = System.currentTimeMillis();
                    System.out.println("文件开始传输");
                }
                else {
                    client.outChannel.write(buffer);
                }
                //切换到输入模式
                buffer.clear();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }

        //到达文件末尾
        if(num == -1){
            client.outChannel.close();
            System.out.println("上传完毕");
            key.cancel();
            System.out.println("文件接收成功,File Name：" + client.fileName);
            long endTime = System.currentTimeMillis();
            System.out.println("NIO IO 传输毫秒数：" + (endTime - client.startTime));
        }
    }

    public static void main(String[] args) throws IOException {
        NIOReceiveServer nioReceiveServer = new NIOReceiveServer();
        nioReceiveServer.startServer();
    }
}
