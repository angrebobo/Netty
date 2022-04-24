package Buffer;

import java.nio.IntBuffer;

/**
 * @Description Buffer的使用
 * @Author HuangSiBo
 * @Date 2022/2/13 18:25
 **/
public class BufferTest {

    public static void main(String[] args) {
         IntBuffer intBuffer = IntBuffer.allocate(20);

        //使用allocate()方法，创建一个Buffer类的实例对象。
        System.out.println("-------------after allocate----------------");
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());


        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        System.out.println("-------------after put----------------");
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());

        //调用Buffer.flip()方法，将缓冲区转换为读模式
        intBuffer.flip();
        System.out.println("-------------after flip----------------");
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());

//        intBuffer.clear();
//        System.out.println("-------------after clear----------------");
//        System.out.println("position: " + intBuffer.position());
//        System.out.println("limit: " + intBuffer.limit());
//        System.out.println("capacity: " + intBuffer.capacity());

        System.out.println("-------------after get 5 int----------------");
        System.out.print("get: ");
        for (int i = 0; i < 5; i++) {
            if(i == 2) intBuffer.mark();

            int j = intBuffer.get();
            System.out.print(j + " ");
        }
        System.out.println();
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());

        //reset和mark是配套使用的
        System.out.println("-------------after reset----------------");
        intBuffer.reset();
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());

        //clear后，缓冲区进入写入模式
        System.out.println("-------------after clear----------------");
        intBuffer.clear();
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());

    }
}


