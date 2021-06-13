package TestAction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestActionListener1 {
    public static void main(String[] args){
        final String COMMAND_OK = "OK";
        final String COMMAND_CANCEL = "Cancel";

        JFrame jf = new JFrame();
        jf.setSize(720,480);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        TextArea textArea = new TextArea(5,10);
        jp.add(textArea);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand(COMMAND_OK);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(COMMAND_CANCEL);

        // 创建一个动作监听器实例
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取事件源，即触发事件的组件（按钮）本身
                // e.getSource();

                // 获取动作命令
                String command = e.getActionCommand();

                // 根据动作命令区分被点击的按钮
                if (COMMAND_OK.equals(command)) {
                    System.out.println("OK 按钮被点击");

                } else if (COMMAND_CANCEL.equals(command)) {
                    System.out.println("Cancel 按钮被点击");
                }
            }
        };

        okButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
        jp.add(okButton);
        jp.add(cancelButton);

        jf.setContentPane(jp);
        jf.setVisible(true);
    }
}
