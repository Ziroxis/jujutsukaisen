package com.example.jujutsukaisen.abilities.straw_doll;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.straw_doll.GiantNailProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class GiantNailAbility extends Ability {
    public static final GiantNailAbility INSTANCE = new GiantNailAbility();

    public GiantNailAbility()
    {
        super("Giant Nail", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a giant nail");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(10);
        this.setExperiencePoint(10);
        this.setExperienceGainLevelCap(30);
        this.onUseEvent = this::onUseEvent;

    }

    private boolean onUseEvent(PlayerEntity player)
    {
        GiantNailProjectile projectile = new GiantNailProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 0.5f, 1);

        return true;
    }

}
