package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.IUseItemObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UseItemObjective extends Objective implements IUseItemObjective
{
	protected ICheckItemUse useEvent = (player, itemStack, duration) -> true;
	
	public UseItemObjective(String title, int count)
	{
		this(title, count, (ICheckItemUse)null);
	}
	
	public UseItemObjective(String title, int count, Item item)
	{
		this(title, count, (player, itemStack, duration) -> itemStack.getItem() == item);
	}
	
	public UseItemObjective(String title, int count, ICheckItemUse check)
	{
		super(title);
		this.setMaxProgress(count);
		if(check != null)
			this.useEvent = check;
	}
	
	@Override
	public boolean checkItem(PlayerEntity player, ItemStack itemStack, int duration)
	{
		return this.useEvent.test(player, itemStack, duration);
	}
	
	@FunctionalInterface
	public interface ICheckItemUse
	{
		boolean test(PlayerEntity player, ItemStack itemStack, int duration);
		
		default ICheckItemUse and(ICheckItemUse check)
		{
			return (player, itemStack, duration) -> {
				return this.test(player, itemStack, duration) && check.test(player, itemStack, duration);		
			};
		}
		
		default ICheckItemUse or(ICheckItemUse check)
		{
			return (player, itemStack, duration) -> {
				return this.test(player, itemStack, duration) || check.test(player, itemStack, duration);		
			};
		}
	}
}