package com.example.jujutsukaisen.networking.client;

import com.example.jujutsukaisen.data.world.ExtendedWorldData;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncWorldDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class CRequestSyncWorldDataPacket
{
	public CRequestSyncWorldDataPacket() {}
	
	public void encode(PacketBuffer buffer)
	{
	}
	
	public static CRequestSyncWorldDataPacket decode(PacketBuffer buffer)
	{
		CRequestSyncWorldDataPacket msg = new CRequestSyncWorldDataPacket();
		return msg;
	}
	
	public static void handle(CRequestSyncWorldDataPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ctx.get().getSender();
				ExtendedWorldData worldData = ExtendedWorldData.get(player.level);

				PacketHandler.sendTo(new SSyncWorldDataPacket(worldData), player);
			});			
		}
		ctx.get().setPacketHandled(true);
	}
}
