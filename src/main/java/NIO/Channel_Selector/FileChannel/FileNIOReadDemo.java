package NIO.Channel_Selector.FileChannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: HuangSiBo
 * @Description: NIO读取文本
 * @Data: Created in 15:08 2022/4/25
 */
public class FileNIOReadDemo {
    public static final int CAPACITY = 1024;

    public static void main(String[] args) throws IOException {
        String sourcePath  = "/D:/项目/java-nio/resource/sourceFile.txt";
        readSourceFile(sourcePath);
    }

    public static void readSourceFile(String sourcePath) throws IOException {
        System.out.println("readPath: " + sourcePath);

        RandomAccessFile randomAccessFile = new RandomAccessFile(sourcePath, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer =ByteBuffer.allocate(CAPACITY);
        int length;

        while ((length=channel.read(buffer)) != -1){
            buffer.flip();
            byte[] bytes = buffer.array();
            String s = new String(bytes);
            System.out.println(s);
        }

        channel.close();
        randomAccessFile.close();
    }
}
