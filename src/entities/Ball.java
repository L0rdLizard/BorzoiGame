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
            firstupdateCheck(lvlData);
        }

        if (inAir){
            updateInAir(lvlData);
        }else {
            switch (enemyState){
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                    move(lvlData);
                    break;
            }
        }
    }
}
