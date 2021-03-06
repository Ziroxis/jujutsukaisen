package com.example.jujutsukaisen.api.ability.sorts;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.interfaces.IParallelContinuousAbility;
import com.example.jujutsukaisen.init.ModAbilities;
import com.example.jujutsukaisen.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ItemAbility extends ContinuousAbility implements IParallelContinuousAbility
{	
	public ItemAbility(String name, AbilityCategories.AbilityCategory category)
	{
		super(name, category);
		
		this.onStartContinuityEvent = this::onStartContinuityEvent;
		this.duringContinuityEvent = this::duringContinuityEvent;
		this.onEndContinuityEvent = this::onEndContinuityEvent;
	}

	/*
	 *  Event Consumers
	 */
	private boolean onStartContinuityEvent(PlayerEntity player)
	{
		if (player.getItemInHand(player.getUsedItemHand()).isEmpty() && !this.getItemStack(player).isEmpty())
		{
			player.inventory.setItem(player.inventory.selected, this.getItemStack(player));
			return true;
		}
		else
		{
			if(this.getItemStack(player).isEmpty())
				player.sendMessage(new TranslationTextComponent("Cannot equip because it's an empty stack!"), Util.NIL_UUID);
			else
				player.sendMessage(new TranslationTextComponent("Cannot equip while holding another item in hand!"), Util.NIL_UUID);
			return false;
		}
	}

	private void duringContinuityEvent(PlayerEntity player, int timer)
	{

	}

	private boolean onEndContinuityEvent(PlayerEntity player)
	{
		for (int i = 0; i < 35; i++)
		{
			if (player.inventory.getItem(i).getItem().equals(ModItems.BLOOD_EDGE.get()))
				player.inventory.getItem(i).shrink(1);
		}

		return true;
	}
	/*
	 * 	Methods
	 */
	public abstract ItemStack getItemStack(PlayerEntity player);
	public abstract boolean canBeActive(PlayerEntity player);
}
