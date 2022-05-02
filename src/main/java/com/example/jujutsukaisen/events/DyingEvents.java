package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.client.gui.CursedEnergyBar;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class DyingEvents {

    //TODO the dying event does not happen
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
            IAbilityData newAbilityData = AbilityDataCapability.get(event.getPlayer());


            for (int i = 0; i <= oldabilityProps.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL).size(); i++)
                newAbilityData.addUnlockedAbility(oldabilityProps.getUnlockedAbility(i));


            newEntityStats.setClan(clan);
            newEntityStats.setTechnique(technique);
            newEntityStats.setCurseGrade(ModValues.locked);
            newEntityStats.setLevel(level);
            newEntityStats.setMaxCursedEnergy((level * 50) + 50);
            newEntityStats.setExperience(0);
            newEntityStats.setMaxExperience((level * 50) + 50);


            PacketHandler.sendTo(new SSyncEntityStatsPacket(event.getPlayer().getId(), newEntityStats), event.getPlayer());
            PacketHandler.sendTo(new SSyncAbilityDataPacket(event.getPlayer().getId(), newAbilityData), event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onRespawnPlayer(PlayerEvent.PlayerRespawnEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new CursedEnergyBar());
    }
}
