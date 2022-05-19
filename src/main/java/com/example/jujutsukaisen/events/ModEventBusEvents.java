package com.example.jujutsukaisen.events;


import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.curses.GrassHopperEntity;
import com.example.jujutsukaisen.entities.curses.LizardEntity;
import com.example.jujutsukaisen.entities.curses.RoppongiEntity;
import com.example.jujutsukaisen.entities.curses.SmallPoxEntity;
import com.example.jujutsukaisen.entities.npc.MaidQuesterEntity;
import com.example.jujutsukaisen.entities.npc.PunchSenseiEntity;
import com.example.jujutsukaisen.entities.npc.SwordSenseiEntity;
import com.example.jujutsukaisen.init.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.ROPPONGI.get(), RoppongiEntity.setCustomAttributes().build());
        event.put(ModEntities.SMALL_POX.get(), SmallPoxEntity.setCustomAttributes().build());
        event.put(ModEntities.LIZARD.get(), LizardEntity.setCustomAttributes().build());
        event.put(ModEntities.PUNCH_SENSEI.get(), PunchSenseiEntity.setCustomAttributes().build());
        event.put(ModEntities.SWORD_SENSEI.get(), SwordSenseiEntity.setCustomAttributes().build());
        event.put(ModEntities.MAID_QUESTER.get(), MaidQuesterEntity.setCustomAttributes().build());
        event.put(ModEntities.GRASS_HOPPER.get(), GrassHopperEntity.setCustomAttributes().build());
        event.put(ModEntities.POSSESED_PUPPET.get(), GrassHopperEntity.setCustomAttributes().build());

    }

/*
    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event)
    {

    }
    */

}
