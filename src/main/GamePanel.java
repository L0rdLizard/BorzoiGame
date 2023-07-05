package main;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private KeyBoardInputs keyboardInputs;
    private MouseInputs mouseInputs;
    private Game game;
    public GamePanel(Game game){
        keyboardInputs = new KeyBoardInputs(this);
        mouseInputs = new MouseInputs(this);

        this.game = game;

        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
//        setMinimumSize(size);
        setPreferredSize(size);
//        setMaximumSize(size);
        System.out.println("size: " + GAME_WIDTH + "/"+ GAME_HEIGHT);
    }

    public void updateGame(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }
    public Game getGame(){
        return game;
    }
}
