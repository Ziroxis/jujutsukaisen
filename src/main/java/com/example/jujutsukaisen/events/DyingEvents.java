package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)

public class DyingEvents {

    @SubscribeEvent
    public static void onClonePlayer(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            IEntityStats oldstatProps = EntityStatsCapability.get(event.getOriginal());
            IAbilityData oldabilityProps = AbilityDataCapability.get(event.getOriginal());
            String clan = oldstatProps.getClan();
            String technique = oldstatProps.getTechnique();
            String oldGrade = oldstatProps.getCurseGrade();
            int oldLevel = oldstatProps.getLevel();
            int level = oldLevel / 3;
            int oldMaxCursedEnergy = oldstatProps.getMaxCursedEnergy();

            IEntityStats newEntityStats = EntityStatsCapability.get(event.getPlayer());
            newEntityStats.setClan(clan);
            newEntityStats.setTechnique(technique);
            newEntityStats.setCurseGrade(ModValues.locked);
            newEntityStats.setLevel(level);
            if (level > 0)
                newEntityStats.setMaxCursedEnergy(level * 50);
            newEntityStats.setExperience(0);
            if (level > 0)
                newEntityStats.setMaxExperience((level * 50) + 50);

        }
    }
}
