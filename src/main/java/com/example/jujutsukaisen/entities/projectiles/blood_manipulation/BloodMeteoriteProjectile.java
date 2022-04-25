package com.example.jujutsukaisen.entities.projectiles.blood_manipulation;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class BloodMeteoriteProjectile extends AbilityProjectileEntity {

    public BloodMeteoriteProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public BloodMeteoriteProjectile(World world, LivingEntity player)
    {
        super(BloodProjectiles.BLOOD_METEORITE.get(), world, player);
        this.setDamage(2);
        this.setMaxLife(32);
        this.setPhysical(false);
    }
}
