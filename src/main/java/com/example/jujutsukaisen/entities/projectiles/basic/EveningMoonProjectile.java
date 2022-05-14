package com.example.jujutsukaisen.entities.projectiles.basic;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import com.example.jujutsukaisen.models.projectiles.basic.BasicProjectiles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EveningMoonProjectile extends AbilityProjectileEntity {

    public EveningMoonProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public EveningMoonProjectile(World world, LivingEntity player)
    {
        super(BasicProjectiles.EVENING_MOON.get(), world, player);
        this.setDamage(10);
        this.setMaxLife(64);
        this.setPhysical(false);
    }
}
