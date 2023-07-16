package entities;

import gameStates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
        addEnemies();
    }

    private void addEnemies() {
        balls = LoadSave.GetBalls();
        System.out.println("size of ball: " + balls.size());
    }

    public void update(int[][] lvlData){
        for (Ball b : balls)
            b.update(lvlData);
    }
    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        drawBall(g, xLvlOffset, yLvlOffset);
    }

    private void drawBall(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Ball b : balls){
            g.drawImage(ballImgs[b.getEnemyState()][b.getAnimIndex()], (int) (b.getHitbox().x - xDrawOffset) - xLvlOffset, (int) (b.getHitbox().y - yDrawOffset) - yLvlOffset, BALL_WIDTH, BALL_HEIGHT, null);
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

}
