package com.example.jujutsukaisen.abilities.projection_sorcery;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class FrameTeleportationAbility extends Ability {

    public static FrameTeleportationAbility INSTANCE = new FrameTeleportationAbility();

    public FrameTeleportationAbility()
    {
        super("Frame Teleportation", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user teleports to another frame");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(15);

        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        double maxTeleportationDistance = 36;

        RayTraceResult mop = Beapi.rayTraceBlocksAndEntities(player, maxTeleportationDistance);

        double x = mop.getLocation().x;
        double y = Math.min(mop.getLocation().y, player.level.getMaxBuildHeight() * 1.5) + 0.5D;
        double z = mop.getLocation().z;

        BlockPos blockPos = new BlockPos(x, y, z);

        float teleportDistance = (float) Math.sqrt(player.distanceToSqr(x, y, z));

        player.teleportToWithTicket(x, y, z);

        return true;
    }
}
