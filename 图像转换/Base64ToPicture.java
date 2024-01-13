package 图像转换;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * @author mendianyu
 */
public class Base64ToPicture
{

    public static ByteArrayInputStream convertToPicture(String url, String filePath)
    {
        //调用getBase64Str方法将图片解析为base64编码
        String base64String = GetBase64Str.getBase64Str(url, filePath);
        // 解码Base64字符串为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64String);

        // 创建 ByteArrayInputStream
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return bis;
        // 读取字节数组为 BufferedImage
        // BufferedImage image = ImageIO.read(bis);

        // 保存图像到文件
        //File output = new File("decoded_image.png");
        //ImageIO.write(image, "png", output);
        //System.out.println("图像已解码并保存为 decoded_image.png");
    }

//    public static void main(String[] args)
//    {//测试
//        //convertToPicture("/Users/mendianyu/Desktop/WechatIMG1675.jpg");
//    }
}
