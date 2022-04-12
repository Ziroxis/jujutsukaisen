package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.tenshadow_technique.DivineDogsAbility;
import com.example.jujutsukaisen.abilities.tenshadow_technique.ShadowInventoryAbility;
import com.example.jujutsukaisen.client.gui.PlayerStatsScreen;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class TestEvents {

    @SubscribeEvent
    public static void testEvents(ServerChatEvent event) {
        if (event.getMessage().contains("test"))
        {
            IAbilityData props = AbilityDataCapability.get(event.getPlayer());
            props.addUnlockedAbility(DivineDogsAbility.INSTANCE);
            PacketHandler.sendToServer(new CSyncAbilityDataPacket(props));
        }
        if (event.getMessage().contains("another"))
        {
            IAbilityData props = AbilityDataCapability.get(event.getPlayer());
            props.addUnlockedAbility(ShadowInventoryAbility.INSTANCE);
            PacketHandler.sendToServer(new CSyncAbilityDataPacket(props));
        }
        if (event.getMessage().contains("stats"))
        {
            PlayerEntity player = event.getPlayer();
            IEntityStats props = EntityStatsCapability.get(player);

            System.out.println(props.getClan());
            System.out.println(props.getCurseGrade());
        }
        if (event.getMessage().contains("give"))
        {
            PlayerEntity player = event.getPlayer();
            IEntityStats props = EntityStatsCapability.get(player);

            props.setClan(ModValues.Zenin);
            props.setCurseGrade(ModValues.locked);
        }
    }
}