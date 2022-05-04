package Netty.HTTP;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 17:17 2022/5/3
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast("encoder", new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler()); //加入自己的处理器
                        }
                    });
            System.out.println("客户端 ok");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8081).sync();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if(channelFuture.isSuccess())
                    System.out.println("客户端" + channelFuture.hashCode() + "已成功连接到服务器");
                else
                    System.out.println("客户端连接失败");
            });

            channelFuture.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
