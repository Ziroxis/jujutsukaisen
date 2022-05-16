package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.ISurviveObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.player.PlayerEntity;

public class TimedSurvivalObjective extends Objective implements ISurviveObjective
{
	private int timeToSurvive;
	private float initialHP;
	
	public TimedSurvivalObjective(String title, int seconds)
	{
		super(title);
		this.timeToSurvive = seconds;
		this.setMaxProgress(this.timeToSurvive);

		this.onStartEvent = this::onStartEvent;
	}
	
	private boolean onStartEvent(PlayerEntity player)
	{
		this.initialHP = player.getHealth();
		return true;
	}

	@Override
	public boolean checkTime(PlayerEntity player)
	{
		if(player.getHealth() > this.initialHP)
			this.initialHP = player.getHealth();
		
		if(player.getHealth() < this.initialHP)
		{
			this.setProgress(0);
			this.initialHP = player.getHealth();
			return false;
		}
		
		return true;
	}
}
