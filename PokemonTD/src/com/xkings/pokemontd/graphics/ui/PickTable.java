package com.xkings.pokemontd.graphics.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.xkings.pokemontd.entity.tower.TowerType;
import com.xkings.pokemontd.manager.TowerManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tomas on 10/27/13.
 */
public abstract class PickTable<E extends InteractiveBlock> extends GuiBox {

    protected final SpriteBatch spriteBatch;
    protected final List<E> pickIcons;

    PickTable(Rectangle rectangle, int offset, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        super(rectangle, offset, shapeRenderer);
        this.spriteBatch = spriteBatch;
        pickIcons = createPickIcons(3);
    }

    private List<E> createPickIcons(int count) {
        List<E> pickIcons = new ArrayList<E>();
        for (int i = 0; i < 9; i++) {
            pickIcons.add(createPickIcon(i, count));
        }
        return pickIcons;
    }

    private E createPickIcon(int position,  int count) {
        Vector2 size = new Vector2(width / count, height / count);
        int x = (int) (this.x + position % count * size.x);
        int y = (int) (this.y + position / count * size.y);
        return createPickIconInstance(x, height - y - size.y, size.x, size.y);
    }

    protected abstract E createPickIconInstance(float x, float y, float w, float h);

    @Override
    public boolean hit(float x, float y) {
        for (Clickable clickable : pickIcons) {
            if(clickable.hit(x, y)){
                return true;
            }
        }
        return false;
    }


    @Override
    public void render() {
        super.render();
        for (E e : pickIcons) {
            e.render();
        }
    }
}