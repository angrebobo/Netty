package NIO.Channel.FileChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: HuangSiBo
 * @Description: FileChannel文件通道，用于文件的数据读写
 * @Data: Created in 21:38 2022/4/5
 */
public class FileNIOCopyDemo {

    /**
     * @Description NIO方式复制文件
     * @author
     * @date 2022/4/5 22:17
     */
    public static void nioCopyFile(String sourcePath, String destPath){
        File srcFile = new File(sourcePath);
        File destFile = new File(destPath);

        try {
            //如果目标文件不存在，新建一个
            if(!destFile.exists()){
                destFile.createNewFile();
            }

            long startTime = System.currentTimeMillis();

            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;

            try {
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                inChannel = fis.getChannel();
                outChannel = fos.getChannel();
                int length = -1;
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                //通道从文件读取数据，写入到buffer中
                while ((length=inChannel.read(buffer)) != -1){
                    //切换buffer状态，变成读模式
                    buffer.flip();

                    int outlength;

                    //通道从buffer读入数据，写入到文件中
                    while ((outlength=outChannel.write(buffer)) != 0){
                        System.out.println("写入的字节数" + outlength);
                    }

                    //切换buffer模式，变成写模式
                    buffer.clear();
                }
                //强制刷新到磁盘
                outChannel.force(true);
            }
            finally {
                fis.close();
                fos.close();
                inChannel.close();
                outChannel.close();
            }

            long endTime = System.currentTimeMillis();
            System.out.println("复制文件花费时间: " + (endTime-startTime) + "ms");
        }
        catch (Exception e){
            System.out.println("ERROR: 文件复制出错");
        }
    }

    public static void main(String[] args) {
        String sourcePath = "D:\\项目\\java-nio\\resource\\sourceFile.txt";
        String destPath = "D:\\项目\\java-nio\\resource\\destFile.txt";

        nioCopyFile(sourcePath, destPath);
    }
}
