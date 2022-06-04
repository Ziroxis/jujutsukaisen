package com.example.jujutsukaisen.entities.projectiles.straw_doll;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class GiantNailProjectile extends AbilityProjectileEntity {

    public GiantNailProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public GiantNailProjectile(World world, LivingEntity player)
    {
        super(StrawDollProjectiles.GIANT_NAIL.get(), world, player);
        this.setDamage(15);
        this.setMaxLife(128);
        this.setPhysical(false);
        this.onEntityImpactEvent = this::onEntityImpactEvent;
    }

    private void onEntityImpactEvent(LivingEntity entity)
    {
        if (!entity.hasEffect(ModEffects.HAIR_PIN.get()))
            entity.addEffect(new EffectInstance(ModEffects.HAIR_PIN.get()));
    }

}
