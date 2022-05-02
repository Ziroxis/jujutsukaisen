package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Main.MODID);

    public static final RegistryObject<Attribute> FALL_RESISTANCE = ATTRIBUTES.register("fall_resistance", () -> new RangedAttribute( Main.MODID + ".fall_resistance", 0D, -256D, 256D));
    public static final RegistryObject<Attribute> JUMP_HEIGHT = ATTRIBUTES.register("jump_height", () -> new RangedAttribute(Main.MODID + ".jump_height", 1D, -256D, 256D).setSyncable(true));
    public static final RegistryObject<Attribute> REGEN_RATE = ATTRIBUTES.register("regen_rate", () -> new RangedAttribute(Main.MODID + ".regen_rate", 1D, 0D, 32D).setSyncable(true));
    public static final RegistryObject<Attribute> STEP_HEIGHT = ATTRIBUTES.register("step_height", () -> new RangedAttribute(Main.MODID + ".step_height", 0.5D, 0D, 20D).setSyncable(true));
    public static final RegistryObject<Attribute> DAMAGE_REDUCTION = ATTRIBUTES.register("damage_reduction", () -> new RangedAttribute(Main.MODID + ".damage_reduction", 0.0D, -2D, 0.999D).setSyncable(true));
    public static final RegistryObject<Attribute> ATTACK_RANGE = ATTRIBUTES.register("attack_range", () -> new RangedAttribute(Main.MODID + ".attack_range", 0.0D, -1024D, 1024D).setSyncable(true));

    public static void register(IEventBus eventBus)
    {

    }

    @Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Setup
    {
        @SubscribeEvent
        public static void onEntityConstruct(EntityAttributeModificationEvent event)
        {
            for(EntityType<? extends LivingEntity> type : event.getTypes())
            {
                event.add(type, FALL_RESISTANCE.get());
                event.add(type, JUMP_HEIGHT.get());
                event.add(type, REGEN_RATE.get());
                event.add(type, STEP_HEIGHT.get());
                event.add(type, DAMAGE_REDUCTION.get());
                event.add(type, ATTACK_RANGE.get());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MODID)
    public static class Defaults
    {
        public static float stepHeight;
        @SubscribeEvent
        public static void onTick(TickEvent.PlayerTickEvent event)
        {
            if (event.phase == TickEvent.Phase.END && event.player.tickCount > 0 && event.player.level.isClientSide)
            {
                event.player.maxUpStep = stepHeight;
                return;
            }

            stepHeight = event.player.maxUpStep;
            ModifiableAttributeInstance attributeInstance = event.player.getAttribute(STEP_HEIGHT.get());
            event.player.maxUpStep = (float) attributeInstance.getValue();
        }

        @SubscribeEvent
        public static void onFall(LivingFallEvent e)
        {
            ModifiableAttributeInstance attributeInstance = e.getEntityLiving().getAttribute(FALL_RESISTANCE.get());
            e.setDistance((float) (e.getDistance() - attributeInstance.getValue()));
        }

        @SubscribeEvent
        public static void onJump(LivingEvent.LivingJumpEvent e)
        {
            double value = e.getEntityLiving().getAttribute(JUMP_HEIGHT.get()).getValue();
            e.getEntityLiving().push(0, 0.1F * (value - 1), 0);
            if (value <= 0)
                e.getEntityLiving().setDeltaMovement(0, e.getEntityLiving().getDeltaMovement().y, 0);
        }

        @SubscribeEvent
        public static void onEntityHurt(LivingHurtEvent e)
        {
            if (e.getEntityLiving().level.isClientSide)
                return;
            double reduction = e.getEntityLiving().getAttribute(DAMAGE_REDUCTION.get()).getValue();

            int absoluteReduction = 50;
            if (reduction > 0 && e.getSource().isBypassArmor())
            {
                for (EffectInstance effectInstance : e.getEntityLiving().getActiveEffects()) {
                }
                reduction = reduction / absoluteReduction;
            }

            e.setAmount((float) (e.getAmount() * (1 - reduction)));
        }

        @SubscribeEvent
        public static void onHeal(LivingHealEvent event)
        {
            float value = (float) event.getEntityLiving().getAttribute(REGEN_RATE.get()).getValue();
            if (value != 1)
                event.setAmount(event.getAmount() * value);
        }
    }
}
