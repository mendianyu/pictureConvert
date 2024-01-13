package 图像转换;

import java.net.URLEncoder;

/**
 * 图像去噪
 */
public class Demo
{


    public static String demo()
    {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/denoise";
        try
        {
            // 本地文件路径
            String filePath = "/Users/mendianyu/软件构造照片素材/照片素材/kun.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "option=" + "0" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.11bf0aeb3debfee1212eb74bb15ad01a.2592000.1703921333.282335-44036401";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args)
//    {
//        Demo.demo();
//    }
}