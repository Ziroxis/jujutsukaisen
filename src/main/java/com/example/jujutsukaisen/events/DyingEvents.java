package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import com.example.jujutsukaisen.client.gui.overlays.CursedEnergyBarGuiOverlay;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModAttributes;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.play.server.SUpdateHealthPacket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class DyingEvents {

    public static final AttributeModifier HEAVENLY_STRENGTH = new AttributeModifier(UUID.fromString("9cd5ec6c-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 3, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_SPEED = new AttributeModifier(UUID.fromString("b7b47dd2-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 0.5, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_JUMP = new AttributeModifier(UUID.fromString("d058ad40-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_HASTE = new AttributeModifier(UUID.fromString("f41bd7ac-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_HEALTH = new AttributeModifier(UUID.fromString("ed591554-dce3-11ec-9d64-0242ac120002"),
            "Heavenly", 10, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier RESTRICTION_CONSTITUTION = new AttributeModifier(UUID.fromString("21624492-dce4-11ec-9d64-0242ac120002"),
            "Constitution", -10, AttributeModifier.Operation.ADDITION);


    @SubscribeEvent
    public static void onClonePlayer(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            IAbilityData newAbilityData = AbilityDataCapability.get(event.getPlayer());
            INBT nbt = new CompoundNBT();
            DyingEvents.restoreFullData(event.getOriginal(), event.getPlayer());
            DyingEvents.restorePermaData(event.getOriginal(), event.getPlayer());
            PacketHandler.sendTo(new SSyncAbilityDataPacket(event.getPlayer().getId(), newAbilityData), event.getPlayer());
        }
        else
            DyingEvents.restoreFullData(event.getOriginal(), event.getPlayer());
    }

    private static void restoreFullData(PlayerEntity original, PlayerEntity player)
    {
        IAbilityData newAbilityData = AbilityDataCapability.get(player);
        INBT nbt = new CompoundNBT();

        // Keep the entity stats
        IEntityStats oldEntityStats = EntityStatsCapability.get(original);
        nbt = EntityStatsCapability.INSTANCE.writeNBT(oldEntityStats, null);
        IEntityStats newEntityStats = EntityStatsCapability.get(player);
        EntityStatsCapability.INSTANCE.readNBT(newEntityStats, null, nbt);

        if (oldEntityStats.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
        {
            player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(HEAVENLY_STRENGTH);
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(HEAVENLY_SPEED);
            player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(HEAVENLY_JUMP);
            player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(HEAVENLY_HASTE);
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
            player.setHealth(30);
        }
        if (oldEntityStats.getRestriction().equals(ModValues.RESTRICTION_CONSTITUTION))
        {
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
            player.setHealth(10);
        }
        // Keep the ability stats
        IAbilityData oldAbilityData = AbilityDataCapability.get(original);
        nbt = AbilityDataCapability.INSTANCE.writeNBT(oldAbilityData, null);
        AbilityDataCapability.INSTANCE.readNBT(newAbilityData, null, nbt);

        DyingEvents.restorePermaData(original, player);

    }
    private static void restorePermaData(PlayerEntity original, PlayerEntity player)
    {
        INBT nbt = new CompoundNBT();
        // Quest data is persisted no matter the config option.
        // Keep the quests data
        IQuestData oldQuestData = QuestDataCapability.get(original);
        nbt = QuestDataCapability.INSTANCE.writeNBT(oldQuestData, null);
        IQuestData newQuestData = QuestDataCapability.get(player);
        QuestDataCapability.INSTANCE.readNBT(newQuestData, null, nbt);

        // Keep the abilities
        IAbilityData oldAbilityData = AbilityDataCapability.get(original);
        oldAbilityData.clearEquippedAbilities(AbilityCategories.AbilityCategory.ALL);

        nbt = AbilityDataCapability.INSTANCE.writeNBT(oldAbilityData, null);
        IAbilityData newAbilityData = AbilityDataCapability.get(player);
        AbilityDataCapability.INSTANCE.readNBT(newAbilityData, null, nbt);


    }

    /*
    @SubscribeEvent
    public static void onRespawnPlayer(PlayerEvent.PlayerRespawnEvent event)
    {
        PlayerEntity player = event.getPlayer();
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if (statsProps.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
        {
            statsProps.setMaxCursedEnergy(0);
            statsProps.setCursedEnergy(0);
            player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(HEAVENLY_STRENGTH);
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(HEAVENLY_SPEED);
            player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(HEAVENLY_JUMP);
            player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(HEAVENLY_HASTE);
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
            player.setHealth(30);
            ((ServerPlayerEntity) event.getPlayer()).connection.send(new SUpdateHealthPacket(event.getPlayer().getHealth(), event.getPlayer().getFoodData().getFoodLevel(), event.getPlayer().getFoodData().getSaturationLevel()));
        }
        else if (statsProps.getRestriction().equals(ModValues.RESTRICTION_CONSTITUTION))
        {
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
            player.setHealth(10);
            ((ServerPlayerEntity) event.getPlayer()).connection.send(new SUpdateHealthPacket(event.getPlayer().getHealth(), event.getPlayer().getFoodData().getFoodLevel(), event.getPlayer().getFoodData().getSaturationLevel()));
        }

        PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), statsProps), player);
        MinecraftForge.EVENT_BUS.register(new CursedEnergyBarGuiOverlay());
    }

     */
}
