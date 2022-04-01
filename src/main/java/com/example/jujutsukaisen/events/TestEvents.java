package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.client.gui.PlayerStatsScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class TestEvents {

    @SubscribeEvent
    public static void testEvents(ServerChatEvent event)
    {
        if (event.getMessage().contains("gui"))
        {
            Minecraft.getInstance().setScreen(new PlayerStatsScreen());
        }
    }
}
