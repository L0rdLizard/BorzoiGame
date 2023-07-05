package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "pito_animation_run_idle_sheet.png";
    public static BufferedImage GetSpriteAtlas(String filename){
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        System.out.println(is);
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
}
