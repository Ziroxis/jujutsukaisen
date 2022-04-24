package com.example.jujutsukaisen.abilities.projection_sorcery;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class FrameBreakAbility extends PunchAbility {

    public static final FrameBreakAbility INSTANCE = new FrameBreakAbility();

    public FrameBreakAbility()
    {
        super("Frame Break", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Instantly makes you stop and gives you enough power to punch one enemy for massive damage");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(2);

        this.duringContinuityEvent = this::duringContinuityEvent;
        this.onHitEntityEvent = this::onHitEntityEvent;
    }

    private void duringContinuityEvent(PlayerEntity player, int i)
    {
        if (!player.hasEffect(Effects.MOVEMENT_SLOWDOWN))
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 40, 10));

    }

    private float onHitEntityEvent(PlayerEntity player, LivingEntity target)
    {
        player.removeEffect(Effects.MOVEMENT_SLOWDOWN);

        return 20;
    }
}
