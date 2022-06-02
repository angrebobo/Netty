import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: HuangSiBo
 * @Description:
 * 服务端 pipeline 触发的原始流程，图中数字代表了处理步骤的先后次序
 * @Data: Created in 22:01 2022/6/2
 */
public class Server {
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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                                @Override
                                protected void messageReceived(ChannelHandlerContext ctx, Object msg) {
                                    System.out.println(1);
//                                    ctx.fireChannelRead(msg);
                                }
                            });
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                                @Override
                                protected void messageReceived(ChannelHandlerContext ctx, Object msg) {
                                    System.out.println(2);
                                    ctx.fireChannelRead(msg); // 2
                                }
                            });
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                                @Override
                                protected void messageReceived(ChannelHandlerContext ctx, Object msg) {
                                    System.out.println(3);
                                    ctx.channel().write(msg);
                                }
                            });
                            ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg,
                                                  ChannelPromise promise) {
                                    System.out.println(4);
                                    ctx.write(msg, promise); // 4
                                }
                            });
                            ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg,
                                                  ChannelPromise promise) {
                                    System.out.println(5);
                                    ctx.write(msg, promise); // 5
                                }
                            });
                            ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg,
                                                  ChannelPromise promise) {
                                    System.out.println(6);
                                    ctx.write(msg, promise); // 6
                                }
                            });
                        }
                    });

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
