package com.example.jujutsukaisen.abilities.disaster_plants;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.RepeaterAbility;
import com.example.jujutsukaisen.entities.projectiles.disaster_plants.CursedBudsProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class CursedBudsAbility extends RepeaterAbility {
    public static final CursedBudsAbility INSTANCE = new CursedBudsAbility();

    public CursedBudsAbility()
    {
        super("Cursed Buds", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Throw a few cursed buds");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(20);
        this.setMaxRepeaterCount(7, 5);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        CursedBudsProjectile projectile = new CursedBudsProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 1f);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        return true;
    }
}
