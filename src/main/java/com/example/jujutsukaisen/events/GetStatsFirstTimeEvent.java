package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
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

        if (!props.hasClan())
        {
            props.setCurseGrade(ModValues.locked);
            props.setLevel(0);
            props.setExperience(0);
            props.setMaxExperience(50);
            props.setCursedEnergy(50);
            props.setMaxCursedEnergy(50);

            int rng = Beapi.RNG(5);
            switch (rng)
            {
                case 0:
                    props.setClan(ModValues.Kamo);
                    break;
                case 1:
                    props.setClan(ModValues.Gojo);
                    break;
                case 2:
                    props.setClan(ModValues.Inumaki);
                    break;
                case 3:
                    props.setClan(ModValues.Zenin);
                    break;
                case 4:
                    props.setClan(ModValues.Suguru);
                    break;
            }
        }
        PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), props), player);
        System.out.println(props.getClan());
        System.out.println(props.getCurseGrade());
        System.out.println(props.getMaxCursedEnergy());
        System.out.println(props.returnCursedEnergy());
    }
}
