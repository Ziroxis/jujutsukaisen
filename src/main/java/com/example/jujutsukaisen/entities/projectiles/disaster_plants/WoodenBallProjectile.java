package com.example.jujutsukaisen.entities.projectiles.disaster_plants;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class WoodenBallProjectile extends AbilityProjectileEntity {

    public WoodenBallProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public WoodenBallProjectile(World world, LivingEntity player)
    {
        super(DisasterPlantsProjectiles.WOODEN_BALL.get(), world, player);
        IEntityStats propStats = EntityStatsCapability.get(player);
        this.setDamage(1 + propStats.getLevel());
        this.setMaxLife(64);
        this.setPhysical(false);
    }
}
