import java.nio.IntBuffer;

/**
 * @Description TODO
 * @Author HuangSiBo
 * @Date 2022/2/13 18:25
 **/
public class BufferTest {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(20);

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

        intBuffer.flip();
        System.out.println("-------------after flip----------------");
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());

        intBuffer.clear();
        System.out.println("-------------after clear----------------");
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        System.out.println("capacity: " + intBuffer.capacity());
    }
}


