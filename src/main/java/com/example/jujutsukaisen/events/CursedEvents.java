package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.reverse_cursed_energy.RejuvinationAbility;
import com.example.jujutsukaisen.abilities.reverse_cursed_energy.SelfHealingAbility;
import com.example.jujutsukaisen.abilities.reverse_cursed_energy.SelfHealingRegenerationAbility;
import com.example.jujutsukaisen.abilities.reverse_cursed_energy.SelfHealingRegenerationPassive;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CursedEvents {

    @SubscribeEvent
    public static void regenerateCursedEnergy(TickEvent.PlayerTickEvent event)
    {

        PlayerEntity player = event.player;
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        if (propsEntity.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
            return;
        float regeneration = 0;
        if (propsEntity.getCurse().equals(ModValues.HUMAN) && !propsEntity.getRestriction().equals(ModValues.RESTRICTION_CONSTITUTION))
            regeneration = propsEntity.getLevel()/2;
        else
            regeneration = propsEntity.getLevel();


        if (player.tickCount % 20 == 0)
            propsEntity.alterCursedEnergy((int) (1 + regeneration));

    }

    @SubscribeEvent
    public static void unlockingReverseCursedEnergy(LivingHurtEvent event)
    {
        Entity entity = event.getEntity();

        if (!(entity instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) entity;
        if (player.getHealth() > 3)
            return;
        IEntityStats statsProps = EntityStatsCapability.get(player);
        IAbilityData abilityProps = AbilityDataCapability.get(player);
        if (statsProps.getLevel() < 20 || abilityProps.hasUnlockedAbility(SelfHealingAbility.INSTANCE))
            return;

        player.sendMessage(new StringTextComponent("You have just reached a higher understanding of cursed energy as a whole."), player.getUUID());
        abilityProps.addUnlockedAbility(RejuvinationAbility.INSTANCE);
        abilityProps.addUnlockedAbility(SelfHealingAbility.INSTANCE);
        abilityProps.addUnlockedAbility(SelfHealingRegenerationPassive.INSTANCE);
        abilityProps.addUnlockedAbility(SelfHealingRegenerationAbility.INSTANCE);

    }
}
