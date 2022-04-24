package com.example.jujutsukaisen.api.ability.sorts;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public abstract class ContinuousPunchAbility extends ContinuousAbility
{
	// Setting the defaults so that no crash occurs and so they will be null safe.
	protected IOnHitEntity onHitEntityEvent = (player, target) -> { return 0; };
	
	public ContinuousPunchAbility(String name, AbilityCategories.AbilityCategory category)
	{
		super(name, category);
	}
	
	
	/*
	 *  Methods 
	 */
	public float hitEntity(PlayerEntity player, LivingEntity target)
	{
		float result = this.onHitEntityEvent.onHitEntity(player, target);


		//this.stopContinuity(player);
		return result;
	}
	
	/*
	 *	Interfaces
	 */
	public interface IOnHitEntity extends Serializable
	{
		float onHitEntity(PlayerEntity player, LivingEntity target);
	}
}
