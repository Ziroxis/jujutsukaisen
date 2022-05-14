package com.example.jujutsukaisen.data.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public interface ICureEffectObjective
{
	boolean checkEffect(PlayerEntity player, EffectInstance effectInstance);
}
