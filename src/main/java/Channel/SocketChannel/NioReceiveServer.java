package Channel.SocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author: HuangSiBo
 * @Description: 文件传输，Server端
 * @Data: Created in 17:44 2022/4/6
 */
public class NioReceiveServer {

    /**
     * @Description 服务器端保存的客户对象
     */
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
        FileChannel fileChannel;
    }

    private Charset charset = StandardCharsets.UTF_8;
    private ByteBuffer buffer = ByteBuffer.allocate(10240);
    HashMap<SelectableChannel, Client> map = new HashMap<>();

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
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

    }


}
