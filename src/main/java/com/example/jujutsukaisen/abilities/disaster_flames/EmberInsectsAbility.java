package com.example.jujutsukaisen.abilities.disaster_flames;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.RepeaterAbility;
import com.example.jujutsukaisen.entities.projectiles.disaster_flames.EmberInsectProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class EmberInsectsAbility extends RepeaterAbility {

    public static final EmberInsectsAbility INSTANCE = new EmberInsectsAbility();

    public EmberInsectsAbility()
    {
        super("Ember Insects", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a huge amount of Ember Insects");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(15);
        this.setMaxRepeaterCount(6, 5);
        this.setExperiencePoint(10);
        this.setExperienceGainLevelCap(10);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        EmberInsectProjectile projectile = new EmberInsectProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 1f);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        return true;
    }
}
