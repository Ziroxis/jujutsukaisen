package com.example.jujutsukaisen.networking.server.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SRecalculateEyeHeightPacket
{
	private int entityId;
	
	public SRecalculateEyeHeightPacket() {}
	
	public SRecalculateEyeHeightPacket(int entityId)
	{
		this.entityId = entityId;
	}
	
	public void encode(PacketBuffer buffer) 
	{
		buffer.writeInt(this.entityId);
	}

	public static SRecalculateEyeHeightPacket decode(PacketBuffer buffer)
	{
		SRecalculateEyeHeightPacket msg = new SRecalculateEyeHeightPacket();
		msg.entityId = buffer.readInt();
		return msg;
	}
	
	public static void handle(SRecalculateEyeHeightPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
		{
			ctx.get().enqueueWork(() ->
			{
				ClientHandler.handle(message);
			});
		}
		ctx.get().setPacketHandled(true);
	}
	
	public static class ClientHandler
	{
		@OnlyIn(Dist.CLIENT)
		public static void handle(SRecalculateEyeHeightPacket message)
		{		
			Entity target = Minecraft.getInstance().level.getEntity(message.entityId);
			
			if(target == null || !(target instanceof PlayerEntity))
				return;
			
			target.refreshDimensions();
		}
	}
}
