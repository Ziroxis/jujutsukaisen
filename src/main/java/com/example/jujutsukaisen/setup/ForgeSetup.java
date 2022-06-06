package com.example.jujutsukaisen.setup;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.commands.AbilityCommand;
import com.example.jujutsukaisen.commands.LevelCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeSetup {

    @SubscribeEvent
    public static void serverStarting(FMLServerStartingEvent event)
    {
        CommandDispatcher dispatcher = event.getServer().getCommands().getDispatcher();

        AbilityCommand.register(dispatcher);
        LevelCommand.register(dispatcher);
    }
}
