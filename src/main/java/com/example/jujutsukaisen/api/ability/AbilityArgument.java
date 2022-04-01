package com.example.jujutsukaisen.api.ability;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.concurrent.CompletableFuture;

public class AbilityArgument implements ArgumentType<Ability>
{
	@Override
	public Ability parse(StringReader reader) throws CommandSyntaxException
	{
		ResourceLocation resourcelocation = ResourceLocation.read(reader);
		Ability ability = GameRegistry.findRegistry(Ability.class).getValue(resourcelocation);
		return ability;
	}

	public static AbilityArgument ability()
	{
		return new AbilityArgument();
	}
	
	public static <S> Ability getAbility(CommandContext<S> context, String name)
	{
		return context.getArgument(name, Ability.class);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
	{
		StringReader stringreader = new StringReader(builder.getInput());
		stringreader.setCursor(builder.getStart());

		return this.suggestAbility(builder);
	}

	private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder builder)
	{
		return ISuggestionProvider.suggestResource(GameRegistry.findRegistry(Ability.class).getKeys(), builder);
	}
}
