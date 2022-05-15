package Netty.Future;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * @author: HuangSiBo
 * @Description:
 * 异步任务的执行顺序：
 * 主线程执行代码时，遇到异步任务，让异步任务自己去执行，主线程继续执行接下来的代码，
 * 异步任务经过一段时间后执行完毕，会自动返回结果
 *
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

        //异步任务，设置监听器，任务执行完会自动返回结果
        future.addListener(future1 -> System.out.println(Thread.currentThread().getName() + " 结果是: " + future.getNow() + " 时间是" + new Date()));
        System.out.println("我在这");
        Thread.sleep(3000);
    }
}
