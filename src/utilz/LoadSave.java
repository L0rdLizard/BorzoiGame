package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "pito_animation_run_sheet4.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
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

    public static int[][] GetLevelData(){
        //TODO remake level loading
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage lvlImage = GetSpriteAtlas(LEVEL_ONE_DATA);
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
