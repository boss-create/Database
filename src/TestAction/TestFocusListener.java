package TestAction;
import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
public class TestFocusListener {

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(720,480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jpanel = new JPanel();
        final JTextArea textArea = new JTextArea(5, 10);
        // 设置自动换行
        textArea.setLineWrap(true);
        // 添加到内容面板
        jpanel.add(textArea);
        JButton jButton = new JButton("提交");
        jButton.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("获得焦点: "+e.getSource());
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("失去焦点: "+e.getSource());
            }
        });
        jpanel.add(jButton);

        frame.setContentPane(jpanel);
        frame.setVisible(true);
    }
}
