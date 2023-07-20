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
    private BufferedImage[] allLevel = new BufferedImage[4];
    private BufferedImage[] pesSprites = new BufferedImage[3];
    private int choosenBlock = 0;
    private int xIndex = 0;
    private int yIndex = 0;

    // buttons
    private BufferedImage[] imgs = new BufferedImage[4];

    private int lvlIndex = 0;
    public Editing(Game game) {
        super(game);
        getAllLvlData();
        importOutsideSprite();
        initPesSprites();
        initButtons();
        setCurrentEditingLevel(lvlIndex);
    }

    private void initPesSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PES);
        for (int i = 0; i < 3; i++){
            pesSprites[i] = img.getSubimage(i*32, 0, 32, 32);
        }
    }

    private void initButtons(){
        imgs[0] = LoadSave.GetSpriteAtlas(LoadSave.EDIT_BUTTONS).getSubimage(0, 0, 140, 56);
        imgs[1] = LoadSave.GetSpriteAtlas(LoadSave.EDIT_BUTTONS).getSubimage(0, 56, 140, 56);
        imgs[2] = LoadSave.GetSpriteAtlas(LoadSave.EDIT_BUTTONS).getSubimage(0, 112, 140, 56);
        imgs[3] = LoadSave.GetSpriteAtlas(LoadSave.EDIT_BUTTONS).getSubimage(0, 168, 140, 56);
//        for (int i = 0; i < 4; i++){
//            editButtons[i] = new MenuButton(imgs[i], 64 + (i*64), 0, 64, 64);
//        }
//        editButtons[4] = new MenuButton(imgs[0], 0, 0, 64, 64);
//        editButtons[5] = new MenuButton(imgs[1], 64, 0, 64, 64);
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
        drawButtons(g);
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

    private void drawButtons(Graphics g){
        for (int i = 0; i < 3; i++){
            g.drawImage(pesSprites[i], (992), (800 + (64*i)), 32, 32, null);
        }

        for (int i = 0; i < 2; i++){
            g.drawImage(imgs[i], (1088 ), 800 + 96 * i, 128, 64, null);
            g.drawImage(imgs[i+2], (1248 ), 800 + 96 * i, 128, 64, null);
        }
    }

    private void setCurrentEditingLevel(int index){
//        this.lvlIndex = lvlIndex;
//        lvlData = levels.get(lvlIndex).getLvlData();
        this.lvlIndex = index;
        getAllLvlData();
        lvlData = HelpMethods.GetLevelData(allLevel[lvlIndex]);
    }


    public void drawMiniLevel(Graphics g){
        for (int j = 0; j < lvlData.length; j++){
            for (int i = 0; i < lvlData[0].length; i++){
                int index = lvlData[j][i];
//                if (index >= 48)
//                    index = 0;
                if (index == 100)
                    g.drawImage(pesSprites[0], Game.TILES_SIZE / 2 * i + 160, Game.TILES_SIZE / 2 * j + 96, Game.TILES_SIZE / 2, Game.TILES_SIZE / 2, null);
                else if (index == 200)
                    g.drawImage(pesSprites[1], Game.TILES_SIZE / 2 * i + 160, Game.TILES_SIZE / 2 * j + 96, Game.TILES_SIZE / 2, Game.TILES_SIZE / 2, null);
                else if (index == 250)
                    g.drawImage(pesSprites[2], Game.TILES_SIZE / 2 * i + 160, Game.TILES_SIZE / 2 * j + 96, Game.TILES_SIZE / 2, Game.TILES_SIZE / 2, null);
                else
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
//        System.out.println("x = " + yTile + " y = " + xTile);
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

    private void clickCheck(int x, int y){
        if (x >= 160 && x <= 1760 && y >= 96 && y <= 736){
            int xIndex = (x - 160) / 32;
            int yIndex = (y - 96) / 32;
            changePixel(xIndex, yIndex, lvlData, choosenBlock);
        }

        if (x >= 224 && x <= 992 && y >= 800 && y <= 1056){
            if ((x - 224) % 64 <= 32 && (y - 800) % 64 <= 32){
                xIndex = (x - 224) / 64;
                yIndex = (y - 800) / 64;
                choosenBlock = yIndex * 12 + xIndex;
            }
        }

        if (x >= 992 && x <= 1024 && y >= 800 && y <= 832){
            xIndex = (x - 224) / 64;
            yIndex = (y - 800) / 64;
            choosenBlock = 100;
        }

        if (x >= 992 && x <= 1024 && y >= 864 && y <= 896){
            xIndex = (x - 224) / 64;
            yIndex = (y - 800) / 64;
            choosenBlock = 200;
        }

        if (x >= 992 && x <= 1024 && y >= 928 && y <= 960){
            xIndex = (x - 224) / 64;
            yIndex = (y - 800) / 64;
            choosenBlock = 250;
        }
//        g.drawImage(imgs[i], (1088 ), 800 + 96 * i, 128, 64, null);
//        g.drawImage(imgs[i+2], (1248 ), 800 + 96 * i, 128, 64, null);
        // level1
        if (x >= 1088 && x <= 1216 && y >= 800 && y <= 864){
            setCurrentEditingLevel(0);
        }
        //level2
        if (x >= 1248 && x <= 1376 && y >= 800 && y <= 864){
            setCurrentEditingLevel(1);
        }
        //level3
        if (x >= 1088 && x <= 1216 && y >= 896 && y <= 960){
            setCurrentEditingLevel(2);
        }
        //level4
        if (x >= 1248 && x <= 1376 && y >= 896 && y <= 960){
            setCurrentEditingLevel(3);
        }
    }

    //  g.drawImage(levelSprite[index], (224 + (64*j)), (800 + (64*i)), 32, 32, null);
    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("mouseClicked");
        int x = e.getX();
        int y = e.getY();
        clickCheck(x, y);
    }



    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        clickCheck(x, y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        clickCheck(x, y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        clickCheck(x, y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameStates.state = GameStates.MENU;
            game.getPlaying().getLevelManager().buildAllLevels();
//            game.getPlaying().getLevelManager().reloadCurrentLevel();
//            game.initClasses();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
