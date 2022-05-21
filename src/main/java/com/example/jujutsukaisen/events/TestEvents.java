package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModQuests;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class TestEvents {

    @SubscribeEvent
    public static void checking(ServerChatEvent event)
    {
        if (event.getMessage().contains("check"))
        {
            PlayerEntity player = event.getPlayer();
            IQuestData questProps = QuestDataCapability.get(player);
            Quest[] quests = questProps.getInProgressQuests();

            for (int i = 0; i < quests.length; i++)
            {
                if (quests[i] != null && quests[i].equals(ModQuests.OBTAIN_SWORD_01) && quests[i].isComplete())
                    System.out.println("Phase 2");
            }
        }
    }
}
