package com.xkings.pokemontd.graphics.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.xkings.pokemontd.component.attack.EffectName;
import com.xkings.pokemontd.component.attack.projectile.data.*;

/**
 * User: Seda
 * Date: 2.11.13
 * Time: 11:15
 */

public class AbilityInfo extends GuiBox {
    private final DisplayText header;
    private final DisplayText text;
    private EffectData effect;
    private float speed;
    private float damage;
    private EffectName effectName;
    private float splash;
    private SlowData slow;

    public AbilityInfo(Ui ui, Rectangle rectangle) {
        super(ui, rectangle);
        int headerSize = (int) (height / 5);
        float textBlockSize = height - headerSize;
        text = new DisplayText(ui, new Rectangle(x, y, width, textBlockSize), ui.getFont(), BitmapFont.HAlignment.LEFT,
                true);
        header = new DisplayText(ui, new Rectangle(x, textBlockSize, width, headerSize), ui.getFont(),
                BitmapFont.HAlignment.LEFT, true);
        header.setScale(ui.getFont().getScaleX() * 2);
        refresh();
    }

    public void update(EffectData effect, EffectName effectName, float splash, float speed, float damage) {
        this.effect = effect;
        this.effectName = effectName;
        this.splash = splash;
        this.speed = speed;
        this.damage = damage;
    }

    @Override
    public void render() {
        if (effectName != null) {
            super.render();
            header.render(effectName.name());
            text.render(getDescription(effectName, splash, effect, damage));
        }
    }

    private String getDescription(EffectName effectName, float splash, EffectData abilityCache, float damage) {
        switch (effectName) {
            case Normal:
                return "Deals " + (int) (damage) + " damage.";
            case Splash:
                return "splash ";

            case Wave:
                BubbleData bubble = (BubbleData) abilityCache;
                return "Deals " + damage + " every " + speed + " ms on contact." +
                        (bubble.getGrow() != 1 ? "Bubble grows by " + (int) ((bubble.getGrow() - 1) * 100) + "% per second." : "");

            case Burn:
                DotData data = (DotData) abilityCache;
                return "Deals " + data.getIterations() + "X " + (int) (damage) + " every " + data.getInterval() + " ms.";

            case Entangle:
                SlowData slow = (SlowData) abilityCache;
                return "Has " + (int)(slow.getChance()*100) + "% chance to slow creep by " + (int) (((slow.getSlowRatio())-1) * 100) + "% for " + (int)slow.getDuration() + " seconds.";

            case Peck:
                BonusAttack bonusAttack = (BonusAttack) abilityCache;
                return (int) (bonusAttack.getChance() * 100) + "% chance to deal " + bonusAttack.getIterations() +
                        "X aditional " + (int) damage + " damage";

            case Terrify:
                LifeStealData lifeSteal = (LifeStealData) abilityCache;
                return "Temporary steal " + (int) (damage * 100) + "% of creeps health for " + (int)lifeSteal.getDuration() + "s.";

            case Haste:
                return "Increases speed of nearby towers by " + (int) ((damage - 1) * 100) + "%.";

            case Sunbeam:
                return "sunbeam ";

            case Weaken:
                data = (DotData) abilityCache;
                return "Deals " +  data.getIterations() + "X " + (int) (damage) + " every " + data.getInterval() + " ms.";

            case Freeze:
                slow = (SlowData) abilityCache;
                return "Has " + (int)(slow.getChance()*100) + "% chance to slow creep by " + (int)((slow.getSlowRatio()- 1) * 100) + "% for " + (int)slow.getDuration() + " seconds.";

            case Incinerate:
                IncreasingDamageData increaseDamage = (IncreasingDamageData) abilityCache;
                return "Increases tower damage by " + (int) ((increaseDamage.getDamageIncreasing())) + " for " + (int) ((increaseDamage.getDuration())) + " seconds.";

            case Stomp:
                slow = (SlowData) abilityCache;
                return "Has " + (int)(slow.getChance()* 100) + "% chance to slow creep by " + (int)((slow.getSlowRatio()-1) * 100) + "% for " + (int)slow.getDuration() + " seconds.";

            case Steal:
                MoneyData money = (MoneyData) abilityCache;
                return "Killed creeps earn " + (int) ((money.getEarnRatio() - 1) * 100) + "% more money.";

            case Boulder:
                return " splash";

            case Impair:
                slow = (SlowData) abilityCache;
                return "Has " + (int)(slow.getChance()*100) + "% chance to slow creep by " + (int) (((slow.getSlowRatio())-1) * 100) + "% for " + (int)slow.getDuration() + " seconds.";

            case Charm:
                return "Increases damage of nearby towers by " + (int) ((damage - 1) * 100) + "%.";

            case Shatter:
                return " splash";

            case Blaze:
                bubble = (BubbleData) abilityCache;
                return "Deals " + damage + " every " + speed + " ms on contact." +
                        (bubble.getGrow() != 1 ? "Bubble grows by " + (int) ((bubble.getGrow() - 1) * 100) + "% per second." : "");

            case Corrode:
                return " ";

            case LifeSteal:
                lifeSteal = (LifeStealData) abilityCache;
                return (int) (lifeSteal.getChance() * 100) +
                        "% chance to earn life from killed creep, instead of money.";

            case Infect:
                return "AuraDmg ";

            case SoundWave:
                return " dumbClaw, bubble";

            case Thunderbolt:
                return "Charge ";

            case Quake:
                slow = (SlowData) abilityCache;
                return "Has " + (int)(slow.getChance()*100) + "% chance to slow creep by " + (int) (((slow.getSlowRatio())-1) * 100) + "% for " + (int) slow.getDuration() + " seconds.";

            case Magma:
                return " AuraDmg/Volcano";

            case Puzzle:
                ChangeDirectionData changeDirectionData = (ChangeDirectionData) abilityCache;
                return (int) (changeDirectionData.getChance() * 100) + "% chance to that hit creep changes direction.";
        }
        return "";
    }

    public void reset() {
        this.effect = null;
        this.effectName = null;
    }

    @Override
    public void refresh() {
        super.refresh();
        int headerSize = (int) (height / 5);
        float offset = (int) (height / 7);
        Rectangle innerRectangle = new Rectangle(x + offset, y + offset, width - offset * 2, height - offset * 2);
        float textBlockSize = innerRectangle.height - headerSize;
        text.set(innerRectangle.x, innerRectangle.y, innerRectangle.width, textBlockSize);
        header.set(innerRectangle.x, innerRectangle.y + textBlockSize, innerRectangle.width, headerSize);
        text.refresh();
        header.refresh();
    }
}
