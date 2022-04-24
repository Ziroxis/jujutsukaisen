package com.example.jujutsukaisen.api.ability.interfaces;

import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 *  Poor name choice maybe ?<br>
 *  Used to have a common interface for all abilities that can hurt the same entity multiple times.
 *  Abilities should clear the targets list themselves using the <code>clearTargets</code> method and
 *  check if an entity can be a target using <code>isTarget</code> BEFORE applying any damage or debuffs
 * @author wynd
 */
public interface IMultiTargetAbility
{
	final List<Integer> TARGETS = new ArrayList<>();

	default void clearTargets()
	{
		TARGETS.clear();
	}
	
	default boolean isTarget(LivingEntity target)
	{
		if(!TARGETS.contains(target.getId()))
		{
			TARGETS.add(target.getId());
			return true;
		}
		
		return false;
	}
}
