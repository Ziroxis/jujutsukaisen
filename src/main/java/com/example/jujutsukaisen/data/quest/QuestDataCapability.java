package com.example.jujutsukaisen.data.quest;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class QuestDataCapability
{

	@CapabilityInject(IQuestData.class)
	public static final Capability<IQuestData> INSTANCE = null;

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IQuestData.class, new Capability.IStorage<IQuestData>()
		{
			@Override
			public INBT writeNBT(Capability<IQuestData> capability, IQuestData instance, Direction side)
			{
				CompoundNBT props = new CompoundNBT();

				ListNBT questsInTracker = new ListNBT();
				for (int i = 0; i < instance.getInProgressQuests().length; i++)
				{
					Quest quest = instance.getInProgressQuests()[i];
					if(quest != null)
						questsInTracker.add(quest.save());
				}
				props.put("quests_in_tracker", questsInTracker);
				
				ListNBT finishedQuests = new ListNBT();
				for (int i = 0; i < instance.getFinishedQuests().size(); i++)
				{
					Quest quest = instance.getFinishedQuests().get(i);
					CompoundNBT questNbt = new CompoundNBT();
					questNbt.putString("id", quest.getId());
					finishedQuests.add(questNbt);
				}
				props.put("finished_quests", finishedQuests);
				
				return props;
			}
			
			@Override
			public void readNBT(Capability<IQuestData> capability, IQuestData instance, Direction side, INBT nbt)
			{
				CompoundNBT props = (CompoundNBT) nbt;

				instance.clearInProgressQuests();
				instance.clearFinishedQuests();
				
				ListNBT trackerQuests = props.getList("quests_in_tracker", Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < trackerQuests.size(); i++)
				{
					CompoundNBT nbtQuests = trackerQuests.getCompound(i);
					Quest quest = GameRegistry.findRegistry(Quest.class).getValue(new ResourceLocation(Main.MODID, nbtQuests.getString("id")));
					try
					{
						Quest newQuest = quest.create();
						newQuest.load(nbtQuests);
						instance.setInProgressQuest(i, newQuest);
					}
					catch(Exception e)
					{
						System.out.println("Unregistered quest: " + nbtQuests.getString("id"));
						e.printStackTrace();
					}
				}
				
				ListNBT finishedQuests = props.getList("finished_quests", Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < finishedQuests.size(); i++)
				{
					CompoundNBT nbtQuests = finishedQuests.getCompound(i);
					Quest quest = GameRegistry.findRegistry(Quest.class).getValue(new ResourceLocation(Main.MODID, nbtQuests.getString("id")));
					try
					{
						instance.addFinishedQuest(quest.create());
					}
					catch(Exception e)
					{
						System.out.println("Unregistered quest: " + nbtQuests.getString("id"));
						e.printStackTrace();
					}
				}
			}
			
		}, QuestDataBase::new);
	}
	
	public static IQuestData get(final PlayerEntity entity)
	{
		return entity.getCapability(INSTANCE, null).orElse(new QuestDataBase());
	}

	public static LazyOptional<IQuestData> getLazy(final PlayerEntity entity)
	{
		return entity.getCapability(INSTANCE, null);
	}
}
