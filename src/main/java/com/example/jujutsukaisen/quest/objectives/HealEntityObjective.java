package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.IHealEntityObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class HealEntityObjective extends Objective implements IHealEntityObjective
{
	protected ICheckHeal healEvent = (player, target, amount) -> true;
	
	public HealEntityObjective(String title, int count)
	{
		this(title, count, (ICheckHeal)null);
	}
	
	public HealEntityObjective(String title, int count, EntityType entityType)
	{
		this(title, count, (player, target, amount) -> target.getType() == entityType);
	}
	
	public HealEntityObjective(String title, int count, @Nullable ICheckHeal check)
	{
		super(title);
		this.setMaxProgress(count);
		if(check != null)
			this.healEvent = check;
	}

	@Override
	public boolean checkHeal(PlayerEntity player, LivingEntity target, float amount)
	{
		return this.healEvent.test(player, target, amount);
	}
	
	@FunctionalInterface
	public interface ICheckHeal
	{
		boolean test(PlayerEntity player, LivingEntity target, float amount);
		
		default ICheckHeal and(ICheckHeal check)
		{
			return (player, target, amount) -> {
				return this.test(player, target, amount) && check.test(player, target, amount);		
			};
		}
		
		default ICheckHeal or(ICheckHeal check)
		{
			return (player, target, amount) -> {
				return this.test(player, target, amount) || check.test(player, target, amount);		
			};
		}
	}
}
