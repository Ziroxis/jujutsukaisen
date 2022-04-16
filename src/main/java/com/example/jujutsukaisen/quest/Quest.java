package com.example.jujutsukaisen.quest;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.quest.IQuestData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class Quest extends ForgeRegistryEntry<Quest>
{
	private String title;
	private String description;
	
	private List<Objective> objectives = new ArrayList<Objective>();
	private List<Quest> requirements = new ArrayList<Quest>();

	// Setting the defaults so that no crash occurs and so they will be null safe.
	protected IStarting onStartEvent = (player) -> { return true; };
	protected ICompleting onCompleteEvent = (player) -> { return true; };
	protected IShouldRestart shouldRestartEvent = (player) -> { return false; };

	public Quest(String id, String title)
	{
		this.title = title;
	}
	
	/*
	 *  Methods
	 */
	
	@Nullable
	public Quest create()
	{
		try
		{
			return this.getClass().getConstructor().newInstance();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean equals(Object quest)
	{
		if(!(quest instanceof Quest))
			return false;
		
		return this.getId().equalsIgnoreCase(((Quest) quest).getId());
	}

	public boolean removeQuestItem(PlayerEntity player, Item item, int amount)
	{
		int id = Beapi.getIndexOfItemStack(item, player.inventory);
		
		if(id < 0)
		{
			player.sendMessage(new TranslationTextComponent("Missing quest items: %s", new ItemStack(item).getDisplayName().getString()), Util.NIL_UUID);
			return false;
		}
		
		player.inventory.getItem(id).shrink(amount);
		return true;
	}
	
	/*
	 *  Setters and Getters
	 */
	
	public boolean checkRestart(PlayerEntity player)
	{
		return this.shouldRestartEvent.check(player);
	}
	
	public boolean triggerCompleteEvent(PlayerEntity player)
	{
		return this.onCompleteEvent.check(player);
	}
	
	public boolean triggerStartEvent(PlayerEntity player)
	{
		return this.onStartEvent.check(player);
	}
	
	public void addRequirements(Quest... requirements)
	{
		for(Quest req : requirements)
			this.addRequirement(req);
	}
	
	public void addRequirement(Quest req)
	{
		if(!this.requirements.contains(req))
			this.requirements.add(req);
	}
	
	public void addObjectives(Objective... objectives)
	{
		for(Objective obj : objectives)
			this.addObjective(obj);
	}
	
	public void addObjective(Objective objective)
	{
		if(!this.objectives.contains(objective))
			this.objectives.add(objective);
	}
	
	public List<Objective> getObjectives()
	{
		return this.objectives;
	}
	
	public boolean isComplete()
	{
		return this.objectives.stream().allMatch(objective -> !objective.isOptional() && objective.isComplete());
	}
	
	public double getProgress()
	{
		int maxProgress = this.objectives.size();
		int completed = this.objectives.stream().filter(objective -> !objective.isOptional() && objective.isComplete()).collect(Collectors.toList()).size();

		double progress = completed / (double) maxProgress;
		
		return progress;
	}
	
	public void setDescription(String desc)
	{
		this.description = desc;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getId()
	{
		return Beapi.getResourceName(this.title);
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public boolean isLocked(IQuestData props)
	{
		if(this.requirements.size() <= 0)
			return false;
		
		boolean isLocked = false;
		for(Quest quest : this.requirements)
		{
			if(!props.hasFinishedQuest(quest))
			{
				isLocked = true;
				break;
			}
		}

		return isLocked;
	}
	
	
	/*
	 *  Save / Load data
	 */
	
	public CompoundNBT save()
	{
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.putString("id", this.getId());
		ListNBT objectivesData = new ListNBT();
		for(Objective obj : this.getObjectives())
		{
			objectivesData.add(obj.save());
		}
		nbt.put("objectives", objectivesData);
		
		return nbt;
	}
	
	public void load(CompoundNBT nbt)
	{
		ListNBT objectivesData = nbt.getList("objectives", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < objectivesData.size(); i++)
		{
			CompoundNBT questData = objectivesData.getCompound(i);
			this.getObjectives().get(i).load(questData);
		}
	}
	
	
	/*
	 *  Interfaces
	 */
	
	public interface IShouldRestart extends Serializable
	{
		boolean check(PlayerEntity player);
	}
	
	public interface IStarting extends Serializable
	{
		boolean check(PlayerEntity player);
	}

	public interface ICompleting extends Serializable
	{
		boolean check(PlayerEntity player);
	}
}
