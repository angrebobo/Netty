package Netty.HTTP;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: HuangSiBo
 *
 * 用Netty实现一个简单的服务器
 * 运行NettyServer类的main方法，开启服务器
 * 然后在浏览器访问 http://localhost:8081/
 *
 * @Data: Created in 15:41 2022/5/3
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        //创建两个线程组,BossGroup 和 WorkerGroup
        //BossGroup负责处理连接请求，业务处理交给workerGroup
        //如果初始化不传入子线程参数，默认就是系统的核心线程数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        int port = 8081;

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    //childHandler表示给workerGroup设置初始化方法，即childHandler会在客户端成功connect后才执行
                    //Handler表示给bossGroup设置初始化方法，Handler在初始化时就会执行
                    .childHandler(new ServerInitializer());

            System.out.println(".....服务器 is ready...");

            //绑定端口，sync()表明是异步方法
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //添加监听器
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess())
                    System.out.println("监听端口 " + port + " 成功");
                else
                    System.out.println("监听端口 " + port + " 失败");
            });

            channelFuture.channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
