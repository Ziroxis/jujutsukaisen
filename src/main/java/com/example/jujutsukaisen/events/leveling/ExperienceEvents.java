package com.example.jujutsukaisen.events.leveling;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ExperienceEvents {

    @SubscribeEvent
    public static void onLevelUp(ExperienceUpEvent e)
    {
        PlayerEntity player = e.getPlayer();
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if (statsProps.getExperience() >= statsProps.getMaxExperience())
        {
            int currentExperience = statsProps.getExperience();
            int currentMaxExperience = statsProps.getMaxExperience();

            statsProps.alterLevel(1);
            LevelUpEvent eventLevelUp = new LevelUpEvent(player, statsProps.getLevel());
            if (MinecraftForge.EVENT_BUS.post(eventLevelUp))
                return;
            statsProps.alterMaxExperience(50);
            MaxExperienceUpEvent eventMaxExperienceUp = new MaxExperienceUpEvent(player, statsProps.getMaxExperience());
            if (MinecraftForge.EVENT_BUS.post(eventMaxExperienceUp))
                return;
            statsProps.setExperience(currentExperience - currentMaxExperience);
            ExperienceUpEvent eventExperienceUp = new ExperienceUpEvent(player, statsProps.getExperience());
            if (MinecraftForge.EVENT_BUS.post(eventExperienceUp))
                return;
            if (statsProps.getCurse().equals(ModValues.HUMAN) && !statsProps.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY) && !statsProps.getRestriction().equals(ModValues.RESTRICTION_CONSTITUTION))
                statsProps.setMaxCursedEnergy(statsProps.getMaxCursedEnergy() + 50);
            else if (!statsProps.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
                statsProps.setMaxCursedEnergy(statsProps.getMaxExperience() + 75);
            PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), statsProps), player);
            //player.sendMessage(new StringTextComponent("You leveled up to level " + statsProps.getLevel() + "!"), player.getUUID());
        }
    }
}
