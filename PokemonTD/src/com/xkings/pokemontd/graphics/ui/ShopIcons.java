package com.xkings.pokemontd.graphics.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.xkings.pokemontd.Element;
import com.xkings.pokemontd.Player;
import com.xkings.pokemontd.entity.tower.TowerType;

import java.util.List;

/**
 * Created by Tomas on 10/27/13.
 */
public class ShopIcons extends PickTable<ElementIcon> {

    private final Player player;
    private List<TowerType> lastHierarchy;

    ShopIcons(Ui ui,Rectangle rectangle) {
        super(ui, rectangle);
        this.player = ui.getPlayer();
        for (int i = 0; i < pickIcons.size(); i++) {
            ElementIcon elementIcon = pickIcons.get(i);
            elementIcon.setPlayer(player);
            elementIcon.setElement(i < Element.values().length ? Element.values()[i] : null);
        }
    }

    @Override
    protected ElementIcon createPickIconInstance(float x, float y, float w, float h) {
        return new ElementIcon(ui,new Rectangle(x, y, w, h), spriteBatch);
    }

    @Override
    public void render() {
        super.render();
    }
}
