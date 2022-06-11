package com.example.jujutsukaisen.damagesource;

import com.example.jujutsukaisen.api.ability.Ability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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

	@Override
	public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn)
	{
		String s = "death.attack." + this.msgId;
		return new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName(), this.ability.getDisplayName());
	}
}
