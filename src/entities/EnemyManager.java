package entities;

import gameStates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] ballImgs;
    private ArrayList<Ball> balls = new ArrayList<>();

    private float xDrawOffset = 36 * Game.SCALE;
    private float yDrawOffset = 29 * Game.SCALE;
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        balls = level.getBalls();
//        System.out.println("size of ball: " + balls.size());
    }

    public void update(int[][] lvlData, Player player){
        boolean isAnyActive = false;
        for (Ball b : balls)
            if(b.isActive()) {
                b.update(lvlData, player);
                isAnyActive = true;
            }
        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }
    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        drawBall(g, xLvlOffset, yLvlOffset);
    }

    private void drawBall(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Ball b : balls){
            if (b.isActive()) {
                g.drawImage(ballImgs[b.getEnemyState()][b.getAnimIndex()],
                        (int) (b.getHitbox().x) - xLvlOffset - BALL_DRAWOFFSET_X + b.flipX(),
                        (int) (b.getHitbox().y) - yLvlOffset - BALL_DRAWOFFSET_Y,
                        BALL_WIDTH * b.flipW(), BALL_HEIGHT, null);
//                drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Ball b : balls)
            if (b.isActive())
                if (attackBox.intersects(b.getHitbox())) {
                    b.hurt(1);
                    return;
                }
    }
    private void drawAttackBox(Graphics g, int lvlOffset, int yLvlOffset) {
        for (Ball b : balls) {
            g.setColor(Color.black);
            g.drawRect((int) b.attackBox.x - lvlOffset, (int) b.attackBox.y - yLvlOffset, (int) b.attackBox.width, (int) b.attackBox.height);
        }
    }

    private void loadEnemyImages() {
       ballImgs = new BufferedImage[5][6];
       BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.BALL_ATLAS);
       for (int j = 0; j < ballImgs.length; j++){
          for (int i = 0; i < ballImgs[0].length; i++){
              ballImgs[j][i] = temp.getSubimage(i * BALL_WIDTH_DEFAULT, j * BALL_HEIGHT_DEFAULT, BALL_WIDTH_DEFAULT, BALL_HEIGHT_DEFAULT);
          }
       }
    }

    public void resetAllEnemies() {
        for (Ball c : balls)
            c.resetEnemy();
    }
}
