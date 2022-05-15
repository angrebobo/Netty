package Netty.Future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 20:29 2022/5/14
 */
public class JdkFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Object> future = service.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "执行计算");
            Thread.sleep(5000);
            return 50;
        });

        System.out.println("等待结果");
        System.out.println(Thread.currentThread().getName() + " 结果是: " + future.get());
    }
}
