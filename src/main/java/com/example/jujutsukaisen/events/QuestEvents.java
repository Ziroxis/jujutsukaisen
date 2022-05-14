package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.objectives.IObtainItemObjective;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.data.quest.objectives.IKillEntityObjective;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class QuestEvents
{
    @SubscribeEvent
    public static void onEntityDies(LivingDeathEvent e)
    {
        if (!(e.getSource().getEntity() instanceof PlayerEntity) || e.getSource().getEntity().level.isClientSide())
            return;

        PlayerEntity player = (PlayerEntity) e.getSource().getEntity();
        LivingEntity target = e.getEntityLiving();
        IQuestData questProps = QuestDataCapability.get(player);

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IKillEntityObjective)
            {
                if (((IKillEntityObjective) obj).checkKill(player, target, e.getSource()))
                {
                    obj.alterProgress(1);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getEntity().getId(), questProps), player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemPickedUp(EntityItemPickupEvent event)
    {
        PlayerEntity player = event.getPlayer();
        IQuestData questProps = QuestDataCapability.get(player);

        if(player.level.isClientSide)
            return;

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IObtainItemObjective)
            {
                if (((IObtainItemObjective) obj).checkItem(event.getItem().getItem()))
                {
                    obj.alterProgress(event.getItem().getItem().getCount());
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                }
            }
        }
    }
}
