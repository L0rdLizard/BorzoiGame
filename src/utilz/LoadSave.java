package utilz;

import entities.Ball;
import main.Game;
import static utilz.Constants.EnemyConstants.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadSave {
    public static final String PLAYER_ATLAS = "pito_animation_sheet5_1.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MY_MAP = "myMap_enemy.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_IMG = "dark.png";
    public static final String MENU_BACKGROUND_IMG_PLAYING = "dark.png";

    // HP Bar
    public static final String HP_BAR3 = "heart3.png";
    public static final String HP_BAR3_1 = "heart3_1.png";
    public static final String HP_BAR3_2 = "heart3_2.png";
    public static final String HP_BAR3_3 = "heart3_3.png";

    public static final String BALL_ATLAS = "pito_animation_sheet5_1.png";
    public static BufferedImage GetSpriteAtlas(String filename){
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
//        System.out.println(is);
        try {
//            assert is != null;
            image = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return image;
    }

    public static ArrayList<Ball> GetBalls(){
        BufferedImage lvlImage = GetSpriteAtlas(MY_MAP);
        ArrayList<Ball> list = new ArrayList<>();
        for (int j = 0; j < lvlImage.getHeight(); j++){
            for (int i = 0; i < lvlImage.getWidth(); i++){
                Color color = new Color(lvlImage.getRGB(i, j));
                int value = color.getGreen();
                if (value == BALL)
                    list.add(new Ball(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        }
        return list;
    }
    public static int[][] GetLevelData(){
        // TODO
//        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];

        BufferedImage lvlImage = GetSpriteAtlas(MY_MAP);
        int[][] lvlData = new int[lvlImage.getHeight()][lvlImage.getWidth()];

        for (int j = 0; j < lvlImage.getHeight(); j++){
            for (int i = 0; i < lvlImage.getWidth(); i++){
                Color color = new Color(lvlImage.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }
}
