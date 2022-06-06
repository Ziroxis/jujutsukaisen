package com.example.jujutsukaisen.events.projectile;

import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ProjectileBlockEvent extends Event
{
	private Entity projectile;
	private boolean canBlock;
	
	public ProjectileBlockEvent(Entity entity)
	{
		this.projectile = entity;
	}

	public Entity getProjectile()
	{
		return this.projectile;
	}
	
	public void setCanBlock(boolean flag)
	{
		this.canBlock = flag;
	}
	
	public boolean canBlock()
	{
		return this.canBlock;
	}
}
