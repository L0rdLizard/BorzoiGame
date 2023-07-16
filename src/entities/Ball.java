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

    public void update(int[][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
    }

    public void updateMove(int[][] lvlData, Player player){
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
                    if(isPlayerCloseToAttack(player))
                        newState(ATTACK);

                    move(lvlData);
                    break;
            }
        }
    }
}
