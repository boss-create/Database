package TestAction;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyAction1 {

    public static void main(String[] args) throws AWTException {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(780, 470);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        final JButton btn = new JButton("提交");

        ImageIcon img1 = new ImageIcon("img\\SubmitButton1.png");
        img1.setImage(img1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));

        ImageIcon img2 = new ImageIcon("img\\PressButton1.png");
        img2.setImage(img2.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        // 设置按钮的默认图片
        btn.setIcon(img1);

        // 设置按钮被点击时的图片
        btn.setPressedIcon(img2);

        // 不绘制边框
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setSize(1,2);
        // 添加按钮点击事件监听器
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按钮被点击了");
            }
        });

        panel.add(btn);

        jf.setContentPane(panel);
        jf.setVisible(true);
    }

}
