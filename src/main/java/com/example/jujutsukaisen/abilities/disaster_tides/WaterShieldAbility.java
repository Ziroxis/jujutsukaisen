package com.example.jujutsukaisen.abilities.disaster_tides;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class WaterShieldAbility extends ContinuousAbility {

    public static final WaterShieldAbility INSTANCE = new WaterShieldAbility();
    private static final AttributeModifier WATER_RESISTANCE = new AttributeModifier(UUID.fromString("363baf3a-cfc8-11ec-9d64-0242ac120002"),
            "Water Resistance", 3, AttributeModifier.Operation.ADDITION);

    public WaterShieldAbility()
    {
        super("Water Shield", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user gets resistance by enveloping himself with water");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(0);
        this.onStartContinuityEvent = this::onStartContinuityEvent;
        this.onEndContinuityEvent = this::onEndContinuityEvent;
    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        propsEntity.alterCursedEnergy(-30);
        player.getAttribute(Attributes.ARMOR).addTransientModifier(WATER_RESISTANCE);
        return true;
    }
    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.ARMOR).removeModifier(WATER_RESISTANCE);
        return true;
    }


}
