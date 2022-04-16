package com.example.jujutsukaisen.networking.server;

import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


public class SSyncQuestDataPacket
{
	private int entityId;
	private INBT data;

	public SSyncQuestDataPacket()
	{
	}

	public SSyncQuestDataPacket(int entityId, IQuestData props)
	{
		this.data = new CompoundNBT();
		this.data = QuestDataCapability.INSTANCE.getStorage().writeNBT(QuestDataCapability.INSTANCE, props, null);
		this.entityId = entityId;
	}

	public void encode(PacketBuffer buffer)
	{
		buffer.writeInt(this.entityId);
		buffer.writeNbt((CompoundNBT) this.data);
	}

	public static SSyncQuestDataPacket decode(PacketBuffer buffer)
	{
		SSyncQuestDataPacket msg = new SSyncQuestDataPacket();
		msg.entityId = buffer.readInt();
		msg.data = buffer.readNbt();
		return msg;
	}

	public static void handle(SSyncQuestDataPacket message, final Supplier<NetworkEvent.Context> ctx)
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
		public static void handle(SSyncQuestDataPacket message)
		{
			Entity target = Minecraft.getInstance().level.getEntity(message.entityId);
			if(target == null || !(target instanceof PlayerEntity))
				return;
			
			IQuestData props = QuestDataCapability.get((PlayerEntity) target);
						
			QuestDataCapability.INSTANCE.getStorage().readNBT(QuestDataCapability.INSTANCE, props, null, message.data);		
		}
	}
}
