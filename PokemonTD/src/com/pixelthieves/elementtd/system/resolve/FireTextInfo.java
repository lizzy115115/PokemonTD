package com.pixelthieves.elementtd.system.resolve;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.pixelthieves.core.component.PositionComponent;
import com.pixelthieves.elementtd.component.ShopComponent;
import com.pixelthieves.elementtd.entity.TextInfo;

public class FireTextInfo extends EntityProcessingSystem {
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;

    private String text;
    private Color color;

    /**
     * Finds desired entity by given type and fires text over it.
     *
     * @param type of the entity.
     */
    public FireTextInfo(Class<ShopComponent> type) {
        super(Aspect.getAspectForAll(type));
    }


    @Override
    protected void process(Entity e) {
        Vector3 position = positionMapper.get(e).getPoint();
        TextInfo.registerTextInfo(world, text, color, position.x, position.y);
    }

    public void fireText(String text, Color info) {
        this.text = text;
        this.color = info;
        process();
    }
}

