package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.IBrewPotionObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.PotionUtils;

public class BrewPotionObjective extends Objective implements IBrewPotionObjective
{
	private Effect[] effects;
	private Item[] types = null;
	
	public BrewPotionObjective(String title, int count)
	{
		this(title, count, new Item[] { Items.POTION, Items.LINGERING_POTION, Items.SPLASH_POTION }, null);
	}
	
	public BrewPotionObjective(String title, int count, Effect[] effects)
	{
		this(title, count, new Item[] { Items.POTION, Items.LINGERING_POTION, Items.SPLASH_POTION }, effects);
	}
	
	public BrewPotionObjective(String title, int count, Item[] types, Effect[] effects)
	{
		super(title);
		this.setMaxProgress(count);
		this.effects = effects;
		this.types = types;
	}

	@Override
	public boolean checkPotion(PlayerEntity player, ItemStack stack)
	{
		if(this.types == null)
			return false;
		
		if(stack.getOrCreateTag().getBoolean("questMark"))
			return false;
		
		boolean isPotion = false;
		boolean isCorrectEffect = true;
		
		for (Item item : this.types)
		{
			if (stack.getItem() == item)
			{
				isPotion = true;
				break;
			}
		}
		
		if(this.effects != null && isPotion)
		{
			for(Effect effect : this.effects)
			{
				isCorrectEffect = PotionUtils.getMobEffects(stack).stream().anyMatch(instance -> instance.getEffect() == effect);
				break;
			}		
		}
		else if(this.effects == null && isPotion)
		{
			isCorrectEffect = PotionUtils.getMobEffects(stack).stream().findAny().isPresent();
		}
		
		if(stack.isEmpty() && isCorrectEffect)
		{
			isPotion = true;
		}

		if(isPotion && isCorrectEffect)
			stack.getOrCreateTag().putBoolean("questMark", true);
		
		return isPotion && isCorrectEffect;
	}
}
