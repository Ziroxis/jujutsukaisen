package com.example.jujutsukaisen.networking.server;

import com.example.jujutsukaisen.client.gui.PlayerStatsScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class SOpenPlayerScreenPacket
{
	public SOpenPlayerScreenPacket() {}

	public void encode(PacketBuffer buffer)
	{
	}

	public static SOpenPlayerScreenPacket decode(PacketBuffer buffer)
	{
		SOpenPlayerScreenPacket msg = new SOpenPlayerScreenPacket();
		return msg;
	}

	public static void handle(SOpenPlayerScreenPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
			ctx.get().enqueueWork(() -> ClientHandler.handle(message));
		ctx.get().setPacketHandled(true);
	}

	public static class ClientHandler
	{
		@OnlyIn(Dist.CLIENT)
		public static void handle(SOpenPlayerScreenPacket message)
		{
			System.out.println("Check 3");
			PlayerStatsScreen.open();
		}
	}
}
