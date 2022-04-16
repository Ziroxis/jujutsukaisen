package com.example.jujutsukaisen.data.quest;


import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestDataBase implements IQuestData
{
	private Quest[] inProgressQuests = new Quest[5];
	private List<Quest> finishedQuests = new ArrayList<Quest>();

	@Override
	public boolean addInProgressQuest(Quest quest)
	{
		for(int i = 0; i < this.inProgressQuests.length; i++)
		{
			Quest ogQuest = this.inProgressQuests[i];
			if(ogQuest == null)
			{
				 this.inProgressQuests[i] = quest;
				 return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean setInProgressQuest(int slot, Quest quest)
	{
		Quest ogQuest = this.getInProgressQuest(quest);
		if (ogQuest == null && slot <= 5)
		{
			this.inProgressQuests[slot] = quest;
			return true;
		}
		return false;
	}

	@Override
	public boolean removeInProgressQuest(Quest quest)
	{
		Quest ogQuest = this.getInProgressQuest(quest);
		if (ogQuest != null)
		{		
			for(int i = 0; i < this.inProgressQuests.length; i++)
			{
				Quest inProgressQuest = this.inProgressQuests[i];
				if(inProgressQuest != null && inProgressQuest.equals(ogQuest))
				{
					 this.inProgressQuests[i] = null;
					 return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasInProgressQuest(Quest quest)
	{
		return Arrays.stream(this.inProgressQuests)
			.parallel()
			.filter(qst -> qst != null)
			.anyMatch(qst -> qst.equals(quest));
	}

	@Override
	public <T extends Quest> T getInProgressQuest(T quest)
	{
		return (T) Arrays.stream(this.inProgressQuests)
			.parallel()
			.filter(qst -> qst != null)
			.filter(qst -> qst.equals(quest))
			.findFirst().orElse(null);
	}

	@Override
	public <T extends Quest> T getInProgressQuest(int slot)
	{
		return (T) this.inProgressQuests[slot];
	}
	
	@Override
	public int getInProgressQuestSlot(Quest quest)
	{
		for(int i = 0; i < this.inProgressQuests.length; i++)
		{
			if(this.inProgressQuests[i].equals(quest))
				return i;
		}
		
		return -1;
	}
	
	@Override
	public List<Objective> getInProgressObjectives()
	{
		List<Objective> objectives = new ArrayList<Objective>();

		for (Quest quest : this.getInProgressQuests())
		{
			if(quest == null)
				continue;
			
			if (!quest.isComplete())
			{
				for (Objective obj : quest.getObjectives())
				{
					if (!obj.isHidden() && !obj.isLocked() && !obj.isComplete())
					{
						objectives.add(obj);
					}
				}
			}
		}

		return objectives;
	}
	
	@Override
	public Quest[] getInProgressQuests()
	{
		return this.inProgressQuests;
	}

	@Override
	public void clearInProgressQuests()
	{
		for(int i = 0; i < this.inProgressQuests.length; i++)
		{
			Quest quest = this.inProgressQuests[i];
			if(quest != null)
			{
				this.inProgressQuests[i] = null;
			}
		}	
	}

	@Override
	public int countInProgressQuests()
	{
		return Arrays.stream(this.inProgressQuests)
			.parallel()
			.filter(quest -> quest != null)
			.collect(Collectors.toList())
			.size();
	}

	@Override
	public boolean addFinishedQuest(Quest quest)
	{
		Quest ogQuest = this.getFinishedQuest(quest);
		if (ogQuest == null)
		{
			this.finishedQuests.add(quest);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeFinishedQuest(Quest quest)
	{
		Quest ogQuest = this.getFinishedQuest(quest);
		if (ogQuest != null)
		{
			this.finishedQuests.remove(ogQuest);
			return true;
		}
		return false;
	}

	@Override
	public boolean hasFinishedQuest(Quest quest)
	{
		this.finishedQuests.removeIf(qst -> qst == null);
		return this.finishedQuests.parallelStream().anyMatch(qst -> qst.equals(quest));
	}

	@Override
	public <T extends Quest> T getFinishedQuest(T quest)
	{
		this.finishedQuests.removeIf(qst -> qst == null);
		return (T) this.finishedQuests.parallelStream().filter(qst -> qst.equals(quest)).findFirst().orElse(null);
	}

	@Override
	public List<Quest> getFinishedQuests()
	{
		this.finishedQuests.removeIf(qst -> qst == null);
		return this.finishedQuests.parallelStream().collect(Collectors.toList());
	}

	@Override
	public void clearFinishedQuests()
	{
		this.finishedQuests.removeIf(qst -> qst == null);		
	}

	@Override
	public int countFinishedQuests()
	{
		this.finishedQuests.removeIf(qst -> qst == null);
		return this.finishedQuests.size();
	}
}
