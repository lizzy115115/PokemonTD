package com.xkings.pokemontd.system.autonomous;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector3;
import com.xkings.core.component.PositionComponent;
import com.xkings.core.component.RotationComponent;
import com.xkings.core.component.TargetComponent;

/**
 * Created by Tomas on 10/4/13.
 */
public class TargetingSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<RotationComponent> rotationMapper;
    @Mapper
    ComponentMapper<TargetComponent> targetMapper;
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;


    public TargetingSystem() {
        super(Aspect.getAspectForAll(TargetComponent.class, PositionComponent.class, RotationComponent.class));
    }

    @Override
    protected void process(Entity e) {
        Vector3 a = positionMapper.get(e).getPoint();
        Vector3 b = positionMapper.get(targetMapper.get(e).getTarget()).getPoint();
        rotationMapper.get(e).getPoint().x = (float) (Math.atan2(b.y - a.y, b.x - a.x) / Math.PI * 180f);
    }
}