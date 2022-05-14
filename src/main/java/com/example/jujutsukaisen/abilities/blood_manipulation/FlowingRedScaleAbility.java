package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
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

    public FlowingRedScaleAbility()
    {
        super("Flowing Red Scale", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user pumps his blood faster and faster in his body which makes him a super human");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(3);

        this.onStartContinuityEvent = this::onStartContinuityEvent;
        this.onEndContinuityEvent = this::onEndContinuityEvent;

    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        propsEntity.alterCursedEnergy(-10);
        player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(RED);
        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(RED);
        player.getAttribute(Attributes.JUMP_STRENGTH).addTransientModifier(RED);
        player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(RED);


        return true;
    }
    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(RED);
        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(RED);
        player.getAttribute(Attributes.JUMP_STRENGTH).removeModifier(RED);
        player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RED);

        return true;
    }
}
