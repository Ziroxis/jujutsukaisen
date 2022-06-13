package com.example.jujutsukaisen.entities.npc;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModQuests;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncQuestDataPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class SwordSenseiEntity extends Quester {

    boolean swordSenseiAcceptance = false;
    public SwordSenseiEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
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

        IQuestData questProps = QuestDataCapability.get(player);
        IEntityStats statsProps = EntityStatsCapability.get(player);


        if (!player.level.isClientSide)
        {
            if (statsProps.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
            {
                player.sendMessage(new StringTextComponent("Kid, I don't know what the heck is wrong with you but you got 0, absolutely 0 cursed energy in ya. Can't help ya"), player.getUUID());
                player.sendMessage(new StringTextComponent("Also maybe go to a doctor for that."), player.getUUID());
                return ActionResultType.PASS;
            }
            Quest[] quests = questProps.getInProgressQuests();
            for (int i = 0; i < quests.length; i++)
            {
                if (quests[i] != null && quests[i].equals(ModQuests.CURSED_SWORD_01) && quests[i].isComplete()
                        || quests[i] != null && quests[i].equals(ModQuests.CURSED_SWORD_02) && quests[i].isComplete()
                || quests[i] != null && quests[i].equals(ModQuests.BATTO_SWORD_01) && quests[i].isComplete()
                || quests[i] != null && quests[i].equals(ModQuests.EVENING_MOON_01) && quests[i].isComplete())
                {
                    if (quests[i].triggerCompleteEvent(player))
                    {
                        questProps.addFinishedQuest(quests[i]);
                        questProps.removeInProgressQuest(quests[i]);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        player.sendMessage(new StringTextComponent("GOOD JOB!!! So proud of you! *Headpats you*"), player.getUUID());
                        return ActionResultType.PASS;
                    }
                }

            }
            if (questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_01) && questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_02) && questProps.hasFinishedQuest(ModQuests.BATTO_SWORD_01) && questProps.hasFinishedQuest(ModQuests.EVENING_MOON_01))
            {
                player.sendMessage(new StringTextComponent("You're done with everything for now! Come back in a later update! Euh I mean.. Later date.."), player.getUUID());
                player.sendMessage(new StringTextComponent("(I should really stop breaking the fourth wall...)"), player.getUUID());

                return ActionResultType.PASS;

            }
            if (questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_02) && questProps.hasFinishedQuest(ModQuests.BATTO_SWORD_01) && !questProps.hasFinishedQuest(ModQuests.EVENING_MOON_01) && !questProps.hasInProgressQuest(ModQuests.EVENING_MOON_01))
            {
                player.sendMessage(new StringTextComponent("It's pretty damn hard to fight against these long range curses right? That's why imma teach you something new"), player.getUUID());
                player.sendMessage(new StringTextComponent("Go kill roppongis with a sword and I'll teach you, like 20."), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.EVENING_MOON_01);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        break;
                    }
                }
                return ActionResultType.PASS;

            }
            if (questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_02) && !questProps.hasFinishedQuest(ModQuests.BATTO_SWORD_01) && !questProps.hasInProgressQuest(ModQuests.BATTO_SWORD_01))
            {
                player.sendMessage(new StringTextComponent("It's time for you to learn something -_- just slashing gets boring after a while"), player.getUUID());
                player.sendMessage(new StringTextComponent("Go kill zombies with an actual sword now, like 20. That's actual practice"), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.BATTO_SWORD_01);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        break;
                    }
                }
                return ActionResultType.PASS;
            }
            if (questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01) || questProps.hasInProgressQuest(ModQuests.CURSED_PUNCHES_01))
            {
                player.sendMessage(new StringTextComponent("You already chose the path to punch curses, I'm very sorry but I can't teach you my teachings..."), player.getUUID());
                return ActionResultType.PASS;
            }
            else if (questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_01) && !questProps.hasInProgressQuest(ModQuests.CURSED_SWORD_02) && !questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_02))
            {
                player.sendMessage(new StringTextComponent("Hey, sorry to bother you but could you also get me a bone? Just to be sure, sorry...."), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.CURSED_SWORD_02);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        break;
                    }
                }
                return ActionResultType.PASS;
            }
            else if (questProps.hasInProgressQuest(ModQuests.CURSED_SWORD_01) || questProps.hasInProgressQuest(ModQuests.CURSED_SWORD_02) || questProps.hasInProgressQuest(ModQuests.BATTO_SWORD_01) || questProps.hasInProgressQuest(ModQuests.EVENING_MOON_01))
            {
                player.sendMessage(new StringTextComponent("C-Cou-Could you please do what I ask? Pretty please?"), player.getUUID());
                return ActionResultType.PASS;
            }
            else if (!questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_01) && !swordSenseiAcceptance)
            {
                player.sendMessage(new StringTextComponent("H-He-Hey there, are you interested in learning the way of the sword? Just asking... Sorry if I bother you."), player.getUUID());
                swordSenseiAcceptance = true;
                return ActionResultType.PASS;
            }
            else if (!questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_01) && swordSenseiAcceptance)
            {
                player.sendMessage(new StringTextComponent("I'm so happy you said yes ^_^, could you just kill something for me? Just to be sure you really want it. It's okay if you don't though TT."), player.getUUID());
                for (int i = 0; i<quests.length; i++)
                {
                    if (quests[i] == null)
                    {
                        questProps.addInProgressQuest(ModQuests.CURSED_SWORD_01);
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

