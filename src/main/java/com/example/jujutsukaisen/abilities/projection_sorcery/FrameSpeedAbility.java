package com.example.jujutsukaisen.abilities.projection_sorcery;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;


public class FrameSpeedAbility extends ContinuousAbility {

    public static final FrameSpeedAbility INSTANCE = new FrameSpeedAbility();

    private static final AttributeModifier FRAME_SPEED_SIMPLE = new AttributeModifier(UUID.fromString("e3ae074c-40a9-49ff-aa3b-7cc9b98ddc2d"), "Frame Speed simple", 5, AttributeModifier.Operation.ADDITION);
    public FrameSpeedAbility()
    {
        super("Frame Speed", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user starts moving in 24 frames a second and causes him to move extremely fast");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(3);
        this.onStartContinuityEvent = this::onStartContinuityEvent;
        this.onEndContinuityEvent = this::onEndContinuityEvent;
    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(FRAME_SPEED_SIMPLE);

        return true;
    }
    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(FRAME_SPEED_SIMPLE);
        return true;
    }
}
