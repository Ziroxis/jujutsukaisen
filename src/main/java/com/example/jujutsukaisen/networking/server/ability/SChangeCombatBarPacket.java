package com.example.jujutsukaisen.networking.server.ability;

import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class SChangeCombatBarPacket
{
	private int combatBar;
	
	public SChangeCombatBarPacket() {}
	
	public SChangeCombatBarPacket(int combatBar)
	{
		this.combatBar = combatBar;
	}
	
	public void encode(PacketBuffer buffer)
	{
		buffer.writeInt(this.combatBar);
	}
	
	public static SChangeCombatBarPacket decode(PacketBuffer buffer)
	{
		SChangeCombatBarPacket msg = new SChangeCombatBarPacket();
		msg.combatBar = buffer.readInt();
		return msg;
	}

	public static void handle(SChangeCombatBarPacket message, final Supplier<NetworkEvent.Context> ctx)
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
		public static void handle(SChangeCombatBarPacket message)
		{
			PlayerEntity player = Minecraft.getInstance().player;
			IAbilityData abilityProps = AbilityDataCapability.get(player);

			abilityProps.setCombatBarSet(message.combatBar);
		}
	}
}
