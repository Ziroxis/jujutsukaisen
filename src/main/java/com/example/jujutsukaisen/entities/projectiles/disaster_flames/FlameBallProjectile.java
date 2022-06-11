package com.example.jujutsukaisen.entities.projectiles.disaster_flames;

import com.example.jujutsukaisen.abilities.disaster_flames.FlameBallAbility;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import com.example.jujutsukaisen.api.ability.sorts.ExplosionAbility;
import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.particles.CommonExplosionParticleEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlameBallProjectile extends AbilityProjectileEntity {

    public FlameBallProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public FlameBallProjectile(World world, LivingEntity player)
    {
        super(DisasterFlamesProjectiles.FLAME_BALL.get(), world, player);
        this.setDamage(5);
        this.setMaxLife(32);
        this.setPhysical(false);

        this.onBlockImpactEvent = this::onBlockImpactEvent;

    }

    private void onBlockImpactEvent(BlockPos hit)
    {
        ExplosionAbility explosion = AbilityHelper.newExplosion(this.getThrower(), this.level, hit.getX(), hit.getY(), hit.getZ(), 3);
        explosion.setStaticDamage(15);
        explosion.setExplosionSound(true);
        explosion.setDamageOwner(false);
        explosion.setDestroyBlocks(true);
        explosion.setFireAfterExplosion(false);
        explosion.setDamageSource(ModDamageSource.causeAbilityDamage(this.getThrower(), FlameBallAbility.INSTANCE, "player"));
        explosion.setSmokeParticles(new CommonExplosionParticleEffect(3));
        explosion.setDamageEntities(true);
        explosion.doExplosion();
    }
}
