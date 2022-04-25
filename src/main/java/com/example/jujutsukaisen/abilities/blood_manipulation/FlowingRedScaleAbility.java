package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class FlowingRedScaleAbility extends ContinuousAbility {

    public static final FlowingRedScaleAbility INSTANCE = new FlowingRedScaleAbility();

    public FlowingRedScaleAbility()
    {
        super("Flowing Red Scale", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user pumps his blood faster and faster in his body which makes him a super human");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(4);

        this.duringContinuityEvent = this::duringContinuity;
        this.onEndContinuityEvent = this::onEndContinuityEvent;

    }

    private void duringContinuity(PlayerEntity player, int timer)
    {
        if (!player.hasEffect(Effects.DAMAGE_BOOST))
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 60, 1));
        if (!player.hasEffect(Effects.DIG_SPEED))
            player.addEffect(new EffectInstance(Effects.DIG_SPEED, 60, 1));
        if (!player.hasEffect(Effects.MOVEMENT_SPEED))
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 60, 2));
    }

    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        player.removeEffect(Effects.DAMAGE_BOOST);
        player.removeEffect(Effects.MOVEMENT_SPEED);
        player.removeEffect(Effects.DIG_SPEED);

        return true;
    }
}
