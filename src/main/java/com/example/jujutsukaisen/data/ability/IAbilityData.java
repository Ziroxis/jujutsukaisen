package com.example.jujutsukaisen.data.ability;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.Api;


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
	<T extends Ability> List<T> getUnlockedAbilities(Api.AbilityCategory category);
	void clearUnlockedAbilities(Api.AbilityCategory category);
	void clearUnlockedAbilities(Predicate<Ability> check);
	void clearUnlockedAbilityFromList(Api.AbilityCategory category, List<Ability> list);
	int countUnlockedAbilities(Api.AbilityCategory category);

	boolean addEquippedAbility(Ability abl);
	boolean setEquippedAbility(int slot, Ability abl);
	boolean removeEquippedAbility(Ability abl);
	boolean hasEquippedAbility(Ability abl);
	int getEquippedAbilitySlot(Ability abl);
	<T extends Ability> T getEquippedAbility(T abl);
	<T extends Ability> T getEquippedAbility(int slot);
	<T extends Ability> T[] getEquippedAbilities();
	<T extends Ability> T[] getEquippedAbilities(Predicate<Ability> check);
	<T extends Ability> T[] getEquippedAbilities(Api.AbilityCategory category);
	void clearEquippedAbilities(Api.AbilityCategory category);
	void clearEquippedAbilityFromList(Api.AbilityCategory category, List<Ability> list);
	int countEquippedAbilities(Api.AbilityCategory category);
	
	<T extends Ability> T getPreviouslyUsedAbility();
	void setPreviouslyUsedAbility(Ability abl);
	
	int getCombatBarSet();
	void nextCombatBarSet();
	void prevCombatBarSet();
	void setCombatBarSet(int set);
}
