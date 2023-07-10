package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    public LevelManager(Game game){
        this.game = game;
//        levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprite();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importOutsideSprite() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++){
            for (int i = 0; i < 12; i++){
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
            }
        }
    }

//    public void  draw(Graphics g, int lvlOffset, int yLvlOffset){
//        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++){
//            for (int i = 0; i < levelOne.getLvlData()[0].length; i++){
//                int index = levelOne.getSpriteIndex(i, j);
//                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j - yLvlOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
//            }
//        }
//    }
public void  draw(Graphics g, int lvlOffset, int yLvlOffset){
    for (int j = 0; j < levelOne.getLvlData().length; j++){
        for (int i = 0; i < levelOne.getLvlData()[0].length; i++){
            int index = levelOne.getSpriteIndex(i, j);
            g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j - yLvlOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
        }
    }
}

    public void update(){

    }
    public Level getCurrentLevel(){
        return levelOne;
    }
}
