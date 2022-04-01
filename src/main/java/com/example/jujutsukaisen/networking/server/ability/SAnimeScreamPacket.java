package com.example.jujutsukaisen.networking.server.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SAnimeScreamPacket
{
	private int entityId;
	private String message;
	
	public SAnimeScreamPacket() {}
	
	public SAnimeScreamPacket(int entityId, String message)
	{
		this.entityId = entityId;
		this.message = message;
	}
	
	public void encode(PacketBuffer buffer)
	{
		buffer.writeInt(this.entityId);
		buffer.writeInt(this.message.length());
		buffer.writeUtf(this.message, this.message.length());
	}
	
	public static SAnimeScreamPacket decode(PacketBuffer buffer)
	{
		SAnimeScreamPacket msg = new SAnimeScreamPacket();
		msg.entityId = buffer.readInt();
		int len = buffer.readInt();
		msg.message = buffer.readUtf(len);
		return msg;
	}

	public static void handle(SAnimeScreamPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
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
		public static void handle(SAnimeScreamPacket message)
		{
			Entity target = Minecraft.getInstance().level.getEntity(message.entityId);
			if(target == null || !(target instanceof PlayerEntity))
				return;
			
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("<" + target.getDisplayName().getString() + "> " + message.message.toUpperCase()), Util.NIL_UUID);
		}
	}
}
