package levels;

import entities.Ball;
import main.Game;
import objects.Coin;
import objects.Spike;
import utilz.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.*;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetBalls;
import static utilz.HelpMethods.GetPlayerSpawn;
import static utilz.HelpMethods.GetCoins;
public class Level {
    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Ball> balls;
    private ArrayList<Coin> coins;
    private ArrayList<Spike> spikes;

    // X offset
    private int lvlTilesWidth;
    private int maxTilesOffsetX;
    private int maxLvlOffsetX;

    // Y offset
    private int lvlTilesHeight;
    private int maxTilesOffsetY;
    private int maxLvlOffsetY;
    private Point playerSpawn;
//    private int lvlIndex;

    public Level(BufferedImage img){
        this.img = img;
//        this.lvlIndex = lvlIndex;
        createLevelData();
        createEnemies();
        createSpikes();
        createCoins();
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

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void createCoins() {
        coins = HelpMethods.GetCoins(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
//        lvlData = loadLvlData();
//        saveLvlData(lvlIndex);
    }

//    private void saveLvlData(int intIndex) {
//        String index = String.valueOf(intIndex);
//        try {
//            FileOutputStream fileOut = new FileOutputStream("res/lvlsBin/levelData" + index + ".bin");
//            // relative path to full
//            //FileOutputStream fileOut = new FileOutputStream("C:\\Users\\User\\Desktop\\Java\\Game\\res\\lvlsBin\\levelData.bin");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(lvlData);
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized data is saved in res/lvlsBin/levelData" + index + ".bin");
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }
//
//    private int[][] loadLvlData(){
//        int[][] lvlData = null;
//        String index = String.valueOf(lvlIndex);
//        try {
//            FileInputStream fileIn = new FileInputStream("res/lvlsBin/levelData" + index + ".bin");
//            // relative path to full
//            //FileInputStream fileIn = new FileInputStream("C:\\Users\\User\\Desktop\\Java\\Game\\res\\lvlsBin\\levelData.bin");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            lvlData = (int[][]) in.readObject();
//            in.close();
//            fileIn.close();
//        } catch (IOException i) {
//            i.printStackTrace();
//            return null;
//        } catch (ClassNotFoundException c) {
//            System.out.println("lvlData class not found");
//            c.printStackTrace();
//            return null;
//        }
//        return lvlData;
//    }

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
    public ArrayList<Coin> getCoins() {
        return coins;
    }
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
