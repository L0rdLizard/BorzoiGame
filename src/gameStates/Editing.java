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


public class Editing extends State implements StateMethods{

    private MenuButton[] editButtons = new MenuButton[6];
    private int[][] lvlData;
    private BufferedImage[] levelSprite;
    BufferedImage[] allLevel = new BufferedImage[4];
    private int choosenBlock = 0;
    private int xIndex = 0;
    private int yIndex = 0;

    // buttons
    private BufferedImage[] imgs;

    private int lvlIndex = 0;
    public Editing(Game game) {
        super(game);
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
        drawOutsideSprites(g);
        highlightChoosenBlock(g);
    }

    private void getAllLvlData(){
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

    private void drawOutsideSprites(Graphics g){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 12; j++){
                int index = j + i*12;
                g.drawImage(levelSprite[index], (224 + (64*j)), (800 + (64*i)), 32, 32, null);
            }
        }
    }

    private void setCurrentEditingLevel(int lvlIndex){
//        this.lvlIndex = lvlIndex;
//        lvlData = levels.get(lvlIndex).getLvlData();
        lvlData = HelpMethods.GetLevelData(allLevel[lvlIndex]);
    }


    public void drawMiniLevel(Graphics g){
        for (int j = 0; j < lvlData.length; j++){
            for (int i = 0; i < lvlData[0].length; i++){
                int index = lvlData[j][i];
                if (index >= 48)
                    index = 0;
                g.drawImage(levelSprite[index], Game.TILES_SIZE / 2 * i + 160, Game.TILES_SIZE / 2 * j + 96, Game.TILES_SIZE / 2, Game.TILES_SIZE / 2, null);
            }
        }
    }

    public void drawGrid(Graphics g){
        g.setColor(Color.darkGray);
        for (int i = 192; i <= 1760; i+=32){
            g.drawLine(i, 96, i,736);
        }
        //(Game.TILES_IN_WIDTH * Game.TILES_SIZE)
        //(Game.TILES_IN_HEIGHT * Game.TILES_SIZE)
        for (int i = 128; i <= 736; i+=32){
            g.drawLine(160, i, 1760, i);
        }
        g.setColor(Color.RED);
        g.drawLine(160, 96, 1760, 96);
        g.drawLine(160, 95, 1760, 95);

        g.drawLine(160, 96, 160, 736);
        g.drawLine(159, 96, 159, 736);

        g.drawLine(160, 736, 1760, 736);
        g.drawLine(160, 735, 1760, 735);

        g.drawLine(1760, 96, 1760, 736);
        g.drawLine(1759, 96, 1759, 736);
    }

    private void drawPanels(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 768, 1920, 576);

        g.setColor(Color.BLACK);
        g.drawLine(0, 768, 1920, 768);
        g.drawLine(0, 767, 1920, 767);
        g.drawLine(0, 766, 1920, 766);
    }


    private void changePixel(int xTile, int yTile, int[][] lvlData, int index){
        lvlData[yTile][xTile] = index;
        System.out.println("x = " + yTile + " y = " + xTile);
        saveLvlToFile();
    }

    private void saveLvlToFile(){
        BufferedImage temp = HelpMethods.LvlDataToImage(lvlData);
        LoadSave.SaveLevel(temp, lvlIndex);
    }

    private void highlightChoosenBlock(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(224 + (64*xIndex), 800 + (64*yIndex), 32, 32);
    }

    //  g.drawImage(levelSprite[index], (224 + (64*j)), (800 + (64*i)), 32, 32, null);
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
        int x = e.getX();
        int y = e.getY();
        if (x >= 160 && x <= 1760 && y >= 96 && y <= 736){
            int xIndex = (x - 160) / 32;
            int yIndex = (y - 96) / 32;
            changePixel(xIndex, yIndex, lvlData, choosenBlock);
        }

        if (x >= 224 && x <= 992 && y >= 800 && y <= 1056){
            if ((x - 224) % 64 <= 32 && (y - 800) % 64 <= 32){
                System.out.println("да");
                xIndex = (x - 224) / 64;
                yIndex = (y - 800) / 64;
                choosenBlock = yIndex * 12 + xIndex;
//                highlightChoosenBlock(xIndex, yIndex);
//                int index = yIndex*12 + xIndex;
//                System.out.println("x = " + xIndex + " y = " + yIndex + " index = " + index);
//                lvlData = HelpMethods.ReplaceTile(lvlData, index);
//                saveLvlToFile();
            }
        }
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
