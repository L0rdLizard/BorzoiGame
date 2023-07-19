package inputs;

import gameStates.GameStates;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    private boolean firstEdit = true, firstPlaying = true;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.state){
//            case MENU:
//                gamePanel.getGame().getMenu().mouseClicked(e);
//                break;
            case PLAYING:
                if (firstPlaying){
                    firstPlaying = false;
                    break;
                }
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            case EDIT:
                if (firstEdit){
                    firstEdit = false;
                    break;
                }
                gamePanel.getGame().getEditing().mouseClicked(e);
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.state){
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                if (firstPlaying){
                    firstPlaying = false;
                    break;
                }
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getEditing().mousePressed(e);
            case EDIT:
                if (firstEdit){
                    firstEdit = false;
                    break;
                }
                gamePanel.getGame().getEditing().mousePressed(e);
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                if (firstPlaying){
                    firstPlaying = false;
                    break;
                }
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getEditing().mouseReleased(e);
            case EDIT:
                if (firstEdit){
                    firstEdit = false;
                    break;
                }
                gamePanel.getGame().getEditing().mouseReleased(e);
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        gamePanel.setNosPos(e.getX(), e.getY()-40);
        switch (GameStates.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getEditing().mouseMoved(e);
            case EDIT:
                gamePanel.getGame().getEditing().mouseMoved(e);
            default:
                break;
        }
    }
}
