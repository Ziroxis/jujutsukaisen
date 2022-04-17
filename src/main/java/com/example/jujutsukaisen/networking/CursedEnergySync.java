package com.example.jujutsukaisen.networking;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CursedEnergySync {

    private float cursedEnergy;

    public CursedEnergySync(float cursedEnergy)
    {
        this.cursedEnergy = cursedEnergy;
    }

    public static void encode(CursedEnergySync msg, PacketBuffer buf)
    {
        buf.writeFloat(msg.cursedEnergy);
    }

    public static CursedEnergySync decode(PacketBuffer buf)
    {
        float data = buf.readFloat();
        return new CursedEnergySync(data);
    }

    public static void handle(CursedEnergySync msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            IEntityStats playercap = mc.player.getCapability(EntityStatsCapability.INSTANCE).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
            playercap.setCursedEnergy((int) msg.cursedEnergy);
        });
        ctx.get().setPacketHandled(true);
    }

}
