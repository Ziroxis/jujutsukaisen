package com.example.jujutsukaisen.events;


import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.curses.RoppongiEntity;
import com.example.jujutsukaisen.init.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Main.MODID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.ROPPONGI.get(), RoppongiEntity.setCustomAttributes().build());
    }

/*
    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event)
    {

    }
    */

}
