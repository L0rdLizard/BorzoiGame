package levels;

import gameStates.GameStates;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;
    public LevelManager(Game game){
        this.game = game;
        importOutsideSprite();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel(){
        lvlIndex++;
        if (lvlIndex >= levels.size()){
            lvlIndex = 0;
            System.out.println("No more levels");
            GameStates.state = GameStates.MENU;
        }
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
//        game.getPlaying().getObjectManager().loadObjects(newLevel);
        game.getPlaying().setMaxLvlOffsetX(newLevel.getLvlOffsetX());
        game.getPlaying().setMaxLvlOffsetY(newLevel.getLvlOffsetY());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    private void buildAllLevels() {
        int index = 0;
        BufferedImage[] allLevel = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevel){
            levels.add(new Level(img));
            index++;
        }
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


    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        for (int j = 0; j < levels.get(lvlIndex).getLvlData().length; j++){
            for (int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++){
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                if (index >= 48 )
                    index = 0;
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - xLvlOffset, Game.TILES_SIZE * j - yLvlOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update(){

    }
    public Level getCurrentLevel(){
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels(){
        return levels.size();
    }
    public ArrayList<Level> getLevels(){
        return levels;
    }
}
