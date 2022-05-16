package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.api.ItemsHelper;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.damagesource.AbilityDamageSource;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import net.minecraft.item.ItemStack;

public class SharedHitChecks
{
	public static final HitEntityObjective.ICheckHit HAS_SWORD = (player, target, source, amount) ->
	{
		ItemStack heldItem = player.getMainHandItem();
		return ItemsHelper.isSword(heldItem);
	};
	
	public static final HitEntityObjective.ICheckHit HAS_BOW = (player, target, source, amount) ->
	{
		ItemStack heldItem = player.getMainHandItem();
		return ItemsHelper.isBow(heldItem);
	};
	

	
	public static final HitEntityObjective.ICheckHit SWEEP_ATTACK_CHECK = (player, target, source, amount) ->
	{
		return source.getMsgId().equalsIgnoreCase("sweep_damage");
	};
	
	public static final HitEntityObjective.ICheckHit checkAbilitySource(Ability ability)
	{
		return (player, target, source, amount) ->
		{
			return source instanceof AbilityDamageSource && ((AbilityDamageSource)source).getAbilitySource().equals(ability);
		};
	}
}
