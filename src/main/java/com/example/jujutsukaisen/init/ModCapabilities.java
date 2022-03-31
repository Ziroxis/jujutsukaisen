package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModCapabilities {

    public static void init()
    {
        EntityStatsCapability.register();
    }

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
