/*
* 控制8轴方向的运动，注释中有解释。但基本上，KeyListener只要定义可能的移动位置，然后a Thread将组合可能的目的地并移动JLabel。
* */

package TestAction;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.io.Serial;

import javax.swing.JFrame;

import javax.swing.JLabel;

public class TestKeyListener3 extends JFrame {
    @Serial
    private static final long serialVersionUID = 7722803326073073681L;

    private boolean left = false;

    private boolean up = false;

    private boolean down = false;

    private boolean right = false;

    private JLabel lbl = new JLabel("#");

    public void Move8Axis() {
// Just setting up the window and objects

        setSize(400, 400);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);

        lbl.setBounds(100, 100, 20, 20);  //设置组件的坐标 和 长宽

        add(lbl);

        setLocationRelativeTo(null);

// Key listener, will not move the JLabel, just set Condition to

        addKeyListener(new KeyListener() {
            @Override

            public void keyTyped(KeyEvent e) {}

            @Override

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;

                if (e.getKeyCode() == KeyEvent.VK_UP) up = false;

                if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;

            }

            @Override

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;

                if (e.getKeyCode() == KeyEvent.VK_UP) up = true;

                if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;

            }

        });

// This thread will read the 4 variables left/right/up/down at every 30 milliseconds

// It will check the combination of keys (left and up, right and down, just left, just up...)

// And move the label 3 pixels

        new Thread(() -> {
            try {
                while (true) {
                    if (left && up) {
                        lbl.setBounds(lbl.getX() - 3, lbl.getY() - 3, 20, 20);

                    } else if (left && down) {
                        lbl.setBounds(lbl.getX() - 3, lbl.getY() + 3, 20, 20);

                    } else if (right && up) {
                        lbl.setBounds(lbl.getX() + 3, lbl.getY() - 3, 20, 20);

                    } else if (right && down) {
                        lbl.setBounds(lbl.getX() + 3, lbl.getY() + 3, 20, 20);

                    } else if (left) {
                        lbl.setBounds(lbl.getX() - 3, lbl.getY(), 20, 20);

                    } else if (up) {
                        lbl.setBounds(lbl.getX(), lbl.getY() - 3, 20, 20);

                    } else if (right) {
                        lbl.setBounds(lbl.getX() + 3, lbl.getY(), 20, 20);

                    } else if (down) {
                        lbl.setBounds(lbl.getX(), lbl.getY() + 3, 20, 20);

                    }

                    Thread.sleep(30);

                }

            } catch (Exception ex) {
                ex.printStackTrace();

                System.exit(0);

            }

        }).start();

    }

    public static void main(String[] args) {
        TestKeyListener3 testKeyListener3 = new TestKeyListener3();
         testKeyListener3.Move8Axis();

    }

}
