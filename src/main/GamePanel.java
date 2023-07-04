package main;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;


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
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
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
