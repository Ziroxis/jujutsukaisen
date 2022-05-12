package com.example.jujutsukaisen.abilities.disaster_flames;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.disaster_flames.FlameBallProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class FlameBallAbility extends Ability {
    public static final FlameBallAbility INSTANCE = new FlameBallAbility();

    public FlameBallAbility()
    {
        super("Flame Ball", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots an exploding flame ball");
        this.setMaxCooldown(25);
        this.setCursedEnergyCost(20);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        FlameBallProjectile projectile = new FlameBallProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 1f);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        return true;
    }
}
