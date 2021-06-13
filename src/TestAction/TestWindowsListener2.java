/*
* 测试关闭窗口时的方法的调用
* */
package TestAction;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TestWindowsListener2 extends JFrame
{
    public static void main(String[] args)
    {
        new TestWindowsListener2();
    }

    public TestWindowsListener2()
    {
        // 创建一个面板
        JPanel paContent = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paContent.setPreferredSize(new Dimension(400, 100));
        // 在面板中创建一个按钮用于关闭窗体
        JButton btn = new JButton("关闭");
        btn.setPreferredSize(new Dimension(100, 25));
//        btn.addActionListener(new ActionListener()
//        {
//
//        public void actionPerformed(ActionEvent e)
//        {
//            closeFrame();
//        }
//
//    });
        // 向面板中加载按钮
        paContent.add(btn);

        // 设置面板
        this.setTitle("Close Event Demo");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setContentPane(paContent);
        this.pack();
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter()  //抽象类WindowAdapter   相当于一个匿名内部类继承一个抽象类  而且不用实现所有的方法
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("触发windowClosing事件");
                closeFrame();
            }

//            public void windowClosed(WindowEvent e)
//            {
//                System.out.println("触发windowClosed事件");
//            }
        });

        this.setVisible(true);
    }

    // 关闭窗体
    private void closeFrame()
    {
        System.out.println("调用窗体关闭功能");
        ImageIcon img1 = new ImageIcon("img\\Exit.png");
        img1.setImage(img1.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        int result = JOptionPane.showConfirmDialog(null, "是否要退出？", "退出确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,img1);
        if (result == JOptionPane.YES_OPTION)
            this.dispose();
    }
}
