package main;

import entities.Player;

import gameStates.Editing;
import gameStates.GameStates;
import gameStates.Menu;
import gameStates.Playing;
import levels.LevelManager;
import utilz.LoadSave;

import java.awt.Graphics;

//public class Game{
//    private GameWindow gameWindow;
//    private GamePanel gamePanel;
//    private Thread gameTread;
//    private final int FPS_SET = 120;
//    public Game(){
//        gamePanel = new GamePanel();
//        gameWindow = new GameWindow(gamePanel);
//        gamePanel.requestFocus();
//    }
//}
public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameTread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;

    private Playing playing;
    private Menu menu;
    private Editing editing;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f;
//    public final static int TILES_IN_WIDTH = 26;
//    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_IN_WIDTH = 30;
    public final static int TILES_IN_HEIGHT = 17;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public Game(){
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();

    }

    public void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        editing = new Editing(this);
    }

    private void startGameLoop(){
        gameTread = new Thread(this);
        gameTread.start();
    }
    private void updates() {
        switch (GameStates.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case QUIT:
                System.exit(0);
                break;
            case OPTIONS:
//                System.exit(0);
//                editing.update();
                break;
            case EDIT:
                editing.update();
                break;
            default:
                break;
        }
    }
    public void render(Graphics g){
        switch (GameStates.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTIONS:
//                editing.draw(g);
                break;
            case EDIT:
                editing.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true){
            long currentTime = System.nanoTime();

            deltaU +=(currentTime - previousTime) / timePerUpdate;
            deltaF +=(currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1){
                updates();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost(){
        if(GameStates.state == GameStates.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }
    public Editing getEditing(){
        return editing;
    }

}
