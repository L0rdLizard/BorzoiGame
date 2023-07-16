package entities;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Ball extends Enemy{
    // Attack hitbox
    public Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    public Ball(float x, float y) {
        super(x, y, BALL_WIDTH, BALL_HEIGHT, BALL);
        initHitbox(x, y, (int)(30 * Game.SCALE), (int)(30 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (30 * Game.SCALE), (int) (40 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 10);
    }

    public void update(int[][] lvlData, Player player){
        if (active) {
            updateBehavior(lvlData, player);
            updateAnimationTick();
            updateAttackBox();
        }
    }

    private void updateAttackBox() {
        if (walkDir == RIGHT){
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 5);
        } else if (walkDir == LEFT){
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 5);
        }
        attackBox.y = hitbox.y - (Game.SCALE * 10);
//        attackBox.x = hitbox.x;
//        attackBox.y = hitbox.y;
    }



    public void updateBehavior(int[][] lvlData, Player player){
        if (firstUpdate){
            firstupdateCheck(lvlData);
        }

        if (inAir){
            updateInAir(lvlData);
        }else {
            switch (enemyState){
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if(canSeePlayer(lvlData, player))
                        turnTowardsPlayer(player);
                    if(isPlayerCloseToAttack(player) && ( Math.abs(hitbox.y - player.hitbox.y) <= Game.TILES_SIZE))
                        newState(ATTACK);

                    move(lvlData);
                    break;
                case ATTACK:
                    if (animIndex == 0)
                        attackChecked = false;
                    if (animIndex == 3 && !attackChecked){
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case HIT:
                    break;
            }
        }
    }



    public int flipX(){
        if (walkDir == RIGHT)
            return width + 44;
        else
            return 0;
    }

    public int flipW(){
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }
}
