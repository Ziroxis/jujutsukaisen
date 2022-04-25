package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.interfaces.IParallelContinuousAbility;
import com.example.jujutsukaisen.api.ability.sorts.ItemAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class BloodEdgeAbility extends ItemAbility implements IParallelContinuousAbility {

    public static final Ability INSTANCE = new BloodEdgeAbility();

    public BloodEdgeAbility()
    {
        super("Blood Edge", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Creates a sharp blade made out of solid blood");
        this.setMaxCooldown(0);
        this.setCursedEnergyCost(0);
        this.onStartContinuityEvent = this::onStartContinuityEvent;
    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        IEntityStats props = EntityStatsCapability.get(player);
        props.alterCursedEnergy(-10);

        return true;
    }
    @Override
    public ItemStack getItemStack(PlayerEntity player)
    {
        return new ItemStack(ModItems.BLOOD_EDGE.get());
    }

    @Override
    public boolean canBeActive(PlayerEntity player) {
        return true;
    }
}
