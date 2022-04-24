package com.example.jujutsukaisen.networking.client.ability;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.sorts.ChargeableAbility;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.ability.SUpdateEquippedAbilityPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class CRemoveAbilityPacket
{
	private int slot;
	
	public CRemoveAbilityPacket() {}
	
	public CRemoveAbilityPacket(int id)
	{
		this.slot = id;
	}
	
	public void encode(PacketBuffer buffer)
	{
		buffer.writeInt(this.slot);
	}
	
	public static CRemoveAbilityPacket decode(PacketBuffer buffer)
	{
		CRemoveAbilityPacket msg = new CRemoveAbilityPacket();
		msg.slot = buffer.readInt();
		return msg;
	}

	public static void handle(CRemoveAbilityPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ctx.get().getSender();
				IAbilityData abilityDataProps = AbilityDataCapability.get(player);

				Ability ability = abilityDataProps.getEquippedAbility(message.slot);
				if(ability == null)
					return;
				
				if(ability instanceof ContinuousAbility && ((ContinuousAbility)ability).isContinuous())
					((ContinuousAbility)ability).stopContinuity(player);
				else if(ability instanceof ChargeableAbility && ((ChargeableAbility)ability).isCharging())
					((ChargeableAbility)ability).stopCharging(player);
				else if(ability.isOnCooldown())
					ability.stopCooldown(player);
				
				abilityDataProps.setEquippedAbility(message.slot, null);
				
				if(abilityDataProps.getEquippedAbilitySlot(ability) >= 0)
					PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, ability), player);
			});	
		}
		ctx.get().setPacketHandled(true);
	}
}