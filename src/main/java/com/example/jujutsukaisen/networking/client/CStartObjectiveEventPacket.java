package com.example.jujutsukaisen.networking.client;

import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CStartObjectiveEventPacket
{
	private int questId;
	private int objId;

	public CStartObjectiveEventPacket() {}

	public CStartObjectiveEventPacket(int questId, int objId)
	{
		this.questId = questId;
		this.objId = objId;
	}

	public void encode(PacketBuffer buffer)
	{
		buffer.writeInt(this.questId);
		buffer.writeInt(this.objId);
	}

	public static CStartObjectiveEventPacket decode(PacketBuffer buffer)
	{
		CStartObjectiveEventPacket msg = new CStartObjectiveEventPacket();
		msg.questId = buffer.readInt();
		msg.objId = buffer.readInt();
		return msg;
	}

	public static void handle(CStartObjectiveEventPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				ServerPlayerEntity player = ctx.get().getSender();
				IQuestData props = QuestDataCapability.get(player);
				
				Quest current = props.getInProgressQuest(message.questId);
				if (current != null)
				{
					Objective obj = current.getObjectives().get(message.objId);
					if(obj != null)
					{
						if (obj.hasStartedEvent())
							obj.triggerRestartEvent(player);
						else
							obj.triggerStartEvent(player);
						PacketHandler.sendTo(new SSyncQuestDataPacket(player.getId(), props), player);
					}
				}
			});
		}
		ctx.get().setPacketHandled(true);
	}
}
