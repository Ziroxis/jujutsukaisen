package com.example.jujutsukaisen.entities.projectiles.disaster_tides;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class WaterFlowProjectile extends AbilityProjectileEntity {

    public WaterFlowProjectile(EntityType type, World world) {
        super(type, world);
    }

    public WaterFlowProjectile(World world, LivingEntity player)
    {
        super(DisasterTidesProjectiles.WATER_FLOW.get(), world, player);
        this.setDamage(3);
        this.setMaxLife(32);
        this.setPhysical(false);
    }
}
