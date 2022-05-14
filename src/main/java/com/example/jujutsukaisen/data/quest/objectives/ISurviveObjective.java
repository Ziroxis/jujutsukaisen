package com.example.jujutsukaisen.data.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;

public interface ISurviveObjective
{
	boolean checkTime(PlayerEntity player);
}
