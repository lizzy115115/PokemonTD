package com.pixelthieves.elementtd.graphics.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pixelthieves.elementtd.App;
import com.pixelthieves.elementtd.Element;
import com.pixelthieves.elementtd.Player;
import com.pixelthieves.elementtd.Treasure;
import com.pixelthieves.elementtd.graphics.ui.menu.Options;
import com.pixelthieves.elementtd.manager.WaveManager;

/**
 * Created by Tomas on 10/8/13.
 */
class ElementIcon extends InteractiveBlock {

    private final DisplayPicture picture;
    private final WaveManager waveManager;
    protected Element element;
    private Player player;
    private final SpriteBatch spriteBatch;

    ElementIcon(Gui ui, Rectangle rectangle, SpriteBatch spriteBatch) {
        super(ui, rectangle);
        this.spriteBatch = spriteBatch;
        picture = new DisplayPicture(ui, rectangle);
        waveManager = ui.getWaveManager();
    }

    @Override
    public void render() {
        if (element != null) {
            int elementCount = player.getReservedElements().getElement(element) + 1;
            if (Treasure.LIMIT.getElement(element) < elementCount) {
                picture.setColor(Color.DARK_GRAY);
                picture.render(App.getAssets().getTexture("gems/" + element.toString().toLowerCase()), "max", true);
            } else {
                picture.setColor(player.getFreeElements() != 0 && elementAllowed() ? Color.WHITE : Color.DARK_GRAY);
                picture.render(App.getAssets().getTexture("gems/" + element.toString().toLowerCase()),
                        "lvl " + elementCount, true);
            }
        }
    }

    private boolean elementAllowed() {
        return (!element.equals(Element.SOUL) || waveManager.isNextWaveLast());
    }

    @Override
    public void process(float x, float y) {
        if (player.getFreeElements() != 0) {
            int elements = player.getReservedElements().getElement(element);
            if (Treasure.LIMIT.getElement(element) > elements && elementAllowed()) {
                float lifeMultiplier = (waveManager.getNextWave() != null ? waveManager.getNextWave() :
                        waveManager.getLastWave()).getHealth();
                waveManager.registerWave(WaveManager.getWave(element, elements), lifeMultiplier, false);
                /*for (Entity creep : wave.getWave()) {
                    Health health = creep.getComponent(HealthComponent.class).getHealth();
                    int newHealth = health.getMaxHealth() * waveType.getHealth();
                    health.setHealth(newHealth, newHealth);
                }   */
                player.getReservedElements().addElement(element, 1);
                player.subtractFreeElement();
                if (!Options.MUTE) {
                    gui.getApp().getAssets().getSound("roar").play();
                }
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    @Override
    public void refresh() {
        picture.set(this);
        picture.refresh();
    }
}