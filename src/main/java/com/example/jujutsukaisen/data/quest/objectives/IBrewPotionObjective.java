package com.example.jujutsukaisen.data.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IBrewPotionObjective
{
	boolean checkPotion(PlayerEntity player, ItemStack stack);
}
