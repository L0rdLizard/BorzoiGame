package entities;

import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.HelpMethods.CanMoveHere;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animTick, animIndex, animSpeed = 30;
    public int playerAction = IDLE;
    boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;

    // Jumping
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = 2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private float xDrawOffset = 10 * Game.SCALE;
    private float yDrawOffset = 11 * Game.SCALE;
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimation();
        initHitbox(x, y, 32*Game.SCALE, 32*Game.SCALE);
    }
    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();

    }
    public void render(Graphics g){
        g.drawImage(animations[playerAction][animIndex], (int) (hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);
    }


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
        if (!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed = 0;
        if (left && right)
            return;
        else if (left && !right)
            xSpeed = -playerSpeed;
        else if(right && !left)
            xSpeed = playerSpeed;

        if (up && down)
            return;
        else if (up && !down)
            ySpeed = -playerSpeed;
        else if(down && !up)
            ySpeed = playerSpeed;

        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
        }
    }
    private void loadAnimation(){
//        InputStream is = getClass().getResourceAsStream("/pito_animation_run_idle_sheet.png");
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[2][4];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
//                animations[j][i] = image.getSubimage(i * 32, j * 24, 32, 24);
                animations[j][i] = image.getSubimage(i * 64, j * 48, 64, 48);
            }
        }
    }

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
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
