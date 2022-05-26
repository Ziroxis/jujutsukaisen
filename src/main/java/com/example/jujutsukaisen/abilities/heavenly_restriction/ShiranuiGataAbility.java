package com.example.jujutsukaisen.abilities.heavenly_restriction;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class ShiranuiGataAbility extends ContinuousAbility {

    public static final ShiranuiGataAbility INSTANCE = new ShiranuiGataAbility();

    public ShiranuiGataAbility()
    {
        super("Shiranui Gata", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Take a specific pose where the next physical attack gets deflected and doubled");
        this.setMaxCooldown(5);
        this.duringContinuityEvent = this::duringContinuityEvent;
        this.onEndContinuityEvent = this::onEndContinuityEvent;
    }

    private void duringContinuityEvent(PlayerEntity player, int time)
    {
        if (!player.hasEffect(ModEffects.MOVEMENT_BLOCKED.get()))
            player.addEffect(new EffectInstance(ModEffects.MOVEMENT_BLOCKED.get(), 40, 10));
    }

    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        if (player.hasEffect(ModEffects.MOVEMENT_BLOCKED.get()))
            player.removeEffect(ModEffects.MOVEMENT_BLOCKED.get());

        return true;
    }
}
