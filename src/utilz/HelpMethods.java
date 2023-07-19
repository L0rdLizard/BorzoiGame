package utilz;

import entities.Ball;
import entities.Player;
import main.Game;
import objects.Coin;
import objects.Spike;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.BALL;
import static utilz.Constants.ObjectConstants.SPIKE;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData){
        if(!IsSolid(x, y, lvlData))
            if (!IsSolid(x+width, y+height, lvlData))
                if (!IsSolid(x+width, y, lvlData))
                    if (!IsSolid(x, y+height, lvlData))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData){
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        int maxHeight = lvlData.length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= maxHeight)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex,(int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData){
        int value = lvlData[yTile][xTile];
        // 11 - invisible
//        if (value >= 48 || value < 0 || value != 11)
//            return true;
//        return false;

        if (value >= 48 || value == 0)
            return false;
        return true;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }
    public static float GetEntityYPosNextToBarrier(Rectangle2D.Float hitbox, float airSpeed) {
        int difYandHeight = (int) hitbox.height;
        while (difYandHeight > 32)
            difYandHeight-=32;
        int currentTile = ((int) (hitbox.y + hitbox.height - difYandHeight) / Game.TILES_SIZE);
        if (airSpeed > 0) {
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            return currentTile * Game.TILES_SIZE;

//        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
//        if (airSpeed > 0) {
//            // Falling - touching floor
//            int tileYPos = currentTile * Game.TILES_SIZE;
//            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
//            return tileYPos + yOffset - 1;
//        } else
//            // Jumping
//            return currentTile * Game.TILES_SIZE;
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData){
        for (int i = 0; i < xEnd - xStart; i ++){
            if (IsTileSolid(xStart + i, y, lvlData)){
                return false;
            }
            if (!IsTileSolid(xStart + i, y + 1, lvlData)){
                return false;
            }
        }
        return true;
    }
    public static boolean IsSightClear( int[][] lvlData, Rectangle2D.Float firstHitbox,
                                        Rectangle2D.Float secondHitbox, int yTile){
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
    }

    public static int[][] GetLevelData(BufferedImage lvlImage){
        // TODO
        int[][] lvlData = new int[lvlImage.getHeight()][lvlImage.getWidth()];

        for (int j = 0; j < lvlImage.getHeight(); j++){
            for (int i = 0; i < lvlImage.getWidth(); i++){
                Color color = new Color(lvlImage.getRGB(i, j));
                int value = color.getRed();
//                if (value >= 48)
//                    value = 11;

                if (value >= 0 && value < 48) {
                    lvlData[j][i] = value;
//                    if (value == 0)
//                        System.out.println("0");
                }
                else if (value == 100)
                    lvlData[j][i] = value;
                else if (value == 200)
                    lvlData[j][i] = value;
                else if (value == 250)
                    lvlData[j][i] = value;
                else if (value == 255)
                    lvlData[j][i] = value;
                else if (value < 0)
                    lvlData[j][i] = value;
                else
                    lvlData[j][i] = 0;

//                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

    public static BufferedImage LvlDataToImage(int[][] lvlData){
        BufferedImage lvlImage = new BufferedImage(lvlData[0].length, lvlData.length, BufferedImage.TYPE_INT_RGB);
        for (int j = 0; j < lvlData.length; j++){
            for (int i = 0; i < lvlData[0].length; i++){
                int value = lvlData[j][i];

                if (value >= 0 && value < 48) {
                    lvlImage.setRGB(i, j, new Color(value, (50 + (value * 4)), (value * 5)).getRGB());
                }
                if (value == 100)
                    lvlImage.setRGB(i, j, new Color(value, 0, 0).getRGB());

                if (value == 200)
                    lvlImage.setRGB(i, j, new Color(value, 0, 0).getRGB());

                if (value == 250)
                    lvlImage.setRGB(i, j, new Color(value, 0, 0).getRGB());

//                if (value >= 48)
//                    lvlImage.setRGB(i, j, new Color(11, value, 0).getRGB());
//                lvlImage.setRGB(i, j, new Color(value, value, value).getRGB());
            }
        }
        return lvlImage;
    }

    public static ArrayList<Ball> GetBalls(BufferedImage lvlImage){
        ArrayList<Ball> list = new ArrayList<>();
        for (int j = 0; j < lvlImage.getHeight(); j++){
            for (int i = 0; i < lvlImage.getWidth(); i++){
                Color color = new Color(lvlImage.getRGB(i, j));
                int value = color.getRed();
                if (value == 200)
                    list.add(new Ball(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        }
        return list;
    }

    public static ArrayList<Ball> GetBalls(int[][] lvlData){
        ArrayList<Ball> list = new ArrayList<>();
        for (int j = 0; j < lvlData.length; j++){
            for (int i = 0; i < lvlData[0].length; i++){
                int value = lvlData[i][j];
                if (value == BALL)
                    list.add(new Ball(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        }
        return list;
    }

    public static ArrayList<Coin> GetCoins(BufferedImage img) {
        ArrayList<Coin> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value == 255)
                    list.add(new Coin(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }
        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage lvlImage){
        for (int j = 0; j < lvlImage.getHeight(); j++){
            for (int i = 0; i < lvlImage.getWidth(); i++){
                Color color = new Color(lvlImage.getRGB(i, j));
                int value = color.getRed();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        }
        return new Point(3 * Game.TILES_SIZE, 3 * Game.TILES_SIZE);
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage img) {
        ArrayList<Spike> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value == 250) {
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE));
                }
            }

        return list;
    }
}
