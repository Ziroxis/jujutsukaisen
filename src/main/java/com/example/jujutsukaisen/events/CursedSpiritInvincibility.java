package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.CurseEntity;
import com.example.jujutsukaisen.init.ModEntities;
import com.example.jujutsukaisen.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Main.MODID)
public class CursedSpiritInvincibility {

    @SubscribeEvent
    public static void cursedSpiritInvincibility(LivingAttackEvent event)
    {
        if (event.getEntity().level.isClientSide)
            return;

        DamageSource source = event.getSource();
        if (source.getDirectEntity() instanceof LivingEntity)
        {
            LivingEntity attacker = (LivingEntity) source.getDirectEntity();
            LivingEntity target = event.getEntityLiving();

            if (!(target instanceof CurseEntity))
                return;

            ItemStack item = attacker.getMainHandItem();
            if (!item.getItem().equals(ModItems.CURSED_HAMMER.get())
                    && !item.getItem().equals(ModItems.BLACK_BLADE.get())
                    && !item.getItem().equals(ModItems.CURSED_SPEAR.get())
                    && !item.getItem().equals(ModItems.DEMON_SLAUGHTERER.get())
                    && !item.getItem().equals(ModItems.NANAMI_BLADE.get()))
                event.setCanceled(true);
        }
    }
}
