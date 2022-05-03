package NIO.Channel.FileChannel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author: HuangSiBo
 * @Description: 列出目录下的子目录及子文件
 * @Data: Created in 23:29 2022/4/23
 */
public class DirListFile {

    public static String getResourcePath(String resName) {
        URL url = DirListFile.class.getResource(resName);
        String path = url.getPath();
        String decodePath = null;
        try
        {
            decodePath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return decodePath;
    }

    public static void listFile(String path) throws IOException {
        if(path==null)
            return;
        File file = new File(path);
        if(file.isFile()){
            System.out.println("File: " + file.getCanonicalPath());
        }
        else if(file.isDirectory()){
            System.out.println("Directory: " + file.getCanonicalPath());

            File[] subFiles = file.listFiles();
            if(subFiles == null)
                return;

            for(File file1 : subFiles)
                listFile(file1.getCanonicalPath());
        }
        else {
            System.out.println("Error: " + file.getCanonicalPath());
        }
    }

    public static void main(String[] args) throws IOException {
        String path = getResourcePath("");
        listFile(path);
    }
}
