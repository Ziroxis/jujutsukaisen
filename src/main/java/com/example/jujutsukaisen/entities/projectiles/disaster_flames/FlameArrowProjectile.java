package com.example.jujutsukaisen.entities.projectiles.disaster_flames;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class FlameArrowProjectile extends AbilityProjectileEntity {


    public FlameArrowProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public FlameArrowProjectile(World world, LivingEntity player)
    {
        super(DisasterFlamesProjectiles.FLAME_ARROW.get(), world, player);
        this.setDamage(20);
        this.setMaxLife(32);
        this.setPhysical(false);
        this.onEntityImpactEvent = this::onEntityImpactEvent;
    }

    private void onEntityImpactEvent(LivingEntity entity)
    {
        entity.setSecondsOnFire(12);
    }
}
