package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.ICureEffectObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

import java.util.Arrays;

public class CureEffectObjective extends Objective implements ICureEffectObjective
{
	private Effect[] effects;

	public CureEffectObjective(String title, int count, Effect effect)
	{
		this(title, count, new Effect[] { effect });
	}
	
	public CureEffectObjective(String title, int count, Effect[] effects)
	{
		super(title);
		this.setMaxProgress(count);
		this.effects = effects;
	}

	@Override
	public boolean checkEffect(PlayerEntity player, EffectInstance effectInstance)
	{
		return Arrays.stream(this.effects).anyMatch((effect) -> effectInstance.getEffect() == effect);
	}
}
