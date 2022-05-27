package com.example.jujutsukaisen.abilities.heavenly_restriction;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class DashAbility extends Ability {

    public static DashAbility INSTANCE = new DashAbility();

    public DashAbility()
    {
        super("Dash", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Spurt towards a place which makes it look like teleportation");
        this.setMaxCooldown(2);
        this.setCursedEnergyCost(0);

        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        double maxTeleportationDistance = 8;

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
