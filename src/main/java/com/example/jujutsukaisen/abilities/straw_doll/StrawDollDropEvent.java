package com.example.jujutsukaisen.abilities.straw_doll;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.init.ModItems;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StrawDollDropEvent {

    @SubscribeEvent
    public static void onStrawDollDrop(ItemTossEvent event)
    {
        if (event.getEntityItem().getItem().getItem().equals(ModItems.STRAW_DOLL.get()))
            event.getEntityItem().remove();
    }
}
