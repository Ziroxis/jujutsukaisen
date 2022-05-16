package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.IHitEntityObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

public class HitEntityObjective extends Objective implements IHitEntityObjective
{
	protected ICheckHit hitEvent = (player, target, source, amount) -> true;
	
	public HitEntityObjective(String title, int count)
	{
		this(title, count, (ICheckHit)null);
	}
	
	public HitEntityObjective(String title, int count, EntityType entityType)
	{
		this(title, count, (player, target, source, amount) -> target.getType() == entityType);
	}
	
	public HitEntityObjective(String title, int count, @Nullable ICheckHit check)
	{
		super(title);
		this.setMaxProgress(count);
		if(check != null)
			this.hitEvent = check;
	}

	@Override
	public boolean checkHit(PlayerEntity player, LivingEntity target, DamageSource source, float amount)
	{
		return this.hitEvent.test(player, target, source, amount);
	}
	
	@FunctionalInterface
	public interface ICheckHit
	{
		boolean test(PlayerEntity player, LivingEntity target, DamageSource source, float amount);
		
		default ICheckHit and(ICheckHit check)
		{
			return (player, target, source, amount) -> {
				return this.test(player, target, source, amount) && check.test(player, target, source, amount);		
			};
		}
		
		default ICheckHit or(ICheckHit check)
		{
			return (player, target, source, amount) -> {
				return this.test(player, target, source, amount) || check.test(player, target, source, amount);		
			};
		}
	}
}
