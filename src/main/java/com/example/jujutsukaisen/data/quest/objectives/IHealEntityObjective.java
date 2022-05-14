package com.example.jujutsukaisen.data.quest.objectives;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface IHealEntityObjective
{
	boolean checkHeal(PlayerEntity player, LivingEntity target, float amount);
}
