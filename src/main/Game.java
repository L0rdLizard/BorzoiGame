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
    private final int UPS_SET = 200;
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
    private void updates() {
        gamePanel.updateGame();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
//        long lastFrame = System.nanoTime();
//        long now = System.nanoTime();

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true){
//            now = System.nanoTime();
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

//            if (now - lastFrame >= timePerFrame){
//                gamePanel.repaint();
//                lastFrame = now;
//                frames++;
//            }

            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
