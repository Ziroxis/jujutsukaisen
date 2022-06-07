package com.example.jujutsukaisen.abilities.projection_sorcery;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.init.ModAttributes;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;


public class FrameSpeedAbility extends ContinuousAbility {

    public static final FrameSpeedAbility INSTANCE = new FrameSpeedAbility();

    private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("1d68a133-8a0e-4b8f-8790-1360007d4741"), "Step Height Multiplier", 1, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier FRAME_SPEED_SIMPLE = new AttributeModifier(UUID.fromString("e3ae074c-40a9-49ff-aa3b-7cc9b98ddc2d"), "Frame Speed simple", 3, AttributeModifier.Operation.ADDITION);
    public FrameSpeedAbility()
    {
        super("Frame Speed", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user starts moving in 24 frames a second and causes him to move extremely fast");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(3);
        this.onStartContinuityEvent = this::onStartContinuityEvent;
        this.duringContinuityEvent = this::duringContinuityEvent;
        this.onEndContinuityEvent = this::onEndContinuityEvent;
        this.setExperiencePoint(5);
        this.setExperienceGainLevelCap(10);

    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(FRAME_SPEED_SIMPLE);
        player.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);

        return true;
    }

    //Stolen code from forge forums
    private void duringContinuityEvent(PlayerEntity player, int time)
    {
        World world = player.level;

        if (world.getBlockState(player.blockPosition().below()).getFluidState().isSource())
        {
            player.setOnGround(true);
            if (player.getDeltaMovement().y <= 0.0D && player.getDeltaMovement().y < 0.009)
            {
                double ySpeed = 0 - player.getDeltaMovement().y;
                if (ySpeed > 0.008)
                    ySpeed = 0.008;
                player.setDeltaMovement(player.getDeltaMovement().x, ySpeed, player.getDeltaMovement().z);
                player.fallDistance = 0.0F;

            }
        }


    }
    private boolean onEndContinuityEvent(PlayerEntity player)
    {
        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(FRAME_SPEED_SIMPLE);
        player.getAttribute(ModAttributes.STEP_HEIGHT.get()).removeModifier(STEP_HEIGHT);
        return true;
    }
}
