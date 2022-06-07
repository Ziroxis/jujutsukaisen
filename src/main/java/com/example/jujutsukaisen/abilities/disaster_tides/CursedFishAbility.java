package com.example.jujutsukaisen.abilities.disaster_tides;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.disaster_tides.CursedFishProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class CursedFishAbility extends Ability{

    public static final CursedFishAbility INSTANCE = new CursedFishAbility();

    public CursedFishAbility()
    {
        super("Cursed Fish", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a cursed fish");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(15);
        this.setExperiencePoint(5);
        this.setExperienceGainLevelCap(30);

        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        CursedFishProjectile projectile = new CursedFishProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 0.5f, 1);

        return true;
    }
}
