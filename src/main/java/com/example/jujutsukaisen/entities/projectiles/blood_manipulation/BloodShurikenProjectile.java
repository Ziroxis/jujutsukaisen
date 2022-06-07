package com.example.jujutsukaisen.entities.projectiles.blood_manipulation;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class BloodShurikenProjectile extends AbilityProjectileEntity {

    public BloodShurikenProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public BloodShurikenProjectile(World world, LivingEntity player)
    {
        super(BloodProjectiles.BLOOD_SHURIKEN.get(), world, player);
        this.setDamage(5);
        this.setMaxLife(32);
        this.setPhysical(false);
    }
}
