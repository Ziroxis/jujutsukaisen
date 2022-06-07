package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import javax.management.Attribute;
import java.util.UUID;

public class FlowingRedScaleAbility extends ContinuousAbility {

    public static final FlowingRedScaleAbility INSTANCE = new FlowingRedScaleAbility();
    public static final AttributeModifier RED = new AttributeModifier(UUID.fromString("7348cda7-da67-4d86-bb9e-68958e100fce"),
            "Red", 3, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier RED_SPEED = new AttributeModifier(UUID.fromString("7348cda7-da67-4d86-bb9e-68958e100fce"),
            "Red", 1, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("d316d3b6-d563-11ec-9d64-0242ac120002"), "Step Height Multiplier for Red", 1, AttributeModifier.Operation.ADDITION);


    public FlowingRedScaleAbility()
    {
        super("Flowing Red Scale", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user pumps his blood faster and faster in his body which makes him a super human");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(4);
        this.setExperiencePoint(15);
        this.setExperienceGainLevelCap(40);

        this.onStartContinuityEvent = this::onStartContinuityEvent;
        this.onEndContinuityEvent = this::onEndContinuityEvent;

    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        propsEntity.alterCursedEnergy(-10);
        player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(RED);
        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(RED_SPEED);
        player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(RED);
        player.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);
        player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(RED);
        player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).addTransientModifier(RED);


        return true;
    }
    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(RED);
        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(RED_SPEED);
        player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(RED);
        player.getAttribute(ModAttributes.STEP_HEIGHT.get()).removeModifier(STEP_HEIGHT);
        player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RED);
        player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).removeModifier(RED);

        return true;
    }
}
