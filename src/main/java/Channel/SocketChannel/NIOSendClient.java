package Channel.SocketChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: HuangSiBo
 * @Description: SocketChannel套接字通道，用于TCP连接的数据读写
 * @Data: Created in 21:38 2022/4/5
 */
public class NIOSendClient {

    private Charset charset = StandardCharsets.UTF_8;
    private String IP = "127.0.0.1";
    private int port = 8080;

    public void sendFile(String sourcePath, String destPath) throws IOException {
        File file = new File(sourcePath);
        if(!file.exists()){
            System.out.println("源文件不存在");
            return;
        }

        FileChannel fileChannel = new FileInputStream(file).getChannel();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.socket().connect(new InetSocketAddress(IP, port));
        //非阻塞模式
        socketChannel.configureBlocking(false);
        while ( !socketChannel.finishConnect() ){
            //不断的自旋、等待，或者做一些其他的事情
        }
        System.out.println("Client连接成功");

        ByteBuffer fileNameByteBuffer = charset.encode(destPath);
        socketChannel.write(fileNameByteBuffer);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putLong(file.length());

        System.out.println("开始传输文件");
        int len = 0;
        long progress = 0;
        while ((len=fileChannel.read(buffer)) > 0){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            progress += len;
            System.out.println("| " + (100 * progress)/file.length() + "% |");
        }

        if(len == -1){
            fileChannel.close();
            socketChannel.shutdownOutput();
            socketChannel.close();
        }
        System.out.println("-----文件传输成功-----");
    }

    public static void main(String[] args) {
        String sourcePath = "D:\\项目\\java-nio\\resource\\sourceFile.txt";
        String destPath =

    }
}
