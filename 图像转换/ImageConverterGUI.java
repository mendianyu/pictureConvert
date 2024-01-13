package 图像转换;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ImageConverterGUI extends JFrame
{
    private JLabel originalImageLabel, convertedImageLabel, label1, label2;
    private JRadioButton radio1, radio2;
    private JComboBox<String> comboBox1, comboBox2;
    private BufferedImage convertedImage;
    //图像的地址
    private String filePath;
    private String url;

    public ImageConverterGUI()
    {
        // 设置窗口属性
        super("图像增强与特效工具");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        // 设置布局管理器
        setLayout(null);


        //图像特效与图像增强选择
        radio1 = new JRadioButton("图像特效");
        radio1.setBounds(70, 10, 100, 20);
        comboBox1 = new JComboBox<>(new String[]{"人物动漫化", "黑白图像上色",});
        comboBox1.setBounds(150, 5, 145, 30);

        radio2 = new JRadioButton("图像增强");
        radio2.setBounds(70, 40, 100, 20);
        comboBox2 = new JComboBox<>(new String[]{"图像去雾", "图像清晰度增强", "图像去摩尔纹", "文档图像去底纹"});
        comboBox2.setBounds(150, 35, 145, 30);

        //创建按钮组，实现两个按钮只能选一个
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radio1);
        buttonGroup.add(radio2);

        // 设置单选按钮点击事件监听器
        radio1.addActionListener(e -> {
            // 当选择图像特效，激活 comboBox1，禁用 comboBox2
            comboBox1.setEnabled(true);
            comboBox2.setEnabled(false);
        });

        radio2.addActionListener(e -> {
            // 当选择图像增强时，激活 comboBox2，禁用 comboBox1
            comboBox1.setEnabled(false);
            comboBox2.setEnabled(true);
        });


        // 初始状态下选中第一个按钮，禁用第二个下拉框
        radio1.setSelected(true);
        comboBox2.setEnabled(false);


        // 创建选择文件按钮
        JButton chooseFileButton = new JButton("选择并上传图像");
        chooseFileButton.setBounds(340, 10, 100, 20);

        label1 = new JLabel("原始图像");
        label1.setBounds(120, 390, 100, 20);
        // 选择文件按钮的 ActionListener
        chooseFileButton.addActionListener(e -> {
            // 打开文件选择对话框
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(ImageConverterGUI.this);

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

        // 图像转换
        JButton convertButton = new JButton("转换图像");
        convertButton.setBounds(340, 40, 100, 20);

        label2 = new JLabel("变化后的照片");
        label2.setBounds(395, 390, 100, 20);
        convertButton.addActionListener(e -> {

            //根据选中的内容获取对应的链接
            url = "";
            if (radio1.isSelected())
            {
                String selected = (String) comboBox1.getSelectedItem();
                if (selected == "人物动漫化")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime";
                } else if (selected == "黑白图像上色")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/colourize";
                }
            } else if (radio2.isSelected())
            {
                String selected = (String) comboBox2.getSelectedItem();
                if (selected == "图像去雾")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/dehaze";
                } else if (selected == "拉伸图像修复")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/stretch_restore";
                } else if (selected == "图像清晰度增强")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/image_definition_enhance";
                } else if (selected == "图像去摩尔纹")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/remove_moire";
                } else if (selected == "文档图片去底纹")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/doc_repair";
                } else if (selected == "图像去噪")
                {
                    url = "https://aip.baidubce.com/rest/2.0/image-process/v1/denoise";
                }
            }

            //这行很关键，调用Base64ToPicture类的方法，实现base64编码解码为图像
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
        originalImageLabel.setBounds(20, 80, 300, 300);
        // 创建标签用于显示转换后的图像
        convertedImageLabel = new JLabel();
        convertedImageLabel.setBounds(300, 80, 300, 300);

        //图像保存
        JButton saveButton = new JButton("保存");
        saveButton.setBounds(480, 390, 50, 20);
        saveButton.addActionListener(e -> {
            //实现图像保存
            saveImage();
        });

        add(originalImageLabel);
        add(convertedImageLabel);
        add(radio1);
        add(radio2);
        add(comboBox1);
        add(comboBox2);
        add(label1);
        add(label2);
        add(saveButton);
        add(chooseFileButton);
        add(convertButton);
    }

    /**
     * 实现图像的缩放，让图像显示在指定大小的空间内
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
     * 实现保存图像，可指定保存的名称和路径，格式固定为jpg
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
                    // 保存图像到指定文件
                    ImageIO.write(convertedImage, "jpg", newFile);
                    JOptionPane.showMessageDialog(this, "图像保存成功");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "图像保存失败");
                }
            }
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new ImageConverterGUI();
            }
        });
    }
}
