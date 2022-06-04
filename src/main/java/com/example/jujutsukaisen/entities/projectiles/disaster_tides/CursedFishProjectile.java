package com.example.jujutsukaisen.entities.projectiles.disaster_tides;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class CursedFishProjectile extends AbilityProjectileEntity {

    public CursedFishProjectile(EntityType type, World world) {
        super(type, world);
    }

    public CursedFishProjectile(World world, LivingEntity player)
    {
        super(DisasterTidesProjectiles.CURSED_FISH_PROJECTILE.get(), world, player);
        this.setDamage(10);
        this.setMaxLife(64);
        this.setPhysical(false);
    }
}
