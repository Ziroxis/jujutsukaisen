package com.example.jujutsukaisen.abilities.disaster_tides;

import com.example.jujutsukaisen.abilities.blood_manipulation.BloodShurikenAbility;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.disaster_tides.WaterChargeProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class WaterChargeAbility extends Ability{

    public static final Ability INSTANCE = new WaterChargeAbility();

    public WaterChargeAbility()
    {
        super("Water Charge", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a charge made of compressed water");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(15);
        this.setExperiencePoint(5);
        this.setExperienceGainLevelCap(10);

        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        WaterChargeProjectile projectile = new WaterChargeProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 2f, 1);

        return true;
    }
}
