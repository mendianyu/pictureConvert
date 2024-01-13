package 图像转换;

import com.alibaba.fastjson2.JSONObject;

import java.net.URLEncoder;

/**
 * 人像动漫化
 */
public class GetBase64Str
{

    public static String getBase64Str(String url, String filePath)
    {
        // 人物动漫化请求url
        //String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime";
        // 图片去雾url
        //String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/dehaze";
        try
        {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            System.out.println("imgStr:" + imgStr);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;
            System.out.println(param);
            String accessToken = "24.3c7f83c8e945304e8f12a84aaa4f1169.2592000.1707725433.282335-44036";


            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = com.alibaba.fastjson2.JSON.parseObject(result);
            String base64Str = "";
            if (jsonObject.getString("image") != null)
            {
                base64Str = jsonObject.getString("image");
            } else if (jsonObject.getString("result") != null)
            {
                base64Str = jsonObject.getString("result");
            } else if (jsonObject.getString("image_processed") != null)
            {
                base64Str = jsonObject.getString("image_processed");
            }
            //System.out.println(base64Str);
            return base64Str;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


//    public static void main(String[] args)
//    {
//        System.out.println("result:" + GetBase64Str.getBase64Str("https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime", "/Users/mendianyu/软件构造照片素材/照片素材/kun.jpg"));
//    }
}