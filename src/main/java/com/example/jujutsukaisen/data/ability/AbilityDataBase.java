package com.example.jujutsukaisen.data.ability;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import com.example.jujutsukaisen.api.ability.AbilityCategories;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbilityDataBase implements IAbilityData
{
	private List<Ability> unlockedAbilities = new ArrayList<Ability>();
	private Ability[] equippedAbilities = new Ability[128];

	private Ability previouslyUsedAbility;
	private int currentCombatBarSet = 0;
	
	/*
	 * Unlocked Abilities
	 */

	@Override
	public boolean addUnlockedAbility(Ability abl)
	{
		Ability ogAbl = this.getUnlockedAbility(abl);
		if (ogAbl == null)
		{
			this.unlockedAbilities.add(abl);
			return true;
		}
		return false;
	}

	@Override
	public boolean setUnlockedAbility(int slot, Ability abl)
	{
		Ability ogAbl = this.getUnlockedAbility(abl);
		if (ogAbl == null)
		{
			if(this.unlockedAbilities.size() > slot)
				this.unlockedAbilities.set(slot, abl);
			else
				this.unlockedAbilities.add(slot, abl);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeUnlockedAbility(Ability abl)
	{
		Ability ogAbl = this.getUnlockedAbility(abl);
		if (ogAbl != null)
		{
			this.unlockedAbilities.remove(ogAbl);
			return true;
		}
		return false;
	}

	@Override
	public boolean hasUnlockedAbility(Ability abl)
	{
		this.unlockedAbilities.removeIf(ability -> ability == null);
		return this.unlockedAbilities.parallelStream().anyMatch(ability -> ability.equals(abl));
	}

	@Override
	public <T extends Ability> T getUnlockedAbility(T abl)
	{
		this.unlockedAbilities.removeIf(ability -> ability == null);
		return (T) this.unlockedAbilities.parallelStream().filter(ability -> ability.equals(abl)).findFirst().orElse(null);
	}

	@Override
	public <T extends Ability> T getUnlockedAbility(int slot)
	{
		this.unlockedAbilities.removeIf(ability -> ability == null);
		return this.unlockedAbilities.size() > slot ? (T) this.unlockedAbilities.get(slot) : null;
	}

	@Override
	public <T extends Ability> List<T> getUnlockedAbilities(AbilityCategories.AbilityCategory category)
	{
		this.unlockedAbilities.removeIf(ability -> ability == null);
		return (List<T>) this.unlockedAbilities.parallelStream().filter(ability -> ability.getCategory() == category || category == AbilityCategories.AbilityCategory.ALL).collect(Collectors.toList());
	}

	@Override
	public void clearUnlockedAbilities(AbilityCategories.AbilityCategory category)
	{
		this.unlockedAbilities.removeIf(ability -> ability == null);
		this.unlockedAbilities.removeIf(ability -> ability.getCategory() == category || category == AbilityCategories.AbilityCategory.ALL);
	}
	
	@Override
	public void clearUnlockedAbilities(Predicate<Ability> check)
	{
		this.unlockedAbilities.removeIf(ability -> ability == null);
		this.unlockedAbilities.removeIf(check);
	}

	@Override
	public void clearUnlockedAbilityFromList(AbilityCategories.AbilityCategory category, List<Ability> list)
	{
		this.unlockedAbilities.removeIf(ability -> (ability == null || ability.getCategory() == category || category == AbilityCategories.AbilityCategory.ALL) && list.contains(ability));
	}

	@Override
	public int countUnlockedAbilities(AbilityCategories.AbilityCategory category)
	{
		this.unlockedAbilities.removeIf(ability -> ability == null);
		return this.unlockedAbilities
			.parallelStream()
			.filter(ability -> !(ability instanceof PassiveAbility))
			.filter(ability -> ability.getCategory() == category || category == AbilityCategories.AbilityCategory.ALL)
			.collect(Collectors.toList()).size();
	}

	/*
	 * Equipped Abilities
	 */

	@Override
	public boolean addEquippedAbility(Ability abl)
	{
		for(int i = 0; i < this.equippedAbilities.length; i++)
		{
			Ability ability = this.equippedAbilities[i];
			if(ability == null)
			{
				 this.equippedAbilities[i] = abl;
				 return true;
			}
		}
		return false;
	}

	@Override
	public boolean setEquippedAbility(int slot, Ability abl)
	{
		Ability ogAbl = this.getEquippedAbility(abl);
		if (ogAbl == null && slot <= 16)
		{
			this.equippedAbilities[slot] = abl;
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEquippedAbility(Ability abl)
	{
		Ability ogAbl = this.getUnlockedAbility(abl);
		if (ogAbl != null)
		{		
			for(int i = 0; i < this.equippedAbilities.length; i++)
			{
				Ability ability = this.equippedAbilities[i];
				if(ability != null)
				{
					 this.equippedAbilities[i] = null;
					 return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasEquippedAbility(Ability abl)
	{
		return Arrays.stream(this.equippedAbilities)
				.parallel()
				.filter(ability -> ability != null)
				.anyMatch(ability -> ability.equals(abl));
	}
	
	@Override
	public int getEquippedAbilitySlot(Ability abl)
	{
		for(int i = 0; i < this.equippedAbilities.length; i++)
		{
			Ability ability = this.equippedAbilities[i];
			if(ability != null && ability.equals(abl))
				return i;
		}
		
		return -1;
	}

	@Override
	public <T extends Ability> T getEquippedAbility(T abl)
	{
		return (T) Arrays.stream(this.equippedAbilities)
				.parallel()
				.filter(ability -> ability != null)
				.filter(ability -> ability.equals(abl))
				.findFirst().orElse(null);
	}

	@Override
	public <T extends Ability> T getEquippedAbility(int slot)
	{
		return (T) this.equippedAbilities[slot];
	}

	@Override
	public<T extends Ability> T[] getEquippedAbilities()
	{
		return (T[]) this.equippedAbilities;
	}
	
	@Override
	public <T extends Ability> T[] getEquippedAbilities(Predicate<Ability> check)
	{
		Stream<Ability> stream = Arrays.stream(this.equippedAbilities);
		stream = stream.filter((ability) -> ability != null);
		stream = stream.filter(check);
		List<Ability> list1 = stream.collect(Collectors.toList());
		Ability list2[] = new Ability[list1.size()];
		return (T[]) list1.toArray(list2);
	}
	
	@Override
	public <T extends Ability> T[] getEquippedAbilities(AbilityCategories.AbilityCategory category)
	{
		List<Ability> list1 = Arrays.stream(this.equippedAbilities).filter(ability -> (ability != null && ability.getCategory() == category) || category == AbilityCategories.AbilityCategory.ALL).collect(Collectors.toList());
		Ability list2[] = new Ability[list1.size()];
		return (T[]) list1.toArray(list2);
	}

	@Override
	public void clearEquippedAbilities(AbilityCategories.AbilityCategory category)
	{
		for(int i = 0; i < this.equippedAbilities.length; i++)
		{
			Ability ability = this.equippedAbilities[i];
			if((ability != null && ability.getCategory() == category) || category == AbilityCategories.AbilityCategory.ALL)
			{
				this.equippedAbilities[i] = null;
			}
		}
	}

	@Override
	public void clearEquippedAbilityFromList(AbilityCategories.AbilityCategory category, List<Ability> list)
	{
		for(int i = 0; i < this.equippedAbilities.length; i++)
		{
			Ability ability = this.equippedAbilities[i];
			if((ability != null && ability.getCategory() == category && list.contains(ability)) || category != AbilityCategories.AbilityCategory.ALL)
			{
				this.equippedAbilities[i] = null;
			}
		}
	}

	@Override
	public int countEquippedAbilities(AbilityCategories.AbilityCategory category)
	{
		return Arrays.stream(this.equippedAbilities)
				.parallel()
				.filter(ability -> ability != null)
				.filter(ability -> ability.getCategory() == category || category == AbilityCategories.AbilityCategory.ALL)
				.filter(ability -> !(ability instanceof PassiveAbility))
				.collect(Collectors.toList())
				.size();
	}

	/*
	 * Previously Used Ability
	 */

	@Override
	public <T extends Ability> T getPreviouslyUsedAbility()
	{
		return (T) this.previouslyUsedAbility;
	}

	@Override
	public void setPreviouslyUsedAbility(Ability abl)
	{
		this.previouslyUsedAbility = abl;
	}

	
	@Override
	public int getCombatBarSet()
	{
		return this.currentCombatBarSet;
	}

	@Override
	public void nextCombatBarSet()
	{
		this.currentCombatBarSet++;
	}

	@Override
	public void prevCombatBarSet()
	{
		this.currentCombatBarSet--;
	}
	
	@Override
	public void setCombatBarSet(int set)
	{
		this.currentCombatBarSet = set;
	}
}
