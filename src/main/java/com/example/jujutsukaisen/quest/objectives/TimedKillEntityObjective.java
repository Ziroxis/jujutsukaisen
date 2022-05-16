package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.Main;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TranslationTextComponent;

public class TimedKillEntityObjective extends KillEntityObjective
{
	private long lastHit = 0;
	private int seconds = 0;
	
	public TimedKillEntityObjective(String title, int count, int seconds)
	{
		super(title, count, (ICheckKill)null);
		this.seconds = seconds * 20;
	}
	
	public TimedKillEntityObjective(String title, int count, int seconds, ICheckKill check)
	{
		super(title, count, check);
		this.seconds = seconds * 20;
	}

	@Override
	public boolean checkKill(PlayerEntity player, LivingEntity target, DamageSource source)
	{
		long hitTime = player.level.getGameTime();

		if (this.lastHit == 0)
			this.lastHit = player.level.getGameTime();
				
		if (hitTime - this.seconds <= this.lastHit)
		{
			this.lastHit = hitTime;
			return super.checkKill(player, target, source);
		}
		else
		{
			this.setProgress(0);
			this.lastHit = 0;
			return false;
		}
	}
	
	@Override
	public String getLocalizedTitle() 
	{
		String objectiveKey = new TranslationTextComponent(String.format("quest.objective." + Main.MODID + ".%s", this.getId())).getKey();
		return new TranslationTextComponent(objectiveKey, ((int)this.getMaxProgress()), this.seconds / 20).getString(); 
	}
}
