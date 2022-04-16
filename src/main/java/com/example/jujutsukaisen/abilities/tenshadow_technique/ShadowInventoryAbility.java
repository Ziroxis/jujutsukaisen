package com.example.jujutsukaisen.abilities.tenshadow_technique;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import net.minecraft.entity.player.PlayerEntity;

public class ShadowInventoryAbility extends Ability {

    public static final Ability INSTANCE = new ShadowInventoryAbility();

    public ShadowInventoryAbility()
    {
        super("Shadow Inventory", AbilityHelper.getTechniqueCategory());
        this.setDescription("Opens up your shadow inventory");
        this.setMaxCooldown(1);
        this.setCursedEnergyCost(5);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        System.out.println("Check 2");

        return true;
    }
}
