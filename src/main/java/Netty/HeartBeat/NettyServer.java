package Netty.HeartBeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 15:24 2022/5/12
 */
public class NettyServer {

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            // IdleStateHandler是Netty提供的处理空闲的处理器,有4个参数
                            // long readerIdleTime: 设定IdleStateHandler心跳检测每*秒进行一次读检测，如果*秒内ChannelRead()方法未被调用则触发一次userEventTrigger()方法
                            // long writerIdleTime: 设定IdleStateHandler心跳检测每*秒进行一次写检测，如果*秒内write()方法未被调用则触发一次userEventTrigger()方法
                            // long allIdleTime: 设定IdleStateHandler心跳检测每*秒进行一次读写检测
                            // TimeUnit unit: 时间单位
                            // 当IdleStateEvent触发后,就会传递给管道的下一个handler去处理,
                            // 通过调用(触发)下一个handler的userEventTiggered,在该方法中去处理IdleStateEvent(读空闲，写空闲，读写空闲)
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println(".....服务器 is ready...");
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer(7000).run();
    }
}
