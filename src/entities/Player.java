package entities;

import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animTick, animIndex, animSpeed = 30;
    public int playerAction = IDLE;
    boolean left, right, up, down;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 2.0f;
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimation();
    }
    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();

    }
    public void render(Graphics g){
        g.drawImage(animations[playerAction][animIndex], (int) x, (int) y, width, height, null);
    }

//    public void setDirection (int direction){
//        this.playerDir = direction;
//        moving = true;
//    }
//    public void setMoving (boolean moving){
//        this.moving = moving;
//    }

    private void updateAnimationTick() {
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(playerAction)){
                animIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAnim = playerAction;

        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if(attacking)
            playerAction = ATTACK;

        if (startAnim != playerAction){
            resetAnimTick();
        }
    }

    private void resetAnimTick() {
        animTick = 0;
        animIndex = 0;
    }

    private void updatePos() {
        moving = false;
        if (left && !right){
            x-=playerSpeed;
            moving = true;
        }else if(right && !left){
            x+=playerSpeed;
            moving = true;
        }

        if (up && !down){
            y-=playerSpeed;
            moving = true;
        }else if(down && !up){
            y+=playerSpeed;
            moving = true;
        }
//        if (moving){
//            switch (playerDir) {
//                case LEFT -> x -= 1;
//                case UP -> y -= 1;
//                case RIGHT -> x += 1;
//                case DOWN -> y += 1;
//            }
//        }
    }
    private void loadAnimation(){
//        InputStream is = getClass().getResourceAsStream("/pito_animation_run_idle_sheet.png");
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[2][4];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = image.getSubimage(i * 64, j * 48, 64, 48);
            }
        }
    }
    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public void setAttack(boolean attacking){
        this.attacking = attacking;
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
