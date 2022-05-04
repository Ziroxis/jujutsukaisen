package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodShurikenAbility;
import com.example.jujutsukaisen.abilities.cursed_speech.StopAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameSpeedAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class GetStatsFirstTimeEvent {

    @SubscribeEvent
    public static void JoinWorldEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
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

            int rng = Beapi.RNG(4);
            switch (rng)
            {
                case 0:
                    props.setClan(ModValues.Kamo);
                    props.setTechnique(ModValues.BLOOD_MANIPULATION);
                    abilityProps.addUnlockedAbility(BloodShurikenAbility.INSTANCE);
                    break;
                case 1:
                    props.setClan(ModValues.Gojo);
                    props.setTechnique(ModValues.LIMITLESS);
                    break;
                case 2:
                    props.setClan(ModValues.Inumaki);
                    props.setTechnique(ModValues.CURSED_SPEECH);
                    abilityProps.addUnlockedAbility(StopAbility.INSTANCE);
                    break;
                case 3:
                    props.setClan(ModValues.Zenin);
                    props.setTechnique(ModValues.PROJECTION_SORCERY);
                    abilityProps.addUnlockedAbility(FrameSpeedAbility.INSTANCE);
                    break;

            }
        }
        PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), props), player);
        PacketHandler.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityProps), player);
    }
}
