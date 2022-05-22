package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityUseEvent;
import com.example.jujutsukaisen.damagesource.AbilityDamageSource;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.objectives.*;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class QuestEvents
{
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) event.getEntity();
        IQuestData questProps = QuestDataCapability.get(player);

        if (player.level.isClientSide)
            return;

        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), (ServerPlayerEntity) player);
    }

    @SubscribeEvent
    public static void onPotionRemoved(LivingEntityUseItemEvent.Stop event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) event.getEntity();
        IQuestData questProps = QuestDataCapability.get(player);

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IUseItemObjective)
            {
                if (((IUseItemObjective) obj).checkItem(player, event.getItem(), event.getDuration()))
                {
                    obj.alterProgress(1);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerAbilityUse(AbilityUseEvent event)
    {
        if(!event.getPlayer().level.isClientSide)
        {
            PlayerEntity player = event.getPlayer();
            IQuestData questProps = QuestDataCapability.get(player);

            for (Objective obj : questProps.getInProgressObjectives())
            {
                if (obj instanceof IUseAbilityObjective)
                {
                    if (((IUseAbilityObjective) obj).checkAbility(player, event.getAbility()))
                    {
                        obj.alterProgress(1);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    }
                }
            }
        }
    }

    /* INFO(wynd) - If the player shift clicks from the brewing stand UI the ItemStack returned from BrewingStandContainer::transferStackInSlot WILL BE EMPTY (air).
     * This happens because the itemstack of the slot is returned, which is emptied and transfered to the player's inventory before the method is called. Meaning the event receives the itemstack of an empty slot.
     * For now it should be avoided to make an objective based specifically on an item type and focus more on the effects of the potions which are consistently returned.
     */
    @SubscribeEvent
    public static void onPlayerBrews(PlayerBrewedPotionEvent event)
    {
        if(event.getPlayer().level.isClientSide)
            return;

        PlayerEntity player = event.getPlayer();
        IQuestData questProps = QuestDataCapability.get(player);

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IBrewPotionObjective)
            {
                if (((IBrewPotionObjective) obj).checkPotion(player, event.getStack()))
                {
                    obj.alterProgress(1);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase != TickEvent.Phase.END || event.side == LogicalSide.SERVER && event.player.tickCount % 20 == 0)
        {
            PlayerEntity player = event.player;
            IQuestData questProps = QuestDataCapability.get(player);

            for (Objective obj : questProps.getInProgressObjectives())
            {
                if (obj instanceof IEquipItemObjective)
                {
                    if (((IEquipItemObjective) obj).checkEquippedItem(player))
                    {
                        obj.alterProgress(1);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    }
                }

                if (obj instanceof ISurviveObjective)
                {
                    if (((ISurviveObjective) obj).checkTime(player))
                    {
                        obj.alterProgress(1);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDies(LivingDeathEvent event)
    {
        //System.out.println(event.getSource());
        //System.out.println(event.getSource().getEntity());


        if (!(event.getSource().getEntity() instanceof PlayerEntity) || event.getSource().getEntity().level.isClientSide)
            return;

        PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
        LivingEntity target = event.getEntityLiving();
        IQuestData questProps = QuestDataCapability.get(player);

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IKillEntityObjective)
            {
                if (((IKillEntityObjective) obj).checkKill(player, target, event.getSource()))
                {
                    obj.alterProgress(1);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityAttack(LivingHurtEvent event)
    {
        if(event.getSource().getEntity() instanceof PlayerEntity && !event.getSource().getEntity().level.isClientSide && event.getEntityLiving() instanceof LivingEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
            LivingEntity target = event.getEntityLiving();
            IQuestData questProps = QuestDataCapability.get(player);

            for (Objective obj : questProps.getInProgressObjectives())
            {
                if (obj instanceof IHitEntityObjective)
                {
                    if (((IHitEntityObjective) obj).checkHit(player, target, event.getSource(), event.getAmount()))
                    {
                        obj.alterProgress(1);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityHealed(LivingHealByEvent event)
    {
        if(event.getHealer() instanceof PlayerEntity && !event.getHealer().level.isClientSide && event.getEntityLiving() instanceof LivingEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getHealer();
            LivingEntity target = event.getEntityLiving();
            IQuestData questProps = QuestDataCapability.get(player);

            for (Objective obj : questProps.getInProgressObjectives())
            {
                if (obj instanceof IHealEntityObjective)
                {
                    if (((IHealEntityObjective) obj).checkHeal(player, target, event.getAmount()))
                    {
                        obj.alterProgress(1);
                        PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    }
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

    @SubscribeEvent
    public static void onItemTossed(ItemTossEvent event)
    {
        PlayerEntity player = event.getPlayer();
        IQuestData questProps = QuestDataCapability.get(player);

        if(player.level.isClientSide)
            return;

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IObtainItemObjective)
            {
                if (((IObtainItemObjective) obj).checkItem(event.getEntityItem().getItem()) && obj.getProgress() > 0)
                {
                    obj.alterProgress(-event.getEntityItem().getItem().getCount());
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event)
    {
        if(event.getHand() != Hand.MAIN_HAND)
            return;

        PlayerEntity player = event.getPlayer();
        IQuestData questProps = QuestDataCapability.get(player);

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IEntityInteractObjective)
            {
                if (((IEntityInteractObjective) obj).checkInteraction(player, event.getTarget()))
                {
                    obj.alterProgress(1);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDorikiGained(ExperienceUpEvent event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) event.getEntity();
        IQuestData questProps = QuestDataCapability.get(player);

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof IReachLevelObjective)
            {
                if (((IReachLevelObjective) obj).checkLevel(player))
                {
                    obj.alterProgress(1);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemoved(PotionEvent.PotionRemoveEvent event)
    {
        if (!(event.getEntityLiving() instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        IQuestData questProps = QuestDataCapability.get(player);

        for (Objective obj : questProps.getInProgressObjectives())
        {
            if (obj instanceof ICureEffectObjective)
            {
                if (((ICureEffectObjective) obj).checkEffect(player, event.getPotionEffect()))
                {
                    obj.alterProgress(1);
                    PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), questProps), player);
                }
            }
        }
    }

}
