package com.example.jujutsukaisen.networking.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CGiveItemStackPacket {


	private ItemStack stack;
	public CGiveItemStackPacket() {}
	
	public CGiveItemStackPacket(ItemStack stack)
	{
		this.stack = stack.copy();
	}

	public void encode(PacketBuffer buffer)
	{
		buffer.writeItemStack(stack, false);
	}
	
	public static CGiveItemStackPacket decode(PacketBuffer buffer)
	{
		CGiveItemStackPacket msg = new CGiveItemStackPacket();
		msg.stack = buffer.readItem();
		return msg;
	}

	public static void handle(CGiveItemStackPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ctx.get().getSender();
				player.inventory.add(message.stack);
			});	
		}
		ctx.get().setPacketHandled(true);
	}

}
