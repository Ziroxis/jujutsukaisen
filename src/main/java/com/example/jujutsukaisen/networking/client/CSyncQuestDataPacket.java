package com.example.jujutsukaisen.networking.client;

import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.data.quest.objectives.IObtainItemObjective;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


public class CSyncQuestDataPacket
{
	private INBT data;

	public CSyncQuestDataPacket() {}

	public CSyncQuestDataPacket(IQuestData props)
	{
		this.data = new CompoundNBT();
		this.data = QuestDataCapability.INSTANCE.getStorage().writeNBT(QuestDataCapability.INSTANCE, props, null);
	}

	public void encode(PacketBuffer buffer)
	{
		buffer.writeNbt((CompoundNBT) this.data);
	}

	public static CSyncQuestDataPacket decode(PacketBuffer buffer)
	{
		CSyncQuestDataPacket msg = new CSyncQuestDataPacket();
		msg.data = buffer.readNbt();
		return msg;
	}

	public static void handle(CSyncQuestDataPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ctx.get().getSender();
				IQuestData props = QuestDataCapability.get(player);

				QuestDataCapability.INSTANCE.getStorage().readNBT(QuestDataCapability.INSTANCE, props, null, message.data);
							
				for(Quest quest : props.getInProgressQuests())
				{
					if(quest != null)
					{
						for(Objective obj : quest.getObjectives())
						{
							if(obj != null && obj instanceof IObtainItemObjective)
							{
								IObtainItemObjective itemQuest = (IObtainItemObjective)obj;
								for(ItemStack stack : player.inventory.items)
								{
									if(itemQuest.checkItem(stack))
									{
										obj.alterProgress(stack.getCount());
									}
								}								
							}
						}
					}
				}
				
				PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), props), player);
			});
		}
		ctx.get().setPacketHandled(true);
	}

}
