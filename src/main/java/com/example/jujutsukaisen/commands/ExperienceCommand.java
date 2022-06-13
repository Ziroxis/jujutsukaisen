package com.example.jujutsukaisen.commands;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class ExperienceCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("experience").requires((commandSource) -> commandSource.hasPermission(3))
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("add|set", StringArgumentType.string()).suggests(SUGGEST_SET)
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes((command) ->
                                        {

                                            String set = StringArgumentType.getString(command, "add|set");
                                            int amount = IntegerArgumentType.getInteger(command, "amount");


                                            return setLevel(command.getSource(), EntityArgument.getPlayer(command, "target"), amount, set);
                                        })))));
    }

    private static final SuggestionProvider<CommandSource> SUGGEST_SET = (source, builder) -> {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("set");
        suggestions.add("add");
        return ISuggestionProvider.suggest(suggestions.stream(), builder);
    };

    private static int setLevel(CommandSource command, PlayerEntity player, int amount, String set)
    {
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if (set.equalsIgnoreCase("set"))
            statsProps.setExperience(amount);
        else if (set.equalsIgnoreCase("add"))
            statsProps.alterExperience(amount);
        PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), statsProps), player);
        ExperienceUpEvent event = new ExperienceUpEvent(player, amount);
        MinecraftForge.EVENT_BUS.post(event);
        command.sendSuccess(new TranslationTextComponent(player.getDisplayName().getString() + " " + set + " " + amount + " experince"), true);

        return 1;
    }
}
