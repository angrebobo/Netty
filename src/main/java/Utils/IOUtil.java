package Utils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 15:29 2022/4/25
 */
public class IOUtil {

    /**
     * 取得当前类路径下的 resName资源的完整路径
     * url.getPath()获取到的路径被utf-8编码了
     * 需要用URLDecoder.decode(path, "UTF-8")解码
     *
     * @param resName 需要获取完整路径的资源,需要以/打头
     * @return 完整路径
     */
    public static String getResourcePath(String resName)
    {
        URL url = IOUtil.class.getResource(resName);
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
}
