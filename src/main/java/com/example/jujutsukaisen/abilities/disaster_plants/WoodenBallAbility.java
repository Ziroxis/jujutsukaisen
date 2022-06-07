package com.example.jujutsukaisen.abilities.disaster_plants;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.disaster_plants.WoodenBallProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class WoodenBallAbility extends Ability {
    public static final WoodenBallAbility INSTANCE = new WoodenBallAbility();

    public WoodenBallAbility()
    {
        super("Wooden Ball", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a big wooden ball");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(15);
        this.setExperiencePoint(10);
        this.setExperienceGainLevelCap(30);

        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        WoodenBallProjectile projectile = new WoodenBallProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 0.5f, 1);

        return true;
    }
}
