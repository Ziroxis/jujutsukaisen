package com.example.jujutsukaisen.abilities.disaster_tides;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.disaster_tides.CursedSharkProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;

public class CursedSharkAbility extends Ability{

    public static final Ability INSTANCE = new CursedSharkAbility();

    public CursedSharkAbility()
    {
        super("Cursed Shark", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a shark.");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(15);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        RayTraceResult mop = Beapi.rayTraceBlocksAndEntities(player);
        double x = mop.getLocation().x;
        double y = mop.getLocation().y;
        double z = mop.getLocation().z;
        CursedSharkProjectile projectile = new CursedSharkProjectile(player.level, player);
        //projectile.setPos(x , y + 1, z);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 0.2f, 1);

        return true;
    }
}
