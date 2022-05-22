package Netty.StickyPacket.粘包和半包现象;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) {
                                    ch.pipeline().addLast(new ChannelHandlerAdapter(){
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) {
                                            //粘包
                                            for (int i = 0; i < 10; i++) {
                                                ByteBuf buffer = ctx.alloc().buffer();
                                                buffer.writeBytes(new byte[]{0,1,2,3,4,5,6,7,8,9});
                                                ctx.writeAndFlush(buffer);
                                            }

                                            //半包
//                                            ByteBuf buffer = ctx.alloc().buffer();
//                                            buffer.writeBytes(new byte[100]);
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
