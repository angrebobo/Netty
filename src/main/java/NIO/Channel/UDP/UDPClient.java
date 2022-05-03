package NIO.Channel.UDP;

import Utils.DateUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class UDPClient {

    /**
     * Java NIO 中的 DatagramChannel 是一个能收发 UDP 包的通道。
     * 因为 UDP 是无连接的网络协议，所以不能像其它通道那样读取和写入。
     * 它发送和接收的是数据包
     */
    public void send() throws IOException {
        //操作一：获取DatagramChannel数据报通道
        DatagramChannel dChannel = DatagramChannel.open();
        dChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(10240);
        Scanner scanner = new Scanner(System.in);
        System.out.println("UDP 客户端启动成功！");
        System.out.println("请输入发送内容:");
        while ( scanner.hasNext() ) {
            String next = scanner.next();
            buffer.put((DateUtil.getNow() + " >>" + next).getBytes());
            buffer.flip();
            // 操作三：通过DatagramChannel数据报通道发送数据
            dChannel.send(buffer,
                    new InetSocketAddress("127.0.0.1",8080));
            buffer.clear();
        }
        //操作四：关闭DatagramChannel数据报通道
        dChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new UDPClient().send();
    }
}
