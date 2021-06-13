package TestModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestAction {

    public static void main(String[] args) {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(250, 250);
        jf.setLocationRelativeTo(null); //设置窗口相对于指定组件的位置  设置为null为屏幕的中央
//        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        // 创建一个中间容器(面板容器)
        JPanel panel = new JPanel();

        // 创建一个 5 行 10 列的文本区域
        final JTextArea textArea = new JTextArea(5, 10);
        // 设置自动换行
        textArea.setLineWrap(true);
        // 添加到内容面板
        panel.add(textArea);

        // 创建一个基本组件（按钮），并添加到 面板容器 中--->创建一个提交按钮，点击按钮获取输入文本
        JButton btn = new JButton("提交");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("提交: " + textArea.getText());
            }
        });

        panel.add(btn);

        // 把 面板容器 作为窗口的内容面板 设置到 窗口
        jf.setContentPane(panel);
        // 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);
    }

}
