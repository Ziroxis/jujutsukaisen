package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.objectives.IReachLevelObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;


public class ReachLevelObjective extends Objective implements IReachLevelObjective
{
	private int level;
	
	public ReachLevelObjective(String title, int doriki)
	{
		super(title);
		this.setMaxProgress(1);
		this.level = doriki;
	}

	@Override
	public boolean checkLevel(PlayerEntity player)
	{
		IEntityStats props = EntityStatsCapability.get(player);
		return props.getLevel() >= this.level;
	}

	@Override
	public String getLocalizedTitle() 
	{
		String objectiveKey = new TranslationTextComponent(String.format("quest.objective." + Main.MODID + ".%s", this.getId())).getKey();
		return new TranslationTextComponent(objectiveKey, this.level).getString();
	}


}
