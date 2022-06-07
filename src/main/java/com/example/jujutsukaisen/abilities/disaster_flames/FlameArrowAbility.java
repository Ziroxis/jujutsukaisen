package com.example.jujutsukaisen.abilities.disaster_flames;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.disaster_flames.FlameArrowProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class FlameArrowAbility extends Ability {
    public static final FlameArrowAbility INSTANCE = new FlameArrowAbility();

    public FlameArrowAbility()
    {
        super("Flame Arrow", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a big flame arrow");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(30);
        this.onUseEvent = this::onUseEvent;
        this.setExperiencePoint(30);
        this.setExperienceGainLevelCap(50);

    }

    private boolean onUseEvent(PlayerEntity player)
    {
        FlameArrowProjectile projectile = new FlameArrowProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 0.5f, 1);

        return true;
    }
}
