package com.example.jujutsukaisen.abilities.reverse_cursed_energy;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import net.minecraft.entity.player.PlayerEntity;

public class SelfHealingAbility extends Ability {

    public static final Ability INSTANCE = new SelfHealingAbility();

    public SelfHealingAbility()
    {
        super("Self Healing", AbilityCategories.AbilityCategory.REVERSED);
        this.setDescription("Heals yourself for a bit of health instantly using reversed cursed energy");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(30);
        this.onUseEvent = this::onUseEvent;

    }

    private boolean onUseEvent(PlayerEntity player)
    {
        player.heal(7);

        return true;
    }
}
