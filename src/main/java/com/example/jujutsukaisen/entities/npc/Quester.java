package com.example.jujutsukaisen.entities.npc;

import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncQuestDataPacket;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

//TODO make it so they say something after the quest
public abstract class Quester extends CreatureEntity {
    String before;
    String during;
    String after;

    Quest quest;

    public Quester(EntityType type, World world) {
        super(type, world);
        this.before = before;
        this.during = during;
        this.after = after;
        this.quest = quest;
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
        System.out.println("Check 1");
        if (hand != Hand.MAIN_HAND)
            return ActionResultType.PASS;

        IQuestData questProps = QuestDataCapability.get(player);

        if (!player.level.isClientSide)
        {
            System.out.println("Check 2");
            Quest[] quests = questProps.getInProgressQuests();

            for (int i = 0; i < quests.length; i++) {
                System.out.println("Check 3");
                if (quests[i] == null && !(questProps.hasFinishedQuest(quest)) || !(questProps.hasInProgressQuest(quest)))
                {
                    System.out.println("Check 4");
                    player.sendMessage(new StringTextComponent(before), player.getUUID());
                    questProps.addInProgressQuest(quest);
                    PacketHandler.sendToServer(new CSyncQuestDataPacket(questProps));
                    return ActionResultType.PASS;
                }
                else if (quests[i] != null && quests[i].equals(quest) && !(questProps.hasFinishedQuest(quest)))
                    player.sendMessage(new StringTextComponent(during), player.getUUID());
                if (quests[i] != null && quests[i].isComplete() && quests[i].triggerCompleteEvent(Minecraft.getInstance().player)) {
                    questProps.addFinishedQuest(quests[i]);
                    questProps.removeInProgressQuest(quests[i]);
                    PacketHandler.sendToServer(new CSyncQuestDataPacket(questProps));
                    player.sendMessage(new StringTextComponent(after), player.getUUID());
                }

            }
        }
        return ActionResultType.PASS;
    }
}
