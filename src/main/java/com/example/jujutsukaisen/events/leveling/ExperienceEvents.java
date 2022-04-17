package com.example.jujutsukaisen.events.leveling;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ExperienceEvents {

    //TODO test this code
    //TODO make it so you get experience by killing cursed spirits
    @SubscribeEvent
    public static void onLevelUp(ExperienceUpEvent e)
    {
        PlayerEntity p = e.getPlayer();
        IEntityStats statsProps = EntityStatsCapability.get(p);

        if (statsProps.getExperience() > statsProps.getMaxExperience())
        {
            statsProps.alterLevel(1);
            LevelUpEvent eventLevelUp = new LevelUpEvent(p, statsProps.getLevel());
            if (MinecraftForge.EVENT_BUS.post(eventLevelUp))
                return;
            statsProps.alterMaxExperience(50);
            MaxExperienceUpEvent eventMaxExperienceUp = new MaxExperienceUpEvent(p, statsProps.getMaxExperience());
            if (MinecraftForge.EVENT_BUS.post(eventMaxExperienceUp))
                return;
            statsProps.setExperience(0);
            ExperienceUpEvent eventExperienceUp = new ExperienceUpEvent(p, statsProps.getExperience());
            if (MinecraftForge.EVENT_BUS.post(eventExperienceUp))
                return;
            PacketHandler.sendTo(new SSyncEntityStatsPacket(p.getId(), statsProps), p);
            p.sendMessage(new StringTextComponent("You leveled up to level " + statsProps.getLevel() + "!"), p.getUUID());
        }
    }
}
