package com.example.jujutsukaisen.entities.projectiles.blood_manipulation;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class PiercingBloodProjectile extends AbilityProjectileEntity {

    public PiercingBloodProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public PiercingBloodProjectile(World world, LivingEntity player)
    {
        super(BloodProjectiles.PIERCING_BLOOD.get(), world, player);
        this.setDamage(10);
        this.setMaxLife(32);
        this.setPhysical(false);
    }
}
