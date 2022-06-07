package com.example.jujutsukaisen.abilities.disaster_plants;

import com.example.jujutsukaisen.abilities.disaster_tides.WaterShieldAbility;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class WoodArmorAbility extends ContinuousAbility {

    public static final WoodArmorAbility INSTANCE = new WoodArmorAbility();
    private static final AttributeModifier WOOD_RESISTANCE = new AttributeModifier(UUID.fromString("3f351d3a-dff2-11ec-9d64-0242ac120002"),
            "Water Resistance", 3, AttributeModifier.Operation.ADDITION);

    public WoodArmorAbility()
    {
        super("Wood Armor", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user gets resistance by enveloping himself with wood");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(3);
        this.setExperiencePoint(10);
        this.setExperienceGainLevelCap(50);

        this.onStartContinuityEvent = this::onStartContinuityEvent;
        this.onEndContinuityEvent = this::onEndContinuityEvent;
    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        propsEntity.alterCursedEnergy(-25);
        player.getAttribute(Attributes.ARMOR).addTransientModifier(WOOD_RESISTANCE);
        return true;
    }
    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.ARMOR).removeModifier(WOOD_RESISTANCE);
        return true;
    }
}
