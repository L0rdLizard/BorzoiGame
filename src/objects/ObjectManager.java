package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameStates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] coinImgs;
//    private ArrayList<Potion> potions;
//    private ArrayList<GameContainer> containers;
    private ArrayList<Coin> coins;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();

        coins = new ArrayList<>();
        coins.add(new Coin(200, 200, COIN));
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Coin c : coins)
            if (c.isActive()) {
                if (hitbox.intersects(c.getHitbox())) {
                    c.setActive(false);
                    applyEffectToPlayer(c);
                }
            }
    }

    public void applyEffectToPlayer(Coin p) {
        // TODO
//        if (p.getObjType() == RED_POTION)
//            playing.getPlayer().changeHealth(RED_POTION_VALUE);
//        else
//            playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }

    public void addCoin(int x, int y, int type){
        coins.add(new Coin(x, y, type));
    }

//    public void checkObjectHit(Rectangle2D.Float attackbox) {
//        for (GameContainer gc : containers)
//            if (gc.isActive()) {
//                if (gc.getHitbox().intersects(attackbox)) {
//                    gc.setAnimation(true);
//                    int type = 0;
//                    if (gc.getObjType() == BARREL)
//                        type = 1;
//                    potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
//                    return;
//                }
//            }
//    }

    public void loadObjects(Level newLevel) {
//        potions = newLevel.getPotions();
//        containers = newLevel.getContainers();
//        coins = newLevel.getCoins();
        coins = new ArrayList<>(newLevel.getCoins());
    }

    private void loadImgs() {
        BufferedImage coinSprite = LoadSave.GetSpriteAtlas(LoadSave.COIN_ATLAS);
        coinImgs = new BufferedImage[1][4];

        for (int j = 0; j < coinImgs.length; j++)
            for (int i = 0; i < coinImgs[j].length; i++)
                coinImgs[j][i] = coinSprite.getSubimage(16 * i, 16 * j, 16, 16);

//        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
//        potionImgs = new BufferedImage[2][7];
//
//        for (int j = 0; j < potionImgs.length; j++)
//            for (int i = 0; i < potionImgs[j].length; i++)
//                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
//
//        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
//        containerImgs = new BufferedImage[2][8];
//
//        for (int j = 0; j < containerImgs.length; j++)
//            for (int i = 0; i < containerImgs[j].length; i++)
//                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
    }

    public void update() {
//        for (Potion p : potions)
//            if (p.isActive())
//                p.update();
//
//        for (GameContainer gc : containers)
//            if (gc.isActive())
//                gc.update();

        for (Coin c : coins)
            if (c.isActive())
                c.update();
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
//        drawPotions(g, xLvlOffset);
//        drawContainers(g, xLvlOffset);
        drawCoins(g, xLvlOffset,  yLvlOffset);
    }

    private void drawCoins(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Coin c : coins)
            if (c.isActive()) {
                int type = 0;
                if (c.getObjType() == COIN2)
                    type = 1;
                if (c.getObjType() == COIN3)
                    type = 2;
                type = 0;
                g.drawImage(coinImgs[type][c.getAniIndex()], (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset), (int) (c.getHitbox().y - c.getyDrawOffset() -  yLvlOffset), COIN_WIDTH * 2,
                        COIN_HEIGHT * 2, null);
            }
    }


    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
//        for (Potion p : potions)
//            p.reset();
//
//        for (GameContainer gc : containers)
//            gc.reset();

        for (Coin c : coins)
            c.reset();
    }

}