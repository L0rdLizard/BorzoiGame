package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

public abstract class Enemy extends Entity {
    protected int animIndex, animTick, animSpeed = 28;
    protected int enemyState, enemyType;
    protected boolean firstUpdate = true;
    protected boolean inAir = false;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    protected void firstupdateCheck(int[][] lvlData){
        if(!IsEntityOnFloor(hitbox , lvlData)){
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData){
        if(CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        }else {
            inAir = false;
            hitbox.y = GetEntityYPosNextToBarrier(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if(IsFloor(hitbox, xSpeed, lvlData)){
                hitbox.x += xSpeed;
                return;
            }

        changeWalDir();
    }
    protected void turnTowardsPlayer(Player player){
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;

    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY){
            if (isPlayerInRange(player)){
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY)){
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }
    protected boolean isPlayerCloseToAttack(Player player){
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 2;
    }

    protected void newState(int enemyState){
        this.enemyState = enemyState;
        animTick = 0;
        animIndex = 0;
    }
    protected void updateAnimationTick(){
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(enemyType, enemyState)){
                animIndex = 0;
                if (enemyState == ATTACK)
                    enemyState = IDLE;
            }
        }
    }

    protected void changeWalDir() {
        if(walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    public int getAnimIndex() {
        return animIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
}
