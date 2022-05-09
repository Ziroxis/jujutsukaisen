package com.example.jujutsukaisen.entities.projectiles.disaster_tides;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class CursedSharkProjectile extends AbilityProjectileEntity {

    public CursedSharkProjectile(EntityType type, World world) {
        super(type, world);
    }

    public CursedSharkProjectile(World world, LivingEntity player)
    {
        super(DisasterTidesProjectiles.CURSED_SHARK_PROJECTILE.get(), world, player);
        this.setDamage(15);
        this.setMaxLife(32);
        this.setPhysical(false);
    }
}
