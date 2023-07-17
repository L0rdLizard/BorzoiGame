package levels;

import entities.Ball;
import main.Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetBalls;

public class Level {
    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Ball> balls;

    // X offset
    private int lvlTilesWidth;
    private int maxTilesOffsetX;
    private int maxLvlOffsetX;

    // Y offset
    private int lvlTilesHeight;
    private int maxTilesOffsetY;
    private int maxLvlOffsetY;

    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
    }

    private void calcLvlOffsets() {
        lvlTilesWidth = img.getWidth();
        maxTilesOffsetX = lvlTilesWidth - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffsetX;

        lvlTilesHeight = img.getHeight();
        maxTilesOffsetY = lvlTilesHeight - Game.TILES_IN_HEIGHT;
        maxTilesOffsetY = Game.TILES_SIZE * maxTilesOffsetY;
    }

    private void createEnemies() {
        balls = GetBalls(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    public int[][] getLvlData(){
        return lvlData;
    }

    public int getLvlOffsetX(){
        return maxLvlOffsetX;
    }

    public int getLvlOffsetY(){
        return maxLvlOffsetY;
    }

    public ArrayList<Ball> getBalls(){
        return balls;
    }
}
