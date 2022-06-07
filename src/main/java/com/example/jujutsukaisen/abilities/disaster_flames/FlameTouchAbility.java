package com.example.jujutsukaisen.abilities.disaster_flames;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class FlameTouchAbility extends PunchAbility {
    public static final FlameTouchAbility INSTANCE = new FlameTouchAbility();

    public FlameTouchAbility()
    {
        super("Flame Touch", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("You touch an entity and make it burn to crisps");
        this.setMaxCooldown(15);
        this.setCursedEnergyCost(10);
        this.setExperiencePoint(5);
        this.setExperienceGainLevelCap(10);

        this.onHitEntityEvent = this::onHitEntity;
    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        target.setSecondsOnFire(10);
        return 10;
    }
}
