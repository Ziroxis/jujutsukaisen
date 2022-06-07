package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodShurikenAbility;
import com.example.jujutsukaisen.abilities.cursed_speech.StopAbility;
import com.example.jujutsukaisen.abilities.disaster_flames.DisasterFlamesPassive;
import com.example.jujutsukaisen.abilities.disaster_flames.FlameTouchAbility;
import com.example.jujutsukaisen.abilities.disaster_tides.DisasterTidesPassive;
import com.example.jujutsukaisen.abilities.disaster_tides.WaterFlowAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.DashAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.KihonZukiAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.ManjiKickAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.ShiranuiGataAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameSpeedAbility;
import com.example.jujutsukaisen.abilities.straw_doll.NailShotAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.client.gui.CursedSpiritAcceptanceScreen;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModAttributes;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SOpenCursedSpiritAcceptanceScreenPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateHealthPacket;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class GetStatsFirstTimeEvent {

    public static final AttributeModifier HEAVENLY_STRENGTH = new AttributeModifier(UUID.fromString("9cd5ec6c-dcd7-11ec-9d64-0242ac120002"),
    "Heavenly", 3, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_SPEED = new AttributeModifier(UUID.fromString("b7b47dd2-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 0.1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_JUMP = new AttributeModifier(UUID.fromString("d058ad40-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_HASTE = new AttributeModifier(UUID.fromString("f41bd7ac-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_HEALTH = new AttributeModifier(UUID.fromString("ed591554-dce3-11ec-9d64-0242ac120002"),
            "Heavenly", 10, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier RESTRICTION_CONSTITUTION = new AttributeModifier(UUID.fromString("21624492-dce4-11ec-9d64-0242ac120002"),
            "Constitution", -10, AttributeModifier.Operation.ADDITION);
    @SubscribeEvent
    public static void JoinWorldEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        IEntityStats props = EntityStatsCapability.get(player);
        IAbilityData abilityProps = AbilityDataCapability.get(player);

        if (!props.hasClan())
        {
            props.setCurseGrade(ModValues.locked);
            props.setLevel(0);
            props.setExperience(0);
            props.setMaxExperience(50);
            props.setCursedEnergy(50);
            props.setMaxCursedEnergy(50);

            int rng_clan = Beapi.RNG(6);
            switch (rng_clan)
            {
                case 0:
                    props.setClan(ModValues.Kamo);
                    props.setTechnique(ModValues.BLOOD_MANIPULATION);
                    abilityProps.addUnlockedAbility(BloodShurikenAbility.INSTANCE);
                    props.setRestriction(ModValues.NONE);
                    props.setCurse(ModValues.HUMAN);
                    break;
                case 1:
                    props.setClan(ModValues.Inumaki);
                    props.setTechnique(ModValues.CURSED_SPEECH);
                    abilityProps.addUnlockedAbility(StopAbility.INSTANCE);
                    props.setRestriction(ModValues.NONE);
                    props.setCurse(ModValues.HUMAN);
                    break;
                case 2:
                    props.setClan(ModValues.Zenin);
                    props.setTechnique(ModValues.PROJECTION_SORCERY);
                    abilityProps.addUnlockedAbility(FrameSpeedAbility.INSTANCE);
                    props.setRestriction(ModValues.NONE);
                    props.setCurse(ModValues.HUMAN);
                    break;
                case 3:
                    props.setClan(ModValues.NONE);
                    props.setTechnique(ModValues.STRAW_DOLL);
                    abilityProps.addUnlockedAbility(NailShotAbility.INSTANCE);
                    props.setRestriction(ModValues.NONE);
                    props.setCurse(ModValues.HUMAN);
                    break;
                case 4:
                    PacketHandler.sendTo(new SOpenCursedSpiritAcceptanceScreenPacket(), player);
                    break;
                case 5:
                    int rng_restriction = Beapi.RNG(2);
                    if (rng_restriction == 0)
                        props.setRestriction(ModValues.RESTRICTION_HEAVENLY);
                    else if (rng_restriction == 1)
                        props.setRestriction(ModValues.RESTRICTION_CONSTITUTION);
                    break;
            }

            if (props.getRestriction().equals(ModValues.RESTRICTION_CONSTITUTION))
            {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
                player.setHealth(10);
                int rng_restriction_constitution = Beapi.RNG(4);
                switch (rng_restriction_constitution)
                {
                    case 0:
                        props.setClan(ModValues.Kamo);
                        props.setTechnique(ModValues.BLOOD_MANIPULATION);
                        abilityProps.addUnlockedAbility(BloodShurikenAbility.INSTANCE);
                        props.setCurse(ModValues.HUMAN);
                        break;
                    case 1:
                        props.setClan(ModValues.Inumaki);
                        props.setTechnique(ModValues.CURSED_SPEECH);
                        abilityProps.addUnlockedAbility(StopAbility.INSTANCE);
                        props.setCurse(ModValues.HUMAN);
                        break;
                    case 2:
                        props.setClan(ModValues.Zenin);
                        props.setTechnique(ModValues.PROJECTION_SORCERY);
                        abilityProps.addUnlockedAbility(FrameSpeedAbility.INSTANCE);
                        props.setCurse(ModValues.HUMAN);
                        break;
                    case 3:
                        props.setClan(ModValues.NONE);
                        props.setTechnique(ModValues.STRAW_DOLL);
                        abilityProps.addUnlockedAbility(NailShotAbility.INSTANCE);
                        props.setRestriction(ModValues.NONE);
                        props.setCurse(ModValues.HUMAN);
                        break;
                }
            }
            else if (props.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
            {
                props.setClan(ModValues.Zenin);
                props.setCurse(ModValues.HUMAN);
                props.setTechnique(ModValues.BRUTE_FORCE);
                props.setMaxCursedEnergy(0);
                props.setCursedEnergy(0);
                abilityProps.addUnlockedAbility(ShiranuiGataAbility.INSTANCE);
                abilityProps.addUnlockedAbility(ManjiKickAbility.INSTANCE);
                abilityProps.addUnlockedAbility(KihonZukiAbility.INSTANCE);
                abilityProps.addUnlockedAbility(DashAbility.INSTANCE);

                player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(HEAVENLY_STRENGTH);
                player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(HEAVENLY_SPEED);
                player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(HEAVENLY_JUMP);
                player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(HEAVENLY_HASTE);
                player.getAttribute(ForgeMod.SWIM_SPEED.get()).addTransientModifier(HEAVENLY_SPEED);
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
                player.setHealth(30);
            }
        }
        if (props.getRestriction().equals(ModValues.RESTRICTION_CONSTITUTION))
        {
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
            if (player.getHealth() < player.getMaxHealth())
                player.setHealth(player.getMaxHealth());
        }
        PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), props), player);
        PacketHandler.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityProps), player);
        ((ServerPlayerEntity) event.getPlayer()).connection.send(new SUpdateHealthPacket(event.getPlayer().getHealth(), event.getPlayer().getFoodData().getFoodLevel(), event.getPlayer().getFoodData().getSaturationLevel()));
    }
}
