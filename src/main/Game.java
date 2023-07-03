package main;

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
    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop(){
        gameTread = new Thread(this);
        gameTread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
//        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        while(true){
            long now = System.nanoTime();
            if (now - lastFrame >= timePerFrame){
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("fps: " + frames);
                frames = 0;
            }
        }
    }
}
