package com.example.jujutsukaisen.abilities.disaster_tides;

import com.example.jujutsukaisen.abilities.blood_manipulation.BloodMeteoriteAbility;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.RepeaterAbility;
import com.example.jujutsukaisen.entities.projectiles.disaster_tides.WaterFlowProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class WaterFlowAbility extends RepeaterAbility {

    public static final Ability INSTANCE = new WaterFlowAbility();

    public WaterFlowAbility()
    {
        super("Water Flow", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user fires multiple densely compressed droplets of watter");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(20);
        this.setMaxRepeaterCount(5, 5);
        this.setExperiencePoint(10);
        this.setExperienceGainLevelCap(30);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        WaterFlowProjectile projectile = new WaterFlowProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 0.5f);

        return true;
    }
}
