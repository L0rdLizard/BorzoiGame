package levels;

import entities.Ball;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetBalls;
import static utilz.HelpMethods.GetPlayerSpawn;
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
    private Point playerSpawn;

    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWidth = img.getWidth();
        maxTilesOffsetX = lvlTilesWidth - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffsetX;

        lvlTilesHeight = img.getHeight();
        maxTilesOffsetY = lvlTilesHeight - Game.TILES_IN_HEIGHT;
        maxLvlOffsetY = Game.TILES_SIZE * maxTilesOffsetY;

        if(maxTilesOffsetY < 0)
            maxLvlOffsetY = 0;

        System.out.println();
        System.out.println("lvlTilesHeight: " + lvlTilesHeight);
        System.out.println("maxTilesOffsetY: " + maxTilesOffsetY);
        System.out.println("maxLvlOffsetY: " + maxLvlOffsetY);
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
    public Point getPlayerSpawn(){
        return playerSpawn;
    }
}
