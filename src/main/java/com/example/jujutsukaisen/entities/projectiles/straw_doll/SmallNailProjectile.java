package com.example.jujutsukaisen.entities.projectiles.straw_doll;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class SmallNailProjectile extends AbilityProjectileEntity {

    public SmallNailProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public SmallNailProjectile(World world, LivingEntity player)
    {
        super(StrawDollProjectiles.SMALL_NAIL.get(), world, player);
        this.setDamage(3);
        this.setMaxLife(96);
        this.setPhysical(false);
        this.onEntityImpactEvent = this::onEntityImpactEvent;
    }

    private void onEntityImpactEvent(LivingEntity entity)
    {
        if (!entity.hasEffect(ModEffects.HAIR_PIN.get()))
            entity.addEffect(new EffectInstance(ModEffects.HAIR_PIN.get(), 10000, 0));
    }

}
