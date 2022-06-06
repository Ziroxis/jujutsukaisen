package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.init.ModItems;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BoodEdgeDropEvent {

    @SubscribeEvent
    public static void onBloodEdgeDrop(ItemTossEvent event)
    {
        if (event.getEntityItem().getItem().getItem().equals(ModItems.BLOOD_EDGE.get()))
            event.getEntityItem().remove();
    }
}
