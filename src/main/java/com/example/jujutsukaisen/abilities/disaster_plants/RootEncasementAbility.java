package com.example.jujutsukaisen.abilities.disaster_plants;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class RootEncasementAbility extends PunchAbility {

    public static final RootEncasementAbility INSTANCE = new RootEncasementAbility();

    public RootEncasementAbility()
    {
        super("Root Encasement", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Hit an enemy to root them");
        this.setMaxCooldown(15);
        this.setCursedEnergyCost(10);

        this.onHitEntityEvent = this::onHitEntity;
    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        target.addEffect(new EffectInstance(ModEffects.ROOT.get(), 60, 0));
        return 5;
    }
}
