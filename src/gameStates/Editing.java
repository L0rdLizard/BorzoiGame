package gameStates;

import UI.MenuButton;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Editing extends State implements StateMethods{

    private MenuButton[] editButtons = new MenuButton[6];
    private int[][] grid = new int[50][Game.TILES_IN_HEIGHT];
    public Editing(Game game) {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

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
