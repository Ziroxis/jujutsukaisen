package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class cursedEnergyEvents {

    @SubscribeEvent
    public static void regenerateCursedEnergy(TickEvent.PlayerTickEvent event)
    {

        PlayerEntity player = event.player;
        if (!player.level.isClientSide)
        {
            IEntityStats propsEntity = EntityStatsCapability.get(player);
            float regeneration = 0;
            if (propsEntity.getCurse().equals(ModValues.HUMAN))
                regeneration = propsEntity.getLevel()/2;
            else
                regeneration = propsEntity.getLevel();


            if (player.tickCount % 20 == 0)
                propsEntity.alterCursedEnergy((int) (1 + regeneration));
        }
    }
}
