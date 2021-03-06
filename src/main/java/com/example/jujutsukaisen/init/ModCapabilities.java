package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.AbilityDataProvider;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsProvider;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.data.quest.QuestDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModCapabilities {

    public static void init()
    {
        EntityStatsCapability.register();
        AbilityDataCapability.register();
        QuestDataCapability.register();
    }

    /*
    public static class Registry
    {
        @SubscribeEvent
        public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
        {
            if (event.getObject() instanceof PlayerEntity)
            {
                event.addCapability(new ResourceLocation(Main.MODID, "ability_data"), new AbilityDataProvider());
                event.addCapability(new ResourceLocation(Main.MODID, "quest_data"), new QuestDataProvider());
            }
        }
    }
     */


    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> e)
    {
        if (e.getObject() == null)
            return;

        if (e.getObject() instanceof LivingEntity)
        {
            e.addCapability(new ResourceLocation(Main.MODID, "entity_stats"), new EntityStatsProvider());
        }
    }
}
