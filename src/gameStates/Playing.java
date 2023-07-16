package gameStates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods{
    private Timer timer;
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;

    private int xLvlOffset;
    private int leftBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int lvlTilesWidth = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffsetX = lvlTilesWidth - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffsetX * Game.TILES_SIZE;

    private int yLvlOffset;
    private int upBorder = (int)(0.6 * Game.GAME_HEIGHT);
    private int downBorder = (int)(0.4 * Game.GAME_HEIGHT);
    private int lvlTilesHeight = LoadSave.GetLevelData().length;
    private int maxTilesOffsetY = lvlTilesHeight - Game.TILES_IN_HEIGHT;
    private int maxLvlOffsetY = maxTilesOffsetY * Game.TILES_SIZE;
    public Playing(Game game){
        super(game);
        initClasses();
    }
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
//        player = new Player(500, 400, (int) (48 * SCALE), (int) (36 * SCALE));
//        player = new Player(200, 300, (int) (64 * Game.SCALE), (int) (48 * Game.SCALE));
        player = new Player(200, 300, (int) (80 * Game.SCALE), (int) (64 * Game.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    }

    @Override
    public void update() {
        levelManager.update();
        player.update();
        enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
        checkCloseToBorder();
        checkCloseToRoof();
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
        levelManager.draw(g, xLvlOffset, yLvlOffset);
        player.render(g, xLvlOffset, yLvlOffset);
        enemyManager.draw(g, xLvlOffset, yLvlOffset);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            player.setAttack(true);
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
            case KeyEvent.VK_BACK_SPACE:
                GameStates.state = GameStates.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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

    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer(){
        return player;
    }

}
