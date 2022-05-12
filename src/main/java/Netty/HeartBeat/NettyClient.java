package Netty.HeartBeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 16:44 2022/5/12
 */
public class NettyClient {
    private final String host;
    private final int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加解码器和编码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            System.out.println("-------" + channel.localAddress()+ "--------");
            Scanner sc = new Scanner(System.in);
            //发送用户消息
            while (sc.hasNext()){
                channel.writeAndFlush(sc.nextLine() + "\r\n");
            }
        }
        finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient("127.0.0.1", 7000).run();
    }
}
