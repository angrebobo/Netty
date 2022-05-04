package BIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 20:42 2022/5/4
 */
public class Client {
    public static void main(String[] args) throws IOException {
        String IP = "127.0.0.1";
        int port = 8080;
        SocketChannel socketChannel = SocketChannel.open();
        try {
            socketChannel.socket().connect(new InetSocketAddress(IP, port));
            //非阻塞模式
            socketChannel.configureBlocking(false);
            while ( !socketChannel.finishConnect() ){
                //不断的自旋、等待，或者做一些其他的事情
            }
            System.out.println("Client连接成功");

            socketChannel.write(StandardCharsets.UTF_8.encode("我是客户端" + socketChannel.hashCode()));
        }
        finally {
            socketChannel.shutdownOutput();
            socketChannel.close();
        }
    }
}
