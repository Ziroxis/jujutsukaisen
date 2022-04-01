package com.example.jujutsukaisen.networking.client;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SOpenPlayerScreenPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class COpenPlayerScreenPacket
{
	public COpenPlayerScreenPacket() {}

	public void encode(PacketBuffer buffer)
	{
	}

	public static COpenPlayerScreenPacket decode(PacketBuffer buffer)
	{
		COpenPlayerScreenPacket msg = new COpenPlayerScreenPacket();

		return msg;
	}
	
	public static void handle(COpenPlayerScreenPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ctx.get().getSender();
				IEntityStats entityProps = EntityStatsCapability.get(player);

				PacketHandler.sendTo(new SOpenPlayerScreenPacket(), player);
			});
		}
		System.out.println("Check 2");
		ctx.get().setPacketHandled(true);
	}
}
