package main;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;


public class GamePanel extends JPanel {
    private KeyBoardInputs keyboardInputs;
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;
    private BufferedImage image;
    private BufferedImage[][] animations;
    private int animTick, animIndex, animSpeed = 30;
    public int playerAction = IDLE;
    public int playerDir = -1;
    private boolean moving = false;

    public GamePanel(){
        keyboardInputs = new KeyBoardInputs(this);
        mouseInputs = new MouseInputs(this);

//        importImg();
//        loadAnimations();

        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

//    private void loadAnimations() {
//        animations = new BufferedImage[2][4];
//        for (int j = 0; j < animations.length; j++) {
//            for (int i = 0; i < animations[j].length; i++) {
////                animations[j][i] = image.getSubimage(i*128,j*128,128,128);
//            animations[j][i] = image.getSubimage(i*64,j*48,64,48);
//            }
//        }
//    }

//    private void importImg() {
//        InputStream is = getClass().getResourceAsStream("/pito_animation_run_idle_sheet.png");
//        System.out.println(is);
//        try {
//            assert is != null;
//            image = ImageIO.read(is);
//        } catch (IOException e){
//            e.printStackTrace();
//        } finally {
//            try {
//                assert is != null;
//                is.close();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

//    public void changeXDelta(int value){
//        this.xDelta += value;
//    }
//
//    public void changeYDelta(int value){
//        this.yDelta += value;
//    }
//
//    public void setNosPos(int x, int y){
//        this.xDelta = x;
//        this.yDelta = y;
//    }

//    public void setDirection (int direction){
//        this.playerDir = direction;
//        moving = true;
//    }
//    public void setMoving (boolean moving){
//        this.moving = moving;
//    }
//
//    private void updateAnimationTick() {
//        animTick++;
//        if(animTick >= animSpeed){
//            animTick = 0;
//            animIndex++;
//            if (animIndex >= GetSpriteAmount(playerAction)){
//                animIndex = 0;
//            }
//        }
//    }
//
//    private void setAnimation() {
//        if(moving)
//            playerAction = RUNNING;
//        else
//            playerAction = IDLE;
//    }
//
//    private void updatePos() {
//        if (moving){
//            switch (playerDir) {
//                case LEFT -> xDelta -= 5;
//                case UP -> yDelta -= 5;
//                case RIGHT -> xDelta += 5;
//                case DOWN -> yDelta += 5;
//            }
//        }
//    }

    public void updateGame(){
//        updateAnimationTick();
//        setAnimation();
//        updatePos();
    }
    public void paintComponent(Graphics g){
//        ImageIcon imageIcon = new ImageIcon("H:/guap/BorzoiGame/src/res/withoutCloth_black.png");
        super.paintComponent(g);

//        g.drawImage(image, (int) xDelta, (int) yDelta, 256, 256, null);
//        g.drawImage(animations[playerAction][animIndex], (int) xDelta, (int) yDelta, 256, 192, null);
    }
}
