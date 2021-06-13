package TestAction;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestDBMSForm {
    //    private final TestDBMSForm dbms;
    private JTextArea showText, inputText;// 显示框与输入框
    private final JFrame frame;// 窗口框架
    private static TestDBMSForm form;

    static {
        form = new TestDBMSForm();
        form.setVisible(true);
    }

    private TestDBMSForm() {
        JPanel panel = new JPanel();
        panel.setLayout(null);// 清空布局，使用像素位定义布局

        showText = new JTextArea();

        showText.setText("\n\n\n\n\t\t\t\t欢迎进入数据库系统！");
        showText.setLineWrap(true);// 设置自动换行
        showText.setEditable(false);// 设置不可编辑
        showText.setSize(780, 470);// 设置大小
        showText.setLocation(0, 0);// 设置位置
        panel.add(showText);

        JLabel label = new JLabel("请输入SQL语句：");
        label.setSize(780, 12);
        label.setLocation(0, 470);
        panel.add(label);

        inputText = new JTextArea();
        inputText.setBackground(Color.decode("#E6E6E6"));
        inputText.setSize(780, 50);
        inputText.setLineWrap(true);
        inputText.setLocation(0, 482);
        panel.add(inputText);

        // 提交按钮
        JButton okButton = new JButton("提交");
        okButton.setLocation(360, 530);
        okButton.setSize(60, 35);
        panel.add(okButton);
//        dbms = new TestDBMSForm(this);
        frame = new JFrame("叭叭叭数据库管理系统");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //关闭窗口程序后台也关闭
        int windowWidth = frame.getWidth(); // 获得窗口宽
        int windowHeight = frame.getHeight(); // 获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width; // 获取屏幕的宽
        int screenHeight = screenSize.height; // 获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2
                - windowHeight / 2);// 设置窗口居中显示

        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeFrame();
            }
        });

    }

    private void closeFrame()
    {
//        System.out.println("调用窗体关闭功能");
        ImageIcon img1 = new ImageIcon("img\\Exit.png");
        img1.setImage(img1.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        int result = JOptionPane.showConfirmDialog(null, "是否要退出？", "退出确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,img1);
        if (result == JOptionPane.YES_OPTION)
            frame.dispose();
    }

    public static void main(String[] args) {
        try {
            Class.forName("TestAction.TestDBMSForm");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

}
