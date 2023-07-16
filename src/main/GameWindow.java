package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
    private JFrame jFrame;

    public GameWindow(GamePanel gamePanel){
        jFrame = new JFrame();
        jFrame.setUndecorated(true);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
//        jFrame.setLocation(0, 0);
        jFrame.setResizable(false);
        jFrame.pack();
//        jFrame.setLocationRelativeTo(null);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        jFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        jFrame.setVisible(true);
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
