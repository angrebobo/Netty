package Netty.HTTP;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 15:41 2022/5/3
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        //创建两个线程组,BossGroup 和 WorkerGroup
        //BossGroup负责处理连接请求，业务处理交给workerGroup
        //如果初始化不传入子线程参数，默认就是系统的核心线程数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ServerInitializer()); //给我们的workerGroup 的 EventLoop 对应的管道设置处理器

            System.out.println(".....服务器 is ready...");

            //绑定端口，sync()表明是异步方法
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            //添加监听器
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (channelFuture.isSuccess())
                    System.out.println("监听端口 6668 成功");
                else
                    System.out.println("监听端口 6668 失败");
            });

            channelFuture.channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
