package entities;
import main.Game;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Ball extends Enemy{
    public Ball(float x, float y) {
        super(x, y, BALL_WIDTH, BALL_HEIGHT, BALL);
        initHitbox(x, y, (int)(30 * Game.SCALE), (int)(30 * Game.SCALE));
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
}
