package com.xkings.pokemontd.component.attack.projectile.data;

import com.xkings.pokemontd.component.attack.EffectName;

/**
 * Created by Tomas on 10/13/13.
 */
public class NormalData extends EffectData {
    public String getEffectDescription(EffectName effectName, float speed, float damage) {
        throw new IllegalStateException("This method should not be invoked on NormalData");
    }
}
