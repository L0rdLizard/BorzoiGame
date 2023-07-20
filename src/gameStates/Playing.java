package gameStates;

import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements StateMethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;

    private int xLvlOffset;
    private int leftBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    private int yLvlOffset;
    private int upBorder = (int)(0.6 * Game.GAME_HEIGHT);
    private int downBorder = (int)(0.4 * Game.GAME_HEIGHT);
    private int maxLvlOffsetY;

    private boolean gameOver;
    private boolean lvlCompleted = false;

    private BufferedImage backgroundImg;
    public Playing(Game game){
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG_PLAYING);

        calcLvlOffset();
        loadStartLevel();
    }

    public void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);

        player = new Player(200, 300, (int) (80 * Game.SCALE), (int) (64 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }
    public void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    public void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffsetX();
        maxLvlOffsetY = levelManager.getCurrentLevel().getLvlOffsetY();
    }

    @Override
    public void update() {
//        if (paused) {
//            pauseOverlay.update();
//        } else
         if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if (!gameOver) {
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            checkCloseToBorder();
            checkCloseToRoof();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder){
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder){
            xLvlOffset += diff - leftBorder;
        }

        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0){
            xLvlOffset = 0;
        }
    }

    private void checkCloseToRoof() {
        int playerY = (int) player.getHitbox().y;
        int diff = playerY - yLvlOffset;

        if (diff > upBorder){
            yLvlOffset += diff - upBorder;
        } else if (diff < downBorder){
            yLvlOffset += diff - downBorder;
        }

        if(yLvlOffset > maxLvlOffsetY)
            yLvlOffset = maxLvlOffsetY;
        else if (yLvlOffset < 0){
            yLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        levelManager.draw(g, xLvlOffset, yLvlOffset);
        player.render(g, xLvlOffset, yLvlOffset);
        enemyManager.draw(g, xLvlOffset, yLvlOffset);
        objectManager.draw(g, xLvlOffset, yLvlOffset);
        if (gameOver){
            gameOverOverlay.draw(g);
        } else if (lvlCompleted){
            levelCompletedOverlay.draw(g);
        }
    }

    public void createCoin(int x, int y, int type){
        objectManager.addCoin(x, y, type);
    }

    public void increaseCoin(int coins){
        int playerCoins;
        // save player coins to bin file in folder gameData
        playerCoins = LoadSave.LoadPlayerCoins();
        playerCoins += coins;
        LoadSave.SavePlayerCoins(playerCoins);
        System.out.println("Player coins: " + playerCoins);
    }

    public int getCoins(){
        return LoadSave.LoadPlayerCoins();
    }

    public void resetAll() {
        gameOver = false;
//        paused = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkCoinTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }
    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttack(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    GameStates.state = GameStates.MENU;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    player.doubleJump = true;
                    player.jumps++;
                    break;
            }
        }
    }

    public void setLevelCompleted(boolean levelCompleted){
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffsetX(int lvlOffsetX){
        this.maxLvlOffsetX = lvlOffsetX;
    }
    public void setMaxLvlOffsetY(int lvlOffsetY){
        this.maxLvlOffsetY = lvlOffsetY;
    }

    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer(){
        return player;
    }
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }
    public ObjectManager getObjectManager() {
        return objectManager;
    }
    public LevelManager getLevelManager(){
        return levelManager;
    }

}
