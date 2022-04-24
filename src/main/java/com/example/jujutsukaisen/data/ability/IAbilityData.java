package com.example.jujutsukaisen.data.ability;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;


import java.util.List;
import java.util.function.Predicate;

public interface IAbilityData
{
	boolean addUnlockedAbility(Ability abl);
	boolean setUnlockedAbility(int slot, Ability abl);
	boolean removeUnlockedAbility(Ability abl);
	boolean hasUnlockedAbility(Ability abl);
	<T extends Ability> T getUnlockedAbility(T abl);
	<T extends Ability> T getUnlockedAbility(int slot);
	<T extends Ability> List<T> getUnlockedAbilities(AbilityCategories.AbilityCategory category);
	void clearUnlockedAbilities(AbilityCategories.AbilityCategory category);
	void clearUnlockedAbilities(Predicate<Ability> check);
	void clearUnlockedAbilityFromList(AbilityCategories.AbilityCategory category, List<Ability> list);
	int countUnlockedAbilities(AbilityCategories.AbilityCategory category);

	boolean addEquippedAbility(Ability abl);
	boolean setEquippedAbility(int slot, Ability abl);
	boolean removeEquippedAbility(Ability abl);
	boolean hasEquippedAbility(Ability abl);
	int getEquippedAbilitySlot(Ability abl);
	<T extends Ability> T getEquippedAbility(T abl);
	<T extends Ability> T getEquippedAbility(int slot);
	<T extends Ability> T[] getEquippedAbilities();
	<T extends Ability> T[] getEquippedAbilities(Predicate<Ability> check);
	<T extends Ability> T[] getEquippedAbilities(AbilityCategories.AbilityCategory category);
	void clearEquippedAbilities(AbilityCategories.AbilityCategory category);
	void clearEquippedAbilityFromList(AbilityCategories.AbilityCategory category, List<Ability> list);
	int countEquippedAbilities(AbilityCategories.AbilityCategory category);
	
	<T extends Ability> T getPreviouslyUsedAbility();
	void setPreviouslyUsedAbility(Ability abl);
	
	int getCombatBarSet();
	void nextCombatBarSet();
	void prevCombatBarSet();
	void setCombatBarSet(int set);
}
