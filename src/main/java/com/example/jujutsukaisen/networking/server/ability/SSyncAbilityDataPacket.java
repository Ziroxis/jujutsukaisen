package com.example.jujutsukaisen.networking.server.ability;

import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class SSyncAbilityDataPacket
{
	private int entityId;
	private INBT data;

	public SSyncAbilityDataPacket()
	{
	}

	public SSyncAbilityDataPacket(int entityId, IAbilityData abiltiyDataProps)
	{
		this.data = new CompoundNBT();
		this.data = AbilityDataCapability.INSTANCE.getStorage().writeNBT(AbilityDataCapability.INSTANCE, abiltiyDataProps, null);
		this.entityId = entityId;
	}

	public void encode(PacketBuffer buffer)
	{
		buffer.writeInt(this.entityId);
		buffer.writeNbt((CompoundNBT) this.data);
	}

	public static SSyncAbilityDataPacket decode(PacketBuffer buffer)
	{
		SSyncAbilityDataPacket msg = new SSyncAbilityDataPacket();
		msg.entityId = buffer.readInt();
		msg.data = buffer.readNbt();
		return msg;
	}

	public static void handle(SSyncAbilityDataPacket message, final Supplier<NetworkEvent.Context> ctx)
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
		public static void handle(SSyncAbilityDataPacket message)
		{
			Entity target = Minecraft.getInstance().level.getEntity(message.entityId);			
			if(target == null || !(target instanceof LivingEntity))
				return;
			
			IAbilityData props = AbilityDataCapability.get((LivingEntity) target);		
			AbilityDataCapability.INSTANCE.getStorage().readNBT(AbilityDataCapability.INSTANCE, props, null, message.data);	
		}
	}
}
