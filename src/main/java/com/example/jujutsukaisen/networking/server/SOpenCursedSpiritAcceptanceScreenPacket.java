package com.example.jujutsukaisen.networking.server;

import com.example.jujutsukaisen.client.gui.CursedSpiritAcceptanceScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SOpenCursedSpiritAcceptanceScreenPacket {

    public SOpenCursedSpiritAcceptanceScreenPacket()
    {}

    public void encode(PacketBuffer buffer)
    {

    }

    public static SOpenCursedSpiritAcceptanceScreenPacket decode(PacketBuffer buffer)
    {
        SOpenCursedSpiritAcceptanceScreenPacket msg = new SOpenCursedSpiritAcceptanceScreenPacket();
        return msg;
    }

    public static void handle(SOpenCursedSpiritAcceptanceScreenPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
            ctx.get().enqueueWork(() -> ClientHandler.handle(message));
        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler
    {
        @OnlyIn(Dist.CLIENT)
        public static void handle(SOpenCursedSpiritAcceptanceScreenPacket message)
        {
            CursedSpiritAcceptanceScreen.open();
        }
    }
}
