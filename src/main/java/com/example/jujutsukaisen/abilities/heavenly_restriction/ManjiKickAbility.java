package com.example.jujutsukaisen.abilities.heavenly_restriction;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class ManjiKickAbility extends PunchAbility {

    public static final ManjiKickAbility INSTANCE = new ManjiKickAbility();

    public ManjiKickAbility()
    {
        super("Manji Kick", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Kick the enemy, completely stunning him");
        this.setCursedEnergyCost(0);
        this.setMaxCooldown(5);

        this.onHitEntityEvent = this::onHitEntityEvent;
    }



    private float onHitEntityEvent(PlayerEntity player, LivingEntity target)
    {
        target.addEffect(new EffectInstance(ModEffects.SLEEP.get(), 80, 10));

        return 25;
    }
}
