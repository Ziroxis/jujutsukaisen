package com.example.jujutsukaisen.abilities.disaster_flames;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.DamagedPassiveAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;


public class DisasterFlamesPassive extends DamagedPassiveAbility {
    public static final DisasterFlamesPassive INSTANCE = new DisasterFlamesPassive();

    public DisasterFlamesPassive()
    {
        super("Flame protection", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.hideInGUI(false);
        this.setDescription("Makes you invulnerable to fire");
        this.onDamagedEvent = this::duringPassiveEvent;
    }

    private boolean duringPassiveEvent(LivingEntity livingEntity, DamageSource damageSource) {
        return !damageSource.equals(DamageSource.LAVA) && !damageSource.equals(DamageSource.IN_FIRE) && !damageSource.equals(DamageSource.ON_FIRE);
    }


}
