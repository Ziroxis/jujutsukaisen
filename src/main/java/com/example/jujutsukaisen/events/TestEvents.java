package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.tenshadow_technique.DivineDogsAbility;
import com.example.jujutsukaisen.abilities.tenshadow_technique.ShadowInventoryAbility;
import com.example.jujutsukaisen.client.gui.PlayerStatsScreen;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModQuests;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import com.example.jujutsukaisen.quest.Quest;
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

            System.out.println(props.returnCursedEnergy());
            System.out.println(props.getMaxCursedEnergy());
        }
        if (event.getMessage().contains("quest"))
        {
            PlayerEntity player = event.getPlayer();
            IQuestData questProps = QuestDataCapability.get(player);

            Quest[] quests = questProps.getInProgressQuests();

            for (int i = 0; i < quests.length; i++)
            {
                if (quests[i] != null && quests[i].equals(ModQuests.UNLOCK_01))
                {
                    System.out.println("you already have the quest");
                    if (quests[i].isComplete() && quests[i].triggerCompleteEvent(Minecraft.getInstance().player))
                    {
                        questProps.addFinishedQuest(quests[i]);
                        questProps.removeInProgressQuest(quests[i]);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        System.out.println("quest completed");
                    }
                    else if (!quests[i].isComplete() && quests[i].triggerStartEvent(Minecraft.getInstance().player))
                    {
                        System.out.println("Quest is not over yet");
                    }
                    break;
                }
                else if (quests[i] == null)
                {
                    questProps.addInProgressQuest(ModQuests.UNLOCK_01.create());
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    System.out.println("given the quest");
                    System.out.println(quests[i]);
                    break;
                }
                System.out.println(quests[i]);
            }
        }
    }
}