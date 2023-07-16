package gameStates;

import UI.MenuButton;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods{
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg, backgroundImgDark;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundImgDark = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150*Game.SCALE), 0, GameStates.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220*Game.SCALE), 1, GameStates.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290*Game.SCALE), 2, GameStates.QUIT);
    }

    @Override
    public void update() {
        for(MenuButton mb: buttons){
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgDark, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        for(MenuButton mb: buttons){
            mb.draw(g);
        }
//        g.setColor(Color.black);
//        g.drawString("PITO ADVENTURE",game.GAME_WIDTH/2, 200);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb: buttons){
            if (isIn(e, mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb: buttons){
            if (isIn(e, mb)){
                if(mb.isMousePressed()){
                    mb.applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb: buttons){
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb: buttons){
            mb.setMouseOver(false);
        }
        for(MenuButton mb: buttons){
            if(isIn(e, mb)){
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            GameStates.state = GameStates.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
