package com.example.jujutsukaisen.effects.sleep;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class SleepHurtEvent {

    @SubscribeEvent
    public static void onHurtSleeping(LivingHurtEvent event)
    {
        Entity livingEntity = event.getEntityLiving();

        if (livingEntity instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) livingEntity;
            if (player.hasEffect(ModEffects.SLEEP.get()))
                player.removeEffect(ModEffects.SLEEP.get());
        }
    }
}
