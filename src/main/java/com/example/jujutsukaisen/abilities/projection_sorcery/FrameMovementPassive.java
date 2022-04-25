package com.example.jujutsukaisen.abilities.projection_sorcery;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.glfw.GLFW;

public class FrameMovementPassive extends PassiveAbility {

    public static final FrameMovementPassive INSTANCE = new FrameMovementPassive();
    boolean hasJumped = false;

    public FrameMovementPassive()
    {
        super("Frame Movement", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The ability to move 24 frames a second makes the user able to make a double jump in the air");
        this.hideInGUI(false);
        this.duringPassiveEvent = this::duringPassiveEvent;
    }

    //TODO make it more fluid and negate fall damage
   private void duringPassiveEvent(PlayerEntity player)
   {
       if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_SPACE) && !player.isOnGround() && player.getDeltaMovement().y < 0.07 && !hasJumped)
       {
           Vector3d speed = Beapi.propulsion(player, 0.1, 0.1);
           player.setDeltaMovement(speed.x, 0.5, speed.z);
           hasJumped = true;
       }
       if (player.isOnGround())
       {
           hasJumped = false;
       }
   }
}
