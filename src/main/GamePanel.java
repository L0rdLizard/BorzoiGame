package main;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private KeyBoardInputs keyboardInputs;
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;
    private BufferedImage image;
    private BufferedImage[][] animations;
    private int animTick, animIndex, animSpeed = 15;

    public GamePanel(){
        keyboardInputs = new KeyBoardInputs(this);
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[1][5];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                //TODO
//            animations[j][i] = image.getSubimage(i*64,j*40,64,40);
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/withoutCloth_black.png");
        System.out.println(is);
        try {
            assert is != null;
            image = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
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

    private void updateAnimationTick() {
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if (animIndex >= 5){
                animIndex = 0;
            }
        }
    }

    public void paintComponent(Graphics g){
//        ImageIcon imageIcon = new ImageIcon("H:/guap/BorzoiGame/src/res/withoutCloth_black.png");
        super.paintComponent(g);
//        g.drawImage(imageIcon.getImage() ,xDelta, yDelta, null);
        updateAnimationTick();

        g.drawImage(image, (int) xDelta, (int) yDelta, 256, 256, null);
        g.drawImage(animations[0][animIndex], (int) xDelta, (int) yDelta, 256, 256, null);
    }


}
