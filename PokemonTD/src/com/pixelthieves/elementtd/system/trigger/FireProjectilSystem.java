package com.pixelthieves.elementtd.system.trigger;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.math.Vector3;
import com.pixelthieves.core.component.PositionComponent;
import com.pixelthieves.elementtd.App;
import com.pixelthieves.elementtd.component.attack.effects.buff.BuffableDamageComponent;
import com.pixelthieves.elementtd.component.attack.effects.buff.BuffableSpeedComponent;
import com.pixelthieves.elementtd.component.attack.projectile.HitAbility;
import com.pixelthieves.elementtd.entity.Projectile;
import com.pixelthieves.elementtd.graphics.ui.menu.Options;
import com.pixelthieves.elementtd.system.resolve.FirstCreepSystem;

/**
 * Created by Tomas on 10/4/13.
 */
public class FireProjectilSystem extends ApplyAbilitySystem<HitAbility> {

    @Mapper
    ComponentMapper<BuffableSpeedComponent> speedMapper;
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<BuffableDamageComponent> damageMapper;


    public FireProjectilSystem() {
        //super(HitAbility.class, ClosestCreepSystem.class);
        super(HitAbility.class, FirstCreepSystem.class);
    }

    @Override
    protected void processTarget(HitAbility ability, Entity entity, Entity target) {
        Vector3 position = positionMapper.get(entity).getPoint();
        float speed = speedMapper.get(entity).getSpeed();
        float damage = damageMapper.get(entity).getDamage();

        Vector3 closestEnemyPosition = positionMapper.get(target).getPoint();

        createProjectile(entity, ability, position, damage, speed, closestEnemyPosition, target);
    }

    public boolean createProjectile(Entity entity, HitAbility projectileType, Vector3 position, float damage,
                                    float speed, Vector3 targetPosition, Entity target) {
        switch (projectileType.getType()) {
            case PASS_THROUGH:
                Vector3 direction = targetPosition.cpy().sub(position).nor().scl(5 * App.WORLD_SCALE).add(position);
                Projectile.registerProjectile(world, entity, projectileType, position.x, position.y, damage, speed,
                        direction, target);
                break;
            case PASS_THROUGH_ALL_DIRECTIONS:float ang = (float) Math.atan2(targetPosition.y - position.y, targetPosition.x - position.x);
                int iterations = 16;
                float step = (float) (Math.PI*2/iterations);
                for (int i = 0; i < iterations; i++) {
                    Vector3 point  = new Vector3();
                    point.x = position.x + (float) (Math.cos(ang+i*step)* 5 * App.WORLD_SCALE);
                    point.y = position.y + (float) (Math.sin(ang+i*step)* 5 * App.WORLD_SCALE);
                    Projectile.registerProjectile(world, entity, projectileType, position.x, position.y, damage, speed,
                            point, target);

                }
                break;

            case FOLLOW_TARGET:
            case IMMEDIATE_ATTACK:
            case IMMEDIATE_NOCONTACT_DAMAGE:
                Projectile.registerProjectile(world, entity, projectileType, position.x, position.y, damage, speed,
                        targetPosition, target);
                break;
        }
        playSound();
        return true;
    }

    private void playSound() {
        if (!Options.MUTE) {
            App.getAssets().getSound("shot").play();
        }
    }

}
