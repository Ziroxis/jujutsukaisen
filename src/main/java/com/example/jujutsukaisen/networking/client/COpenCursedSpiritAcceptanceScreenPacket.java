package com.example.jujutsukaisen.networking.client;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SOpenCursedSpiritAcceptanceScreenPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class COpenCursedSpiritAcceptanceScreenPacket {

    public COpenCursedSpiritAcceptanceScreenPacket()
    {}

    public void encode(PacketBuffer buffer)
    {

    }

    public static COpenCursedSpiritAcceptanceScreenPacket decode(PacketBuffer buffer)
    {
        COpenCursedSpiritAcceptanceScreenPacket msg = new COpenCursedSpiritAcceptanceScreenPacket();

        return msg;
    }

    public static void handle(COpenCursedSpiritAcceptanceScreenPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
        {
            ctx.get().enqueueWork(() ->
            {
                PlayerEntity player = ctx.get().getSender();
                IEntityStats entityStats = EntityStatsCapability.get(player);

                PacketHandler.sendTo(new SOpenCursedSpiritAcceptanceScreenPacket(), player);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
