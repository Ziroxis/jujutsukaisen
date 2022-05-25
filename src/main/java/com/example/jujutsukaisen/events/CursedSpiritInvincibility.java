package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.damagesource.AbilityDamageSource;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.entities.CurseEntity;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.items.CursedWeapon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class CursedSpiritInvincibility {



    @SubscribeEvent
    public static void cursedSpiritInvincibility(LivingHurtEvent event)
    {
        if (event.getEntity().level.isClientSide)
            return;

        DamageSource source = event.getSource();
        if (source.getDirectEntity() instanceof LivingEntity)
        {
            LivingEntity target = event.getEntityLiving();
            LivingEntity attacker = (LivingEntity) source.getDirectEntity();
            if (!(target instanceof CurseEntity))
                return;
            ItemStack item = attacker.getMainHandItem();
            Map<Enchantment, Integer> enchantment = EnchantmentHelper.getEnchantments(item);

            if (!(source instanceof AbilityDamageSource) && !(item.getItem() instanceof CursedWeapon)
                    && !(enchantment.get(Enchantments.BINDING_CURSE) != null)
                    && !(enchantment.get(Enchantments.VANISHING_CURSE) != null))
            {
                System.out.println(source);
                event.setAmount(0);
            }
        }
    }

    /*
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
            IAbilityData props = AbilityDataCapability.get(attacker);


            if (!(target instanceof CurseEntity))
                return;


            //TODO make it work with cursedswordimbuing
            ItemStack item = attacker.getMainHandItem();
            Map<Enchantment, Integer> enchantment = EnchantmentHelper.getEnchantments(item);

            if (!(item.getItem() instanceof CursedWeapon) && !(source instanceof AbilityDamageSource)
                    && !(enchantment.get(Enchantments.BINDING_CURSE) != null)
                    && !(enchantment.get(Enchantments.VANISHING_CURSE) != null))
                event.setCanceled(true);
        }
    }

     */
}
