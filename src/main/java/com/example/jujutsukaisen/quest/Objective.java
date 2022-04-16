package com.example.jujutsukaisen.quest;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class Objective
{
	private String title;
	private String description;
	private boolean isHidden;
	private boolean isOptional;
	private double maxProgress = 1;
	private double progress;
	private boolean hasEvent;
	private boolean hasStartedEvent;
	
	private List<Objective> requirements = new ArrayList<Objective>();

	// Setting the defaults so that no crash occurs and so they will be null safe.
	public IStartEvent onStartEvent = (player) -> {};
	public IRestartEvent onRestartEvent = (player) -> { return true; };
	
	public Objective(String title)
	{
		this.title = title;
	}
	
	
	/*
	 *	Methods
	 */
	
	public void triggerStartEvent(PlayerEntity player)
	{
		this.hasStartedEvent = true;
		this.onStartEvent.start(player);
	}
	
	public void triggerRestartEvent(PlayerEntity player)
	{
		this.hasStartedEvent = false;
		this.onRestartEvent.restart(player);
	}
	
	
	/*
	 * 	Setters and Getters
	 */
	
	public void setProgress(double progress)
	{
		if(progress <= this.getMaxProgress())
			this.progress = progress;
		else
			this.progress = this.getMaxProgress();
	}
	
	public void alterProgress(double progress)
	{
		if(this.progress + progress <= this.getMaxProgress())
			this.progress += progress;
		else
			this.progress = this.getMaxProgress();
	}
	
	public double getProgress()
	{
		return this.progress;
	}
	
	public void setMaxProgress(double progress)
	{
		this.maxProgress = progress;
	}
	
	public double getMaxProgress()
	{
		return this.maxProgress;
	}
	
	public Objective addRequirements(Objective... objectives)
	{
		for(Objective obj : objectives)
			this.addRequirement(obj);
		
		return this;
	}
	
	public Objective addRequirement(Objective objective)
	{
		if(!this.requirements.contains(objective))
			this.requirements.add(objective);
		
		return this;
	}
	
	public Objective setDescription(String desc)
	{
		this.description = desc;
		return this;
	}

	public Objective setOptional()
	{
		this.isOptional = true;
		return this;
	}
	
	public boolean isOptional()
	{
		return this.isOptional;
	}
	
	public Objective markHidden()
	{
		this.isHidden = true;
		return this;
	}
	
	public String getId()
	{
		return Beapi.getResourceName(this.title);
	}
	
	public boolean isHidden()
	{
		return this.isHidden && this.isLocked();
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public boolean isComplete()
	{
		return this.progress >= this.maxProgress;
	}
	
	public boolean isLocked()
	{
		if(this.requirements.size() <= 0)
			return false;
		
		if(this.requirements.stream().allMatch(objective -> !objective.isOptional() && objective.isComplete()))
			return false;
		
		return true;
	}
	
	public String getLocalizedTitle() 
	{
		String objectiveKey = new TranslationTextComponent(String.format("quest.objective." + Main.MODID + ".%s", this.getId())).getKey();
		return new TranslationTextComponent(objectiveKey, ((int)this.getMaxProgress())).getString();
	}
	
	public void setHasEvent(boolean flag)
	{
		this.hasEvent = flag;
	}
	
	public boolean hasEvent()
	{
		return this.hasEvent;
	}
	
	public boolean hasStartedEvent()
	{
		return this.hasStartedEvent;
	}
	
	
	/*
	 *  Save / Load data
	 */
	
	public CompoundNBT save()
	{
		CompoundNBT nbt = new CompoundNBT();

		nbt.putString("id", this.getId());
		nbt.putBoolean("isHidden", this.isHidden);
		nbt.putDouble("progress", this.progress);
		nbt.putBoolean("hasStartedEvent", this.hasStartedEvent);

		return nbt;
	}
	
	public void load(CompoundNBT nbt)
	{	
		this.isHidden = nbt.getBoolean("isHidden");
		this.progress = nbt.getDouble("progress");
		this.hasStartedEvent = nbt.getBoolean("hasStartedEvent");
	}

	
	
	/*
	 *  Interfaces
	 */
	
	@FunctionalInterface
	public interface IStartEvent
	{
		void start(PlayerEntity player);
	}
	
	@FunctionalInterface
	public interface IRestartEvent
	{
		boolean restart(PlayerEntity player);
	}
}
