package com.example.jujutsukaisen.abilities.disaster_tides;


import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class DisasterTidesPassive extends PassiveAbility {
    private static final AttributeModifier SWIM_SPEED = new AttributeModifier(UUID.fromString("a11440ee-5d84-4c36-960b-992e13b66aff"), "Fishman Speed Multiplier", 1.8, AttributeModifier.Operation.MULTIPLY_BASE);
    public static final DisasterTidesPassive INSTANCE = new DisasterTidesPassive();
    boolean hasJumped = false;

    public DisasterTidesPassive()
    {
        super("Ocean protection", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Makes you able to swim faster and breathe under water");
        this.hideInGUI(false);
        this.duringPassiveEvent = this::duringPassiveEvent;
    }

    private void duringPassiveEvent(PlayerEntity player)
    {
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if(!player.getAttribute(ForgeMod.SWIM_SPEED.get()).hasModifier(SWIM_SPEED))
            player.getAttribute(ForgeMod.SWIM_SPEED.get()).addTransientModifier(SWIM_SPEED);

        if (player.isInWater())
            player.setAirSupply(300);
    }
}
