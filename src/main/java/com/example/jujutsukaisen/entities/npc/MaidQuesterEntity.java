package com.example.jujutsukaisen.entities.npc;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModQuests;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncQuestDataPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

//TODO make a check if they're a grade 4 sorcerer yet or not
public class MaidQuesterEntity extends Quester {
    public MaidQuesterEntity(EntityType type, World world) {
        super(type, world);
    }
    boolean acceptanceMaidQuester_01 = false;
    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand)
    {
        if (hand != Hand.MAIN_HAND)
            return ActionResultType.PASS;

        IQuestData questProps = QuestDataCapability.get(player);
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if (!player.level.isClientSide)
        {
            Quest[] quests = questProps.getInProgressQuests();
            //Check if they already have or already done the quest
            if (questProps.hasFinishedQuest(ModQuests.OBTAIN_SWORD_01) && !acceptanceMaidQuester_01)
            {
                player.sendMessage(new StringTextComponent("Thank you for the big help! I'll talk to you when I need you again"), player.getUUID());
                return ActionResultType.PASS;
            }
            else if (questProps.hasInProgressQuest(ModQuests.OBTAIN_SWORD_01) && !acceptanceMaidQuester_01)
            {
                player.sendMessage(new StringTextComponent("Come back when you killed them! I'll give you a reward"), player.getUUID());
                for (int i = 0; i < quests.length; i++)
                {
                    if (quests[i] != null && quests[i].equals(ModQuests.OBTAIN_SWORD_01) && quests[i].isComplete() && quests[i].triggerCompleteEvent(player))
                    {
                        questProps.addFinishedQuest(quests[i]);
                        questProps.removeInProgressQuest(quests[i]);
                        statsProps.alterLevel(1);
                        PacketHandler.sendToServer(new SSyncQuestDataPacket(i, questProps));
                        PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), statsProps), player);
                        player.sendMessage(new StringTextComponent("HUGE THANKS!!!! HERE IS A REWARD!"), player.getUUID());
                        player.sendMessage(new StringTextComponent("I'll see you around!!!"), player.getUUID());
                        remove();
                    }
                }
                    return ActionResultType.PASS;
            }
            else if (!acceptanceMaidQuester_01)
            {
                player.sendMessage(new StringTextComponent("Hey, could you help me with something if you can? I'll definitely give you a reward!"), player.getUUID());
                acceptanceMaidQuester_01 = true;
                return ActionResultType.PASS;
            }
            else
            {
                player.sendMessage(new StringTextComponent("Thank you! Could you please get rid of those small annoying spirits?"), player.getUUID());
                for (int i = 0; i < quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.OBTAIN_SWORD_01);
                        PacketHandler.sendToServer(new SSyncQuestDataPacket(i, questProps));
                        break;
                    }
                }
                acceptanceMaidQuester_01 = false;
                return ActionResultType.PASS;
            }
            //Give the quest if they haven't done or are doing it

        }
        return ActionResultType.PASS;
    }

}
