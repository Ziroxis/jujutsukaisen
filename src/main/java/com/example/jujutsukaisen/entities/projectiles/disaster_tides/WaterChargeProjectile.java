package com.example.jujutsukaisen.entities.projectiles.disaster_tides;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class WaterChargeProjectile extends AbilityProjectileEntity {

    public WaterChargeProjectile(EntityType type, World world) {
        super(type, world);
    }

    public WaterChargeProjectile(World world, LivingEntity player)
    {
        super(DisasterTidesProjectiles.WATER_CHARGE.get(), world, player);
        this.setDamage(15);
        this.setMaxLife(32);
        this.setPhysical(false);
    }
}
