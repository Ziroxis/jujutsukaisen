package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class TestEvents {

    @SubscribeEvent
    public static void testEvents(ServerChatEvent event) {
        if (event.getMessage().contains("stats"))
        {
            PlayerEntity player = event.getPlayer();
            IEntityStats props = EntityStatsCapability.get(player);

            System.out.println(props.returnCursedEnergy());
            System.out.println(props.getMaxCursedEnergy());
            System.out.println(props.getExperience());
            System.out.println(props.getMaxExperience());
            System.out.println(props.getLevel());
        }
        if (event.getMessage().contains("level"))
        {
            PlayerEntity player = event.getPlayer();
            IEntityStats props = EntityStatsCapability.get(player);

            props.setLevel(50);
            props.setExperience(props.getMaxExperience() - 1);
        }
    }
}