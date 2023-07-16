package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

public abstract class Enemy extends Entity {
    private int animIndex, animTick, animSpeed = 28;
    private int enemyState, enemyType;
    private boolean firstUpdate = true;
    private boolean inAir = false;
    private float fallSpeed;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 0.4f * Game.SCALE;
    private int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }
    private void updateAnimationTick(){
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(enemyType, enemyState)){
                animIndex = 0;
            }
        }
    }
    public void update(int[][] lvlData){
        updateMove(lvlData);
        updateAnimationTick();
    }
    public void updateMove(int[][] lvlData){
        if (firstUpdate){
            if(!IsEntityOnFloor(hitbox , lvlData)){
                inAir = true;
            }
            firstUpdate = false;
        }

        if (inAir){
            if(CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            }else {
                inAir = false;
                hitbox.y = GetEntityYPosNextToBarrier(hitbox, fallSpeed);
            }
        }else {
            switch (enemyState){
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
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

                    break;
            }
        }
    }

    private void changeWalDir() {
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
