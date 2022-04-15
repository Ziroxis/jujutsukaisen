package com.example.jujutsukaisen.api.ability;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbilityGroupArgument<T extends Enum<T>> implements ArgumentType<T>
{
	private final Class<T> enumClass;

	public static AbilityGroupArgument<AbilityCommandGroup> abilityGroup()
	{
		return new AbilityGroupArgument<>(AbilityCommandGroup.class);
	}

	public AbilityGroupArgument(final Class<T> enumClass)
	{
		this.enumClass = enumClass;
	}

	@Override
	public T parse(final StringReader reader) throws CommandSyntaxException
	{
		return Enum.valueOf(enumClass, reader.readUnquotedString());
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder)
	{
		return ISuggestionProvider.suggest(Stream.of(enumClass.getEnumConstants()).map(Object::toString), builder);
	}

	@Override
	public Collection<String> getExamples()
	{
		return Stream.of(enumClass.getEnumConstants()).map(Object::toString).collect(Collectors.toList());
	}
}
