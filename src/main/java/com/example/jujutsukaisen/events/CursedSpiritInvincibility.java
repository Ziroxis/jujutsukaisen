package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.damagesource.AbilityDamageSource;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.entities.CurseEntity;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.items.CursedWeapon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.system.CallbackI;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class CursedSpiritInvincibility {



    @SubscribeEvent
    public static void cursedSpiritInvincibility(LivingHurtEvent event)
    {

        if (!event.getEntity().level.isClientSide)
        {
            DamageSource source = event.getSource();
            System.out.println(source.getDirectEntity());
            if (source.getDirectEntity() instanceof PlayerEntity) {
                LivingEntity target = event.getEntityLiving();
                PlayerEntity attacker = (PlayerEntity) source.getDirectEntity();
                IEntityStats statsProps = EntityStatsCapability.get(attacker);
                ItemStack item = attacker.getMainHandItem();
                Map<Enchantment, Integer> enchantment = EnchantmentHelper.getEnchantments(item);

                if (!(target instanceof CurseEntity))
                    return;

                if (item.getItem() instanceof CursedWeapon)
                    return;
                else if (!(source instanceof AbilityDamageSource) || !statsProps.getCurse().equals(ModValues.HUMAN) || !(item.getItem().asItem() instanceof CursedWeapon)
                        || !(enchantment.get(Enchantments.BINDING_CURSE) != null)
                        || !(enchantment.get(Enchantments.VANISHING_CURSE) != null)) {
                    event.setAmount(0);
                }
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
