package gameStates;

import UI.MenuButton;
import levels.Level;
import main.Game;
import utilz.HelpMethods;
import utilz.HelpMethods.*;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Editing extends State implements StateMethods{

    private MenuButton[] editButtons = new MenuButton[6];
    private int[][] lvlData;
    private BufferedImage[] levelSprite;
//    private ArrayList<Level> levels;
    BufferedImage[] allLevel = new BufferedImage[4];
    private int lvlIndex = 2;
    public Editing(Game game) {
        super(game);
//        levels = new ArrayList<>();
//        buildAllLevels();
        getAllLvlData();
        importOutsideSprite();
        setCurrentEditingLevel(lvlIndex);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        drawMiniLevel(g);
        drawGrid(g);
        drawPanels(g);
//        g.setColor(Color.darkGray);
//        g.drawLine(64, 0, 64, (Game.TILES_IN_HEIGHT * Game.TILES_SIZE));
    }

//    private void buildAllLevels() {
//        BufferedImage[] allLevel = LoadSave.GetAllLevels();
//        for (BufferedImage img : allLevel){
//            levels.add(new Level(img));
//        }
//    }

    private void getAllLvlData(){
//        levels = game.getPlaying().getLevelManager().getLevels();
        allLevel = LoadSave.GetAllLevels();
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

    private void setCurrentEditingLevel(int lvlIndex){
        this.lvlIndex = lvlIndex;
//        lvlData = levels.get(lvlIndex).getLvlData();
        lvlData = HelpMethods.GetLevelData(allLevel[lvlIndex]);
    }

//    public void drawMiniLevel(Graphics g){
//        for (int j = 0; j < levels.get(lvlIndex).getLvlData().length; j++){
//            for (int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++){
//                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
//                g.drawImage(levelSprite[index], Game.TILES_SIZE / 2 * i + 160, Game.TILES_SIZE / 2 * j + 160, Game.TILES_SIZE / 2, Game.TILES_SIZE / 2, null);
//            }
//        }
//    }

    public void drawMiniLevel(Graphics g){
        for (int j = 0; j < lvlData.length; j++){
            for (int i = 0; i < lvlData[0].length; i++){
//                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                int index = lvlData[j][i];
                g.drawImage(levelSprite[index], Game.TILES_SIZE / 2 * i + 160, Game.TILES_SIZE / 2 * j + 160, Game.TILES_SIZE / 2, Game.TILES_SIZE / 2, null);
            }
        }
    }

    public void drawGrid(Graphics g){
        g.setColor(Color.darkGray);
        for (int i = 192; i <= 1760; i+=32){
            g.drawLine(i, 160, i,800);
        }
        //(Game.TILES_IN_WIDTH * Game.TILES_SIZE)
        //(Game.TILES_IN_HEIGHT * Game.TILES_SIZE)
        for (int i = 192; i <= 800; i+=32){
            g.drawLine(160, i, 1760, i);
        }
        g.setColor(Color.RED);
        g.drawLine(160, 160, 1760, 160);
        g.drawLine(160, 159, 1760, 159);

        g.drawLine(160, 160, 160, 800);
        g.drawLine(159, 160, 159, 800);

        g.drawLine(160, 800, 1760, 800);
        g.drawLine(160, 799, 1760, 799);

        g.drawLine(1760, 160, 1760, 800);
        g.drawLine(1759, 160, 1759, 800);
    }

    private void drawPanels(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 832, 1920, 576);

        g.setColor(Color.BLACK);
        g.drawLine(0, 833, 1920, 833);
        g.drawLine(0, 832, 1920, 832);
        g.drawLine(0, 831, 1920, 831);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            GameStates.state = GameStates.MENU;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
