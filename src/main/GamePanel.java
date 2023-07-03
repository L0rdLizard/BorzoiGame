package main;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {
    private KeyBoardInputs keyboardInputs;
    private MouseInputs mouseInputs;
    private int xDelta = 100;
    private int yDelta = 100;
//    private int frames = 0;
//    private long lastCheck = 0;
    public GamePanel(){
        keyboardInputs = new KeyBoardInputs(this);
        mouseInputs = new MouseInputs(this);
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value){
        this.xDelta += value;

    }

    public void changeYDelta(int value){
        this.yDelta += value;

    }

    public void setNosPos(int x, int y){
        this.xDelta = x;
        this.yDelta = y;

    }

    public void paintComponent(Graphics g){
        ImageIcon imageIcon = new ImageIcon("H:/guap/BorzoiGame/src/withoutCloth_black.png");
        super.paintComponent(g);
        g.drawRect(100, 100, 150, 100);
        g.drawImage(imageIcon.getImage() ,xDelta, yDelta, null);

//        frames++;
//        if(System.currentTimeMillis() - lastCheck >= 1000){
//            lastCheck = System.currentTimeMillis();
//            System.out.println("fps" + frames);
//            frames = 0;
//        }
//        repaint();
    }
}
