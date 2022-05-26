package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.init.ModAbilities;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum AbilityCommandGroup implements IExtensibleEnum
{
	HEAVENLY_RESTRICTION(() -> ModAbilities.HEAVENLY_RESTRICTION),
	DISASTER_FLAMES(() -> ModAbilities.DISASTER_FLAMES),
	DISASTER_TIDES(() -> ModAbilities.DISASTER_TIDES),
	CURSED_SPEECH(() -> ModAbilities.CURSED_SPEECH),
	BLOOD_MANIPULATION(() -> ModAbilities.BLOOD_MANIPULATION),
	PROJECTION_SORCERY(() -> ModAbilities.PROJECTION_SORCERY),
	TENSHADOW_TECHNIQUE(() -> ModAbilities.TENSHADOW_TECHNIQUE),
	CURSED_PUNCHES(() -> ModAbilities.CURSED_PUNCHES),
	CURSED_SWORDS(() -> ModAbilities.CURSED_SWORD);


	
	private Supplier<Ability[]> abilities;
	
	private AbilityCommandGroup(Supplier<Ability[]> abilities)
	{
		this.abilities = abilities;
	}
	
	public List<Ability> getAbilities()
	{
		return Arrays.asList(this.abilities.get());
	}
	
	public static AbilityCommandGroup create(String name, Supplier<Ability[]> abilities)
	{
		throw new IllegalStateException("Enum not extended");
	}
}
