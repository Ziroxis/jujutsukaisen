package com.example.jujutsukaisen.entities.npc;

import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModQuests;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncQuestDataPacket;
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

public class PunchSenseiEntity extends Quester {

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

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 30)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.FOLLOW_RANGE, 100)
                .add(Attributes.MOVEMENT_SPEED, 2);
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

        if (!player.level.isClientSide)
        {
            Quest[] quests = questProps.getInProgressQuests();

            for (int i = 0; i < quests.length; i++)
            {
                if (questProps.hasFinishedQuest(ModQuests.CURSED_SWORD_01) || questProps.hasInProgressQuest(ModQuests.CURSED_SWORD_01))
                {
                    player.sendMessage(new StringTextComponent("You already went for the path of slashing curses... I'm sorry but I can't teach you my noble art."), player.getUUID());
                    return ActionResultType.PASS;
                }
                if (quests[i] != null && quests[i].equals(ModQuests.CURSED_PUNCHES_01) && !(questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01)))
                {
                    player.sendMessage(new StringTextComponent("Almost done?"), player.getUUID());
                    if (quests[i].isComplete() && quests[i].triggerCompleteEvent(Minecraft.getInstance().player))
                    {
                        questProps.addFinishedQuest(quests[i]);
                        questProps.removeInProgressQuest(quests[i]);
                        PacketHandler.sendToServer(new CSyncQuestDataPacket(questProps));
                        player.sendMessage(new StringTextComponent("Good job! But that's not enough to prove me your worth..."), player.getUUID());
                    }

                    else if (!quests[i].isComplete() && quests[i].triggerStartEvent(Minecraft.getInstance().player))
                    {
                        System.out.println("Quest is not over yet");
                    }
                    break;
                }
                else if (quests[i] == null && !(questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01)))
                {
                    questProps.addInProgressQuest(ModQuests.CURSED_PUNCHES_01.create());
                    PacketHandler.sendToServer(new CSyncQuestDataPacket(questProps));
                    player.sendMessage(new StringTextComponent("Go kill ANYTHING to prove you're worth mastering the art of punching curses!"), player.getUUID());
                    System.out.println(quests[i]);
                    break;
                }


                if (quests[i] != null && quests[i].equals(ModQuests.CURSED_PUNCHES_02) && !(questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_02)))
                {
                    player.sendMessage(new StringTextComponent("Now go out and do what I asked!"), player.getUUID());
                    if (quests[i].isComplete() && quests[i].triggerCompleteEvent(Minecraft.getInstance().player))
                    {
                        questProps.addFinishedQuest(quests[i]);
                        questProps.removeInProgressQuest(quests[i]);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                        player.sendMessage(new StringTextComponent("Good job! Here ya go, simple cursed punch lemme teach you that real quick"), player.getUUID());
                        player.sendMessage(new StringTextComponent("You have unlocked Cursed Punch"), player.getUUID());
                    }
                    else if (!quests[i].isComplete() && quests[i].triggerStartEvent(Minecraft.getInstance().player))
                    {
                        System.out.println("Quest is not over yet");
                    }
                    break;
                }
                //TODO check if quest 2 is in progress or not
                else if (quests[i] == null && questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_01)
                        && !(questProps.hasFinishedQuest(ModQuests.CURSED_PUNCHES_02)))
                {
                    questProps.addInProgressQuest(ModQuests.CURSED_PUNCHES_02.create());
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    player.sendMessage(new StringTextComponent("Go collect me a bone to prove your kill."), player.getUUID());
                    System.out.println(quests[i]);
                    break;
                }
                System.out.println(quests[i]);
            }
        }
        return ActionResultType.PASS;
    }
}
