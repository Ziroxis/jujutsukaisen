package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.basic.CursedEnergyPunchAbility;
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
        if (event.getMessage().contains("punches"))
        {
            IAbilityData props = AbilityDataCapability.get(event.getPlayer());
            props.addUnlockedAbility(CursedEnergyPunchAbility.INSTANCE);
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
        if (event.getMessage().contains("quest"))
        {
            PlayerEntity player = event.getPlayer();
            IQuestData questProps = QuestDataCapability.get(player);

            Quest[] quests = questProps.getInProgressQuests();

            for (int i = 0; i < quests.length; i++)
            {
                if (quests[i] != null && quests[i].equals(ModQuests.UNLOCK_01))
                {
                    player.sendMessage(new StringTextComponent("Now go out and do what I asked!"), player.getUUID());
                    if (quests[i].isComplete() && quests[i].triggerCompleteEvent(Minecraft.getInstance().player))
                    {
                        questProps.addFinishedQuest(quests[i]);
                        questProps.removeInProgressQuest(quests[i]);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        player.sendMessage(new StringTextComponent("Good job! Lemme tell you what to do next."), player.getUUID());
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
                    player.sendMessage(new StringTextComponent("Go kill ANYTHING to prove you're worth mastering cursed energy"), player.getUUID());
                    System.out.println(quests[i]);
                    break;
                }


                if (quests[i] != null && quests[i].equals(ModQuests.UNLOCK_02) && questProps.hasFinishedQuest(ModQuests.UNLOCK_01))
                {
                    player.sendMessage(new StringTextComponent("Now go out and do what I asked!"), player.getUUID());
                    if (quests[i].isComplete() && quests[i].triggerCompleteEvent(Minecraft.getInstance().player))
                    {
                        questProps.addFinishedQuest(quests[i]);
                        questProps.removeInProgressQuest(quests[i]);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        player.sendMessage(new StringTextComponent("Good job! I'll teach you the technique..."), player.getUUID());
                        player.sendMessage(new StringTextComponent("You have unlocked Cursed Punch"), player.getUUID());
                    }
                    else if (!quests[i].isComplete() && quests[i].triggerStartEvent(Minecraft.getInstance().player))
                    {
                        System.out.println("Quest is not over yet");
                    }
                    break;
                }
                else if (quests[i] == null && questProps.hasFinishedQuest(ModQuests.UNLOCK_01) && !(questProps.hasFinishedQuest(ModQuests.UNLOCK_02)))
                {
                    questProps.addInProgressQuest(ModQuests.UNLOCK_01.create());
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    player.sendMessage(new StringTextComponent("Go collect me a bone to prove your kill."), player.getUUID());
                    System.out.println(quests[i]);
                    break;
                }
                System.out.println(quests[i]);
            }
        }
    }
}