package 图像转换;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class test extends JFrame
{
    private JLabel originalImageLabel, convertedImageLabel, label1, label2;
    private BufferedImage convertedImage;
    //图片的地址
    private String filePath;
    private String url;

    public test()
    {
        // 设置窗口属性
        super("图片特效工具");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(570, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        // 设置布局管理器
        setLayout(null);

        // 创建选择文件按钮
        JButton chooseFileButton = new JButton("选择并上传图片");
        chooseFileButton.setBounds(95, 20, 100, 20);

        label1 = new JLabel("原始图片");
        label1.setBounds(120, 380, 100, 20);
        // 选择文件按钮的 ActionListener
        chooseFileButton.addActionListener(e -> {
            // 打开文件选择对话框
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(test.this);

            if (result == JFileChooser.APPROVE_OPTION)
            {
                // 获取用户选择的文件
                File selectedFile = fileChooser.getSelectedFile();

                // 获取选择的文件的完整地址
                filePath = selectedFile.getAbsolutePath();
                try
                {
                    // 读取图像文件
                    BufferedImage originalImage = ImageIO.read(selectedFile);

                    // 缩放图像
                    Image scaledImage = pictureResize(originalImage);

                    // 显示缩放后的图像
                    originalImageLabel.setIcon(new ImageIcon(scaledImage));
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });


        url = "https://aip.baidubce.com/rest/2.0/image-process/v1/denoise";
        // 创建转换按钮
        JButton convertButton = new JButton("转换图片");
        convertButton.setBounds(380, 20, 100, 20);

        label2 = new JLabel("变化后的照片");
        label2.setBounds(395, 380, 100, 20);
        convertButton.addActionListener(e -> {
            //根据选中获取对应链接


            //这行是关键，调用Base64ToPicture类的方法，实现base64编码解码为图片
            ByteArrayInputStream picture = Base64ToPicture.convertToPicture(url, filePath);
            try
            {
                //获取转换后的图像
                convertedImage = ImageIO.read(picture);

                // 缩放图像
                Image scaledImage = pictureResize(convertedImage);

                //显示转换后的图像
                convertedImageLabel.setIcon(new ImageIcon(scaledImage));
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });

        // 创建标签用于显示原始图像
        originalImageLabel = new JLabel();
        originalImageLabel.setBounds(20, 60, 300, 300);
        // 创建标签用于显示转换后的图像
        convertedImageLabel = new JLabel();
        convertedImageLabel.setBounds(300, 60, 300, 300);

        JButton saveButton = new JButton("保存");
        saveButton.setBounds(480, 380, 50, 20);
        saveButton.addActionListener(e -> {
            //实现图片保存
            saveImage();
        });

        add(originalImageLabel);
        add(convertedImageLabel);
        add(label1);
        add(label2);
        add(saveButton);
        add(chooseFileButton);
        add(convertButton);
    }

    /**
     * 实现图片的缩放，让图片显示在指定大小的空间内
     */
    public Image pictureResize(BufferedImage image)
    {
        // 获取原始图像的尺寸
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        // 获取目标框体的尺寸
        int targetWidth = originalImageLabel.getWidth();
        int targetHeight = originalImageLabel.getHeight();

        // 计算缩放比例，确保图像在框体内显示
        double scaleX = (double) targetWidth / originalWidth;
        double scaleY = (double) targetHeight / originalHeight;
        double scale = Math.min(scaleX, scaleY);

        // 缩放图像
        Image scaledImage = image.getScaledInstance(
                (int) (originalWidth * scale),
                (int) (originalHeight * scale),
                Image.SCALE_SMOOTH
        );
        return scaledImage;
    }

    /**
     * 实现保存图片，可指定保存的名称和路径，格式固定为jpg
     */
    private void saveImage()
    {
        if (convertedImage != null)
        {
            // 创建文件选择器
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);

            // 处理文件选择结果
            if (result == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fileChooser.getSelectedFile();

                // 检查文件名是否以 ".jpg" 结尾，如果不是，则添加后缀
                String fileName = selectedFile.getName();
                if (!fileName.toLowerCase().endsWith(".jpg"))
                {
                    fileName += ".jpg";
                }

                // 构建新的文件对象
                File newFile = new File(selectedFile.getParentFile(), fileName);

                try
                {
                    // 保存图片到指定文件
                    ImageIO.write(convertedImage, "jpg", newFile);
                    JOptionPane.showMessageDialog(this, "图片保存成功");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "图片保存失败");
                }
            }
        }
    }

//    public static void main(String[] args)
//    {
//        SwingUtilities.invokeLater(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                new test();
//            }
//        });
//    }
}
