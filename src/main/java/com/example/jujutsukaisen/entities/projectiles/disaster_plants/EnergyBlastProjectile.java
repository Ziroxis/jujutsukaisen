package com.example.jujutsukaisen.entities.projectiles.disaster_plants;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyBlastProjectile extends AbilityProjectileEntity {


    public EnergyBlastProjectile(EntityType type, World world)
    {
        super(type, world);
    }

    public EnergyBlastProjectile(World world, LivingEntity player)
    {
        super(DisasterPlantsProjectiles.ENERGY_BLAST.get(), world, player);
        this.setDamage(20);
        this.setMaxLife(128);
        this.setPhysical(false);
        this.onBlockImpactEvent = this::onBlockImpactEvent;
    }
    private void onBlockImpactEvent(BlockPos hit)
    {
        this.level.setBlock(hit, Blocks.AIR.defaultBlockState(), 2);
    }
}
