package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.Main;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TranslationTextComponent;

public class TimedHitEntityObjective extends HitEntityObjective
{
	private long lastHit = 0;
	private int seconds = 0;

	public TimedHitEntityObjective(String title, int count, int seconds)
	{
		super(title, count, (ICheckHit)null);
		this.seconds = seconds * 20;
	}
	
	public TimedHitEntityObjective(String title, int count, int seconds, ICheckHit check)
	{
		super(title, count, check);
		this.seconds = seconds * 20;
	}

	@Override
	public boolean checkHit(PlayerEntity player, LivingEntity target, DamageSource source, float amount)
	{
		long hitTime = player.level.getGameTime();

		if (this.lastHit == 0)
			this.lastHit = player.level.getGameTime();

		if (hitTime - this.seconds <= this.lastHit)
		{
			this.lastHit = hitTime;
			return super.checkHit(player, target, source, amount);
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
