package entities;
import main.Game;

import static utilz.Constants.EnemyConstants.*;
public class Ball extends Enemy{
    public Ball(float x, float y) {
        super(x, y, BALL_WIDTH, BALL_HEIGHT, BALL);
        initHitbox(x, y, (int)(30 * Game.SCALE), (int)(30 * Game.SCALE));
    }
}
