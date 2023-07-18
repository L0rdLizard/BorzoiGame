package entities;

import gameStates.Playing;
import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animTick, animIndex, animSpeed = 28;
    private int playerAction = IDLE;
    boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] lvlData;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private Playing playing;

    // Jumping
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    // Jump / Gravity
    private BufferedImage hpBarImg;
    private float xDrawOffset = 36 * Game.SCALE;
    private float yDrawOffset = 29 * Game.SCALE;
    public boolean doubleJump = false;
    public int jumps = 0;

    // HP bar
    private int hpBarWidth = (int) (96 * Game.SCALE);
    private int hpBarHeight = (int) (32 * Game.SCALE);
    private int hpBarX = (int) (10 * Game.SCALE);
    private int hpBarY = (int) (10 * Game.SCALE);
    private int maxHealth = 3;
    private int currentHealth = maxHealth;

    // Attack hitbox
    private Rectangle2D.Float attackBox;


    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimation();
        initHitbox(x, y, (int) (30*Game.SCALE), (int) (30*Game.SCALE));
        initAttackBox();
    }

    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(30 * Game.SCALE), (int)(40 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();

        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();

        updatePos();
        if (moving) {
            checkCoinTouched();
            checkSpikesTouched();
        }
        if (attacking)
            checkAttack();

        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);

    }

    private void checkCoinTouched() {
        playing.checkCoinTouched(hitbox);
    }

    private void checkAttack() {
        if (attackChecked || animIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }

    private void updateAttackBox() {
        if (right){
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 5);
        } else if (left){
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 5);
        }
        attackBox.y = hitbox.y - (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        switch (currentHealth){
            case 3:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3);
                break;
            case 2:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_1);
                break;
            case 1:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_2);
                break;
            case 0:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_3);
                break;
            default:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_3);
                break;
        }
    }

    public void render(Graphics g, int lvlOffset, int yLvlOffset) {
        g.drawImage(animations[playerAction][animIndex],
                (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
                (int) (hitbox.y - yDrawOffset) - yLvlOffset,
                width * flipW, height, null);
//        drawHitbox(g, lvlOffset, yLvlOffset);
//        drawAttackBox(g, lvlOffset, yLvlOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffset, int yLvlOffset) {
        g.setColor(Color.black);
        g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(hpBarImg, hpBarX, hpBarY, hpBarWidth, hpBarHeight, null);
    }


    private void updateAnimationTick() {
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(playerAction)){
                animIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = DROP;
        }

        if (attacking) {
            playerAction = ATTACK;
            if (startAni != ATTACK){
                animIndex = 1;
                animTick = 0;
                return;
            }
        }

        if (startAni != playerAction)
            resetAnimTick();
    }

    private void resetAnimTick() {
        animTick = 0;
        animIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (!inAir && right && left){
            return;
        }

        if (jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = 0;
            flipW = 1;

        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = width + 44;
            flipW = -1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosNextToBarrier(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
//        if (inAir)
//            return;
        if (inAir)
            if (doubleJump && jumps < 2) {
                airSpeed = jumpSpeed;
                doubleJump = false;
                return;
            }else
                return;
        inAir = true;
        doubleJump = true;
        airSpeed = jumpSpeed;
    }

    public void kill() {
        currentHealth = 0;
    }
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        jumps = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value){
        currentHealth += value;
        if (currentHealth <= 0){
            currentHealth = 0;
            // gameOver();
        }else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }
    private void loadAnimation(){

        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[5][6];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
//                animations[j][i] = image.getSubimage(i * 32, j * 24, 32, 24);
//                animations[j][i] = image.getSubimage(i * 64, j * 48, 64, 48);
                animations[j][i] = image.getSubimage(i * 80, j * 64, 80, 64);
            }
        }
        hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }
}
