package com.example.jujutsukaisen.entities.projectiles.disaster_plants;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class CursedBudsProjectile extends AbilityProjectileEntity {

    public CursedBudsProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public CursedBudsProjectile(World world, LivingEntity player)
    {
        super(DisasterPlantsProjectiles.CURSED_BUDS.get(), world, player);
        this.setDamage(3);
        this.setMaxLife(64);
        this.setPhysical(false);
        this.onEntityImpactEvent = this::onEntityImpactEvent;
    }

    private void onEntityImpactEvent(LivingEntity entity)
    {
        if (!entity.hasEffect(ModEffects.CURSED_BUD.get()))
            entity.addEffect(new EffectInstance(ModEffects.CURSED_BUD.get()));
    }
}
