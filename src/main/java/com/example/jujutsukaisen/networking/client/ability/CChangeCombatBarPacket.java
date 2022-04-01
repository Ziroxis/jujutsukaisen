package com.example.jujutsukaisen.networking.client.ability;

import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.ability.SChangeCombatBarPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class CChangeCombatBarPacket
{
	private int dir = 0;
	
	public CChangeCombatBarPacket() {}
	
	public CChangeCombatBarPacket(int dir)
	{
		this.dir = dir;
	}
	
	public void encode(PacketBuffer buffer)
	{
		buffer.writeInt(this.dir);
	}

	public static CChangeCombatBarPacket decode(PacketBuffer buffer)
	{
		CChangeCombatBarPacket msg = new CChangeCombatBarPacket();
		msg.dir = buffer.readInt();
		return msg;
	}
	
	public static void handle(CChangeCombatBarPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ctx.get().getSender();
				IAbilityData abilityProps = AbilityDataCapability.get(player);
								
				if(message.dir == 0)
				{
					if(((abilityProps.getCombatBarSet() + 1) * 8) < (10))
						abilityProps.nextCombatBarSet();
					else
						abilityProps.setCombatBarSet(0);
				}
				else
				{
					if(abilityProps.getCombatBarSet() > 0)
						abilityProps.prevCombatBarSet();
					else
						abilityProps.setCombatBarSet(8);
				}
				
				PacketHandler.sendTo(new SChangeCombatBarPacket(abilityProps.getCombatBarSet()), player);
			});
		}
		
		ctx.get().setPacketHandled(true);
	}
}
