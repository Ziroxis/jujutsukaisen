package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ProjectileHitEvent extends Event
{
	private AbilityProjectileEntity projectile;
	private RayTraceResult hit;

	public ProjectileHitEvent(AbilityProjectileEntity abilityProjectileEntity, RayTraceResult hit)
	{
		this.projectile = abilityProjectileEntity;
		this.hit = hit;
	}

	public AbilityProjectileEntity getProjectile()
	{
		return this.projectile;
	}

	public RayTraceResult getHit()
	{
		return this.hit;
	}
}
