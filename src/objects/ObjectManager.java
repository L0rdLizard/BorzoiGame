package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gameStates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] coinImgs;
    private BufferedImage spikeImg;
    private ArrayList<Coin> coins;
    private ArrayList<Spike> spikes;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();

        coins = new ArrayList<>();
//        coins.add(new Coin(200, 200, COIN));
    }

    public void checkSpikesTouched(Player p) {
        if (spikes == null)
            return;
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
//        if (coins == null)
//            return;
        for (Coin c : coins)
            if (c.isActive()) {
                if (hitbox.intersects(c.getHitbox())) {
                    c.setActive(false);
                    applyEffectToPlayer(c);
                }
            }
    }

    public void applyEffectToPlayer(Coin c) {
        if (c.getObjType() == COIN)
            playing.increaseCoin(1);
        else if (c.getObjType() == COIN2)
            playing.increaseCoin(2);
        else if (c.getObjType() == COIN3)
            playing.increaseCoin(3);
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
        coins = new ArrayList<>(newLevel.getCoins());
        spikes = newLevel.getSpikes();
    }

    private void loadImgs() {
        BufferedImage coinSprite = LoadSave.GetSpriteAtlas(LoadSave.COIN_ATLAS);
        coinImgs = new BufferedImage[1][4];

        for (int j = 0; j < coinImgs.length; j++)
            for (int i = 0; i < coinImgs[j].length; i++)
                coinImgs[j][i] = coinSprite.getSubimage(16 * i, 16 * j, 16, 16);

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.SPIKES);
    }

    public void update() {
//        for (Potion p : potions)
//            if (p.isActive())
//                p.update();
//
//        for (GameContainer gc : containers)
//            if (gc.isActive())
//                gc.update();
//        if (coins == null)
//            return;
        for (Coin c : coins)
            if (c.isActive())
                c.update();
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
//        drawPotions(g, xLvlOffset);
//        drawContainers(g, xLvlOffset);
        drawCoins(g, xLvlOffset,  yLvlOffset);
        drawTraps(g, xLvlOffset, yLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset, int yLvlOffset) {
        if (spikes == null)
            return;
        for (Spike s : spikes)
            g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset() - yLvlOffset), SPIKE_WIDTH, SPIKE_HEIGHT, null);

    }

    private void drawCoins(Graphics g, int xLvlOffset, int yLvlOffset) {
//        if (coins == null)
//            return;
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