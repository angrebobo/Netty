package Netty.StickyPacket.解决方案.定长解码器;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Arrays;
import java.util.Random;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 20:40 2022/5/15
 */
public class Client {

    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        send();
        System.out.println("finish");
    }

    public static byte[] fill10Bytes(char c, int len) {
        byte[] bytes = new byte[10];
        Arrays.fill(bytes, (byte) '_');
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) c;
        }
        System.out.println(new String(bytes));
        return bytes;
    }

    private void send() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) {
                                    ch.pipeline().addLast(new ChannelHandlerAdapter(){
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) {
                                            ByteBuf buf = ctx.alloc().buffer();
                                            char c = '0';
                                            Random r = new Random();
                                            for (int i = 0; i < 10; i++) {
                                                byte[] bytes = fill10Bytes(c, r.nextInt(10) + 1);
                                                c++;
                                                buf.writeBytes(bytes);
                                            }
                                            ctx.writeAndFlush(buf);
                                        }
                                    });
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Client("127.0.0.1", 7000).run();
    }
}
