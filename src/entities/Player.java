package entities;

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
    public int playerDir = -1;
    private boolean moving = false;
    public Player(float x, float y) {
        super(x, y);
        loadAnimation();

    }
    public void update(){
        updateAnimationTick();
        setAnimation();
        updatePos();
    }
    public void render(Graphics g){
        g.drawImage(animations[playerAction][animIndex], (int) x, (int) y, 256, 192, null);
    }

    public void setDirection (int direction){
        this.playerDir = direction;
        moving = true;
    }
    public void setMoving (boolean moving){
        this.moving = moving;
    }

    private void updateAnimationTick() {
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(playerAction)){
                animIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updatePos() {
        if (moving){
            switch (playerDir) {
                case LEFT -> x -= 5;
                case UP -> y -= 5;
                case RIGHT -> x += 5;
                case DOWN -> y += 5;
            }
        }
    }
    private void loadAnimation(){
        InputStream is = getClass().getResourceAsStream("/pito_animation_run_idle_sheet.png");
        System.out.println(is);
        try {
            assert is != null;
            BufferedImage image = ImageIO.read(is);
            animations = new BufferedImage[2][4];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = image.getSubimage(i*64,j*48,64,48);
                }
            }
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
}
