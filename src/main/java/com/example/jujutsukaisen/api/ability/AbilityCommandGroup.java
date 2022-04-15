package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.init.ModAbilities;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum AbilityCommandGroup implements IExtensibleEnum
{
	TECHNIQUE(() -> ModAbilities.TENSHADOW_TECHNIQUE);

	
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
