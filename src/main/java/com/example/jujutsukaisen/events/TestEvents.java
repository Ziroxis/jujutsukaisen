package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.basic.BattoSwordAbility;
import com.example.jujutsukaisen.abilities.basic.CursedEnergyContinuousPunchAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.*;
import com.example.jujutsukaisen.abilities.tenshadow_technique.DivineDogsAbility;
import com.example.jujutsukaisen.abilities.tenshadow_technique.ShadowInventoryAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModQuests;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class TestEvents {

    @SubscribeEvent
    public static void testEvents(ServerChatEvent event) {
        if (event.getMessage().contains("inventory"))
        {
            IAbilityData props = AbilityDataCapability.get(event.getPlayer());
            props.addUnlockedAbility(ShadowInventoryAbility.INSTANCE);
            PacketHandler.sendToServer(new CSyncAbilityDataPacket(props));
        }
        if (event.getMessage().contains("projection"))
        {
            IAbilityData props = AbilityDataCapability.get(event.getPlayer());
            props.addUnlockedAbility(FrameBreakAbility.INSTANCE);
            props.addUnlockedAbility(FrameCatchAbility.INSTANCE);
            props.addUnlockedAbility(FrameSpeedAbility.INSTANCE);
            props.addUnlockedAbility(FrameTeleportationAbility.INSTANCE);
            props.addUnlockedAbility(FrameMovementPassive.INSTANCE);
            PacketHandler.sendToServer(new CSyncAbilityDataPacket(props));
        }
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