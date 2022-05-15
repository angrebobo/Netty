package Netty.Future;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * @author: HuangSiBo
 * @Description
 * @Data: Created in 21:01 2022/5/14
 */
public class NettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = eventLoopGroup.next();
        Future<Integer> future = eventLoop.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "执行计算" + " 时间是" + new Date());
            Thread.sleep(2000);
            return 50;
        });

        //异步任务，设置监听器
        future.addListener(future1 -> {
            //判断监听器的状态
            if(future1.isSuccess())
                System.out.println(Thread.currentThread().getName() + " 结果是: " + future.getNow() + " 时间是" + new Date());
        });
        System.out.println("我在这");
        Thread.sleep(3000);
    }
}
