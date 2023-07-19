package utilz;

import entities.Ball;
import main.Game;
import static utilz.Constants.EnemyConstants.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    public static final String PLAYER_ATLAS = "pito_animation_sheet5_1.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
//    public static final String MY_MAP = "2.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
//    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND = "menu_menu.png";
    public static final String MENU_BACKGROUND_IMG = "dark.png";
    public static final String MENU_BACKGROUND_IMG_PLAYING = "dark.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";
    public static final String COIN_ATLAS = "coins.png";
    public static final String SPIKES = "spikes.png";


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

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png"))
                    filesSorted[i] = files[j];

            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return imgs;
    }

    public static void SaveLevel(BufferedImage temp, int lvlIndex) {
        //save BufferedImage to file
        try {
            File outputfile = new File("src/lvls/" + (lvlIndex + 1) + ".png");
            ImageIO.write(temp, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int LoadPlayerCoins() {
        // load coins from bin file from folder gameData
        try {
            FileInputStream fileIn = new FileInputStream("res/gameData/coins.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            int playerCoins = (int) in.readObject();
            in.close();
            fileIn.close();
            return playerCoins;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void SavePlayerCoins(int playerCoins) {
        // save playerCoins to bin file in folder res.gameData
        try {
            FileOutputStream fileOut = new FileOutputStream("res/gameData/coins.bin");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(playerCoins);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
