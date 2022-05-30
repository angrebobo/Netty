/**
 * @author: HuangSiBo
 * @Description:
 * 动态代理的实现
 * @Data: Created in 20:16 2022/5/28
 */

import java.lang.reflect.Proxy;

public class ProxyDemo {
    public static void main(String[] args) throws IllegalArgumentException {
        Vehicle car = new Car();

        Vehicle instance = (Vehicle) Proxy.newProxyInstance(car.getClass().getClassLoader(), car.getClass().getInterfaces(), (proxy, method, args1) -> {
            System.out.println("---------before-------");
            Object invoke = method.invoke(car, args1);
            System.out.println("---------after-------");
            return invoke;
        });

        instance.Run();
    }
}

interface Vehicle{
    void Run();
}

class Car implements Vehicle {
    @Override
    public void Run() {
        System.out.println("小汽车开起来了~~~");
    }
}
