package com.example.jujutsukaisen.abilities.tenshadow_technique;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;

import javax.annotation.Nullable;

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

        return true;
    }
}
