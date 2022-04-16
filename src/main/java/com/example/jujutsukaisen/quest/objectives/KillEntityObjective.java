package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.IKillEntityObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

public class KillEntityObjective extends Objective implements IKillEntityObjective
{
	protected ICheckKill killEvent = (player, target, source) -> {
		ModifiableAttributeInstance attackAttribute = target.getAttribute(Attributes.ATTACK_DAMAGE);
		boolean isAggressive = attackAttribute != null && attackAttribute.getValue() > 0;
		return isAggressive;
	};
	
	public KillEntityObjective(String title, int count)
	{
		this(title, count, (ICheckKill)null);
	}

	public KillEntityObjective(String title, int count, EntityType entityType)
	{
		this(title, count, (player, target, source) -> target.getType() == entityType);
	}
	
	public KillEntityObjective(String title, int count, ICheckKill check)
	{
		super(title);
		this.setMaxProgress(count);
		if(check != null)
			this.killEvent = check;
	}
	
	@Override
	public boolean checkKill(PlayerEntity player, LivingEntity target, DamageSource source)
	{
		return this.killEvent.test(player, target, source);
	}

	@FunctionalInterface
	public interface ICheckKill
	{
		boolean test(PlayerEntity player, LivingEntity target, DamageSource source);
		
		default ICheckKill and(ICheckKill check)
		{
			return (player, target, source) -> {
				return this.test(player, target, source) && check.test(player, target, source);		
			};
		}
		
		default ICheckKill or(ICheckKill check)
		{
			return (player, target, source) -> {
				return this.test(player, target, source) || check.test(player, target, source);		
			};
		}
	}
}
