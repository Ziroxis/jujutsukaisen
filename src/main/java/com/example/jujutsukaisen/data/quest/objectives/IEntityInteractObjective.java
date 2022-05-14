package com.example.jujutsukaisen.data.quest.objectives;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface IEntityInteractObjective
{
	boolean checkInteraction(PlayerEntity player, Entity entity);
}
