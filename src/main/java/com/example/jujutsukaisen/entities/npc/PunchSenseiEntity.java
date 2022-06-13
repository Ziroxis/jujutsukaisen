package com.example.jujutsukaisen.entities.npc;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModQuests;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import com.example.jujutsukaisen.networking.server.SSyncTriggerQuest;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class PunchSenseiEntity extends Quester {
    boolean punchSenseiEntityAcceptance = false;

    public PunchSenseiEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 4));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }



    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }



    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND)
            return ActionResultType.PASS;


        if (!player.level.isClientSide)
        {
            IQuestData questProps = QuestDataCapability.get(player);
            IEntityStats statsProps = EntityStatsCapability.get(player);

            if (statsProps.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
            {
                player.sendMessage(new StringTextComponent("Kid, I don't know what the heck is wrong with you but you got 0, absolutely 0 cursed energy in ya. Can't help ya"), player.getUUID());
                player.sendMessage(new StringTextComponent("Also maybe go to a doctor for that."), player.getUUID());
                return ActionResultType.PASS;
            }
            Quest[] quests = questProps.getInProgressQuests();
            for (int i = 0; i < quests.length; i++)
            {
                //TODO check if you can delete is complete or triggerComplete event
                if (quests[i] != null && quests[i].equals(ModQuests.CURSED_PUNCHES_01) && quests[i].isComplete()
                        || quests[i] != null && quests[i].equals(ModQuests.CURSED_PUNCHES_02) && quests[i].isComplete()
                || quests[i] != null && quests[i].equals(ModQuests.CURSED_PUNCH_01) && quests[i].isComplete()
                || quests[i] != null && quests[i].equals(ModQuests.DIVERGENT_FIST_01) && quests[i].isComplete())
                {
                    PacketHandler.sendTo(new SSyncTriggerQuest(i, player.getId()), player);
                    questProps.addFinishedQuest(quests[i]);
                    questProps.removeInProgressQuest(quests[i]);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    player.sendMessage(new StringTextComponent("Good job kid, gotta give it to ya"), player.getUUID());
                    return ActionResultType.PASS;
                }

            }
            if (questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01) && questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_02) && questProps.hasFinishedQuest(ModQuests.CURSED_PUNCH_01) && questProps.hasFinishedQuest(ModQuests.DIVERGENT_FIST_01))
            {
                player.sendMessage(new StringTextComponent("Got nothing else for ya to do kid!"), player.getUUID());
                return ActionResultType.PASS;
            }

            if (questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_02) && questProps.hasFinishedQuest(ModQuests.CURSED_PUNCH_01) && !questProps.hasFinishedQuest(ModQuests.DIVERGENT_FIST_01) && !questProps.hasInProgressQuest(ModQuests.DIVERGENT_FIST_01))
            {
                player.sendMessage(new StringTextComponent("It's pretty damn hard to fight against these long range curses right? That's why imma teach you something new"), player.getUUID());
                player.sendMessage(new StringTextComponent("Go kill roppongis with a sword and I'll teach you, like 20."), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.DIVERGENT_FIST_01);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        break;
                    }
                }
                return ActionResultType.PASS;

            }
            if (questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_02) && !questProps.hasFinishedQuest(ModQuests.CURSED_PUNCH_01) && !questProps.hasInProgressQuest(ModQuests.CURSED_PUNCH_01))
            {
                player.sendMessage(new StringTextComponent("It's time for you to learn something -_- just slashing gets boring after a while"), player.getUUID());
                player.sendMessage(new StringTextComponent("Go kill zombies with an actual punch now, like 20. That's actual practice"), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.CURSED_PUNCH_01);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        break;
                    }
                }
                return ActionResultType.PASS;
            }

            if (questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_01) || questProps.hasInProgressQuest(ModQuests.CURSED_SWORD_01))
            {
                player.sendMessage(new StringTextComponent("Go beat it brat, you already chose the path of slashing stuff."), player.getUUID());
                return ActionResultType.PASS;
            }
            else if (questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01) && !questProps.hasInProgressQuest(ModQuests.CURSED_PUNCHES_02) && !questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_02))
            {
                player.sendMessage(new StringTextComponent("That's not enough to prove me your kill, go get me a bone."), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.CURSED_PUNCHES_02);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        break;
                    }
                }
                return ActionResultType.PASS;
            }
            else if (questProps.hasInProgressQuest(ModQuests.CURSED_PUNCHES_01) || questProps.hasInProgressQuest(ModQuests.CURSED_PUNCHES_02) || questProps.hasInProgressQuest(ModQuests.CURSED_PUNCH_01) || questProps.hasInProgressQuest(ModQuests.DIVERGENT_FIST_01))
            {
                player.sendMessage(new StringTextComponent("Beat it brat. Go do what I asked."), player.getUUID());
                return ActionResultType.PASS;
            }
            else if (!questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01) && !punchSenseiEntityAcceptance)
            {
                player.sendMessage(new StringTextComponent("Ya wanna punch stuff or not brat?"), player.getUUID());
                punchSenseiEntityAcceptance = true;
                return ActionResultType.PASS;
            }
            else if (!questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01) && punchSenseiEntityAcceptance)
            {
                player.sendMessage(new StringTextComponent("Guess you chose my path huh? Go kill something to prove it."), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.CURSED_PUNCHES_01);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        break;
                    }
                }
                return ActionResultType.PASS;
            }

        }
        return ActionResultType.PASS;
    }
}
