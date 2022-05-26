package com.example.jujutsukaisen.abilities.reverse_cursed_energy;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class HealOtherAbility extends PunchAbility {

    public static final HealOtherAbility INSTANCE = new HealOtherAbility();

    public HealOtherAbility()
    {
        super("Heal Other", AbilityCategories.AbilityCategory.REVERSED);
        this.setDescription("Punch someone to heal that person.");
        this.setCursedEnergyCost(20);
        this.setMaxCooldown(20);
        this.onHitEntityEvent = this::onHitEntityEvent;
    }

    private float onHitEntityEvent(PlayerEntity player, LivingEntity target)
    {
        target.heal(8);
        return 2;
    }
}
