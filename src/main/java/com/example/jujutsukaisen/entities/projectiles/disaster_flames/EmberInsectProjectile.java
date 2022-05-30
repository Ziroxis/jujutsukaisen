package com.example.jujutsukaisen.entities.projectiles.disaster_flames;

import com.example.jujutsukaisen.abilities.disaster_flames.EmberInsectsAbility;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import com.example.jujutsukaisen.api.ability.sorts.ExplosionAbility;
import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.particles.CommonExplosionParticleEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EmberInsectProjectile extends AbilityProjectileEntity {


    public EmberInsectProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public EmberInsectProjectile(World world, LivingEntity player)
    {
        super(DisasterFlamesProjectiles.EMBER_INSECT.get(), world, player);
        this.setDamage(2);
        this.setMaxLife(64);
        this.setPhysical(false);

        this.onBlockImpactEvent = this::onBlockImpactEvent;
        this.onEntityImpactEvent = this::onEntityImpactEvent;
    }

    private void onBlockImpactEvent(BlockPos hit)
    {
        ExplosionAbility explosion = AbilityHelper.newExplosion(this.getThrower(), this.level, hit.getX(), hit.getY(), hit.getZ(), 1);
        explosion.setStaticDamage(5);
        explosion.setExplosionSound(true);
        explosion.setDamageOwner(false);
        explosion.setDestroyBlocks(true);
        explosion.setFireAfterExplosion(true);
        explosion.setDamageSource(ModDamageSource.causeAbilityDamage(this.getThrower(), EmberInsectsAbility.INSTANCE));
        explosion.setSmokeParticles(new CommonExplosionParticleEffect(3));
        explosion.setDamageEntities(true);
        explosion.doExplosion();
    }

    private void onEntityImpactEvent(LivingEntity entity)
    {
        ExplosionAbility explosion = AbilityHelper.newExplosion(this.getThrower(), this.level, entity.getX(), entity.getY(), entity.getZ(), 1);
        explosion.setStaticDamage(5);
        explosion.setExplosionSound(true);
        explosion.setDamageOwner(false);
        explosion.setDestroyBlocks(true);
        explosion.setFireAfterExplosion(true);
        explosion.setDamageSource(ModDamageSource.causeAbilityDamage(this.getThrower(), EmberInsectsAbility.INSTANCE));
        explosion.setSmokeParticles(new CommonExplosionParticleEffect(3));
        explosion.setDamageEntities(true);
        explosion.doExplosion();
    }
}

