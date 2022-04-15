package com.example.jujutsukaisen.damagesource;

import com.example.jujutsukaisen.api.ability.Ability;
import net.minecraft.entity.Entity;

public class AbilityDamageSource extends ModEntityDamageSource
{
	private Ability ability;
	
	public AbilityDamageSource(String damageType, Entity source, Ability ability)
	{
		super(damageType, source);
		this.ability = ability;
	}
	
	public Ability getAbilitySource()
	{
		return this.ability;
	}
}
