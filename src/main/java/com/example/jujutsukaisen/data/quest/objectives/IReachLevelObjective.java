package com.example.jujutsukaisen.data.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;

public interface IReachLevelObjective
{
	boolean checkLevel(PlayerEntity player);
}
