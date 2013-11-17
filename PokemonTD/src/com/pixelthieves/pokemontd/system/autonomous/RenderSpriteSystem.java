package com.pixelthieves.pokemontd.system.autonomous;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.pixelthieves.core.component.PositionComponent;
import com.pixelthieves.core.component.RotationComponent;
import com.pixelthieves.core.component.SizeComponent;
import com.pixelthieves.pokemontd.Animation;
import com.pixelthieves.pokemontd.component.SpriteComponent;
import com.pixelthieves.pokemontd.component.TintComponent;
import com.pixelthieves.pokemontd.component.VisibleComponent;

import java.util.*;

/**
 * Created by Tomas on 10/4/13.
 */
public class RenderSpriteSystem extends EntitySystem {
    private final Camera camera;
    private final SpriteBatch spriteBatch;

    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<SizeComponent> sizeMapper;
    @Mapper
    ComponentMapper<RotationComponent> rotationMapper;
    @Mapper
    ComponentMapper<SpriteComponent> spriteMapper;
    @Mapper
    ComponentMapper<TintComponent> tintMapper;
    @Mapper
    ComponentMapper<VisibleComponent> visibleMapper;

    public RenderSpriteSystem(Camera camera, SpriteBatch spriteBatch) {
        super(Aspect.getAspectForAll(PositionComponent.class, SizeComponent.class, SpriteComponent.class));
        this.camera = camera;
        this.spriteBatch = spriteBatch;
    }

    protected void process(Entity e) {
        if (visibleMapper.has(e) && !visibleMapper.get(e).isVisible()) {
            return;
        }

        PositionComponent positionComponent = positionMapper.get(e);
        Vector3 size = sizeMapper.get(e).getPoint();

        for (Animation animation : spriteMapper.get(e).get()) {
            if (animation != null && animation.hasNext()) {
                TextureAtlas.AtlasRegion sprite = animation.next();
                float x = positionComponent.getPoint().x - size.x / 2f;
                float y = positionComponent.getPoint().y - size.y / 2f;
                spriteBatch.setColor(tintMapper.has(e) ? tintMapper.get(e).getTint() : Color.WHITE);
                if (rotationMapper.has(e)) {
                    RotationComponent rotationComponent = rotationMapper.get(e);
                    Vector3 rotation = rotationComponent.getPoint();
                    Vector3 origin = rotationComponent.getOrigin();
                    if (origin.equals(Vector3.Zero)) {
                        spriteBatch.draw(sprite, x, y, size.x / 2f, size.y / 2f, size.x, size.y, 1f, 1f, rotation.x);
                    } else {
                        float originX = size.x * origin.x;
                        float originY = size.y * origin.y;
                        // FIXME this I have no idea how this works.
                        spriteBatch.draw(sprite, x + size.x * origin.y, y + size.y * origin.x, originX, originY, size.x,
                                size.y, 1f, 1f, rotation.x);
                    }
                    // spriteBatch.draw(sprite, x, y, size.x / 2f, size.y / 2f, size.x, size.y, 1f, 1f, rotation.x);
                } else {
                    spriteBatch.draw(sprite, x, y, size.x, size.y);
                }
            }
        }

    }

    @Override
    protected void end() {
        spriteBatch.setColor(Color.WHITE);
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        List<PositionComponent> positions = new ArrayList<PositionComponent>(entities.size());
        Map<PositionComponent, Entity> map = new HashMap<PositionComponent, Entity>();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            PositionComponent position = positionMapper.get(entity);
            positions.add(position);
            map.put(position, entity);
        }
        Collections.sort(positions);
        for (PositionComponent position : positions) {
            process(map.get(position));
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }
}