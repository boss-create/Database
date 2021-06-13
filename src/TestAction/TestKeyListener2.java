package TestAction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TestKeyListener2 {
    public static void main(String[] args){

        JFrame jf = new JFrame();
        jf.setSize(720,380);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        JTextArea textArea = new JTextArea(5,10);
        textArea.setLineWrap(true);


        JButton okButton = new JButton();

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 获取键值，和 KeyEvent.VK_XXXX 常量比较确定所按下的按键
                int keyCode = e.getKeyCode();
                System.out.println("按下: " + e.getKeyCode());
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // e.getKeyChar() 获取键入的字符
                System.out.println("键入: " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("释放: " + e.getKeyCode());
            }
        });

        ActionListener actionListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String command = e.getActionCommand();
                System.out.println(e);


            }
        };
        okButton.addActionListener(actionListener);
        okButton.setToolTipText("提交");
        jp.add(textArea);
        jp.add(okButton);


        jf.setContentPane(jp);
        jf.setVisible(true);
    }
}
