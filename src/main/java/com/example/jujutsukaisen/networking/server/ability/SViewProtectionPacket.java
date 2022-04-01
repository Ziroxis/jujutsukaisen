package com.example.jujutsukaisen.networking.server.ability;

import com.example.jujutsukaisen.api.ability.AbilityHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class SViewProtectionPacket {

	private boolean state;
	private int[] midPoint;
	private int size;

	public SViewProtectionPacket() {
	}

	public SViewProtectionPacket(boolean state, int[] midPoint, int size) {
		this.state = state;
		this.midPoint = midPoint;
		this.size = size;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeBoolean(this.state);
		buffer.writeInt(this.midPoint[0]);
		buffer.writeInt(this.midPoint[1]);
		buffer.writeInt(this.midPoint[2]);
		buffer.writeInt(this.size);
	}

	public static SViewProtectionPacket decode(PacketBuffer buffer) {
		SViewProtectionPacket msg = new SViewProtectionPacket();
		msg.state = buffer.readBoolean();
		msg.midPoint = new int[]{buffer.readInt(), buffer.readInt(), buffer.readInt()};
		msg.size = buffer.readInt();
		return msg;
	}

	public static void handle(SViewProtectionPacket message, final Supplier<NetworkEvent.Context> ctx) {
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
			ctx.get().enqueueWork(() ->
			{
				ClientHandler.handle(message);
			});
		}

		ctx.get().setPacketHandled(true);
	}

	public static class ClientHandler {

		@OnlyIn(Dist.CLIENT)
		public static void handle(SViewProtectionPacket message) {
			PlayerEntity player = Minecraft.getInstance().player;

			if (message.state) {
				AbilityHelper.createEmptyCube(player.level, message.midPoint[0], message.midPoint[1], message.midPoint[2], message.size, message.size, message.size, Blocks.BLUE_STAINED_GLASS, null);
				AbilityHelper.createEmptyCube(player.level, message.midPoint[0], message.midPoint[1], message.midPoint[2], 1, 1, 1, Blocks.RED_STAINED_GLASS, null);
			} else {
				AbilityHelper.createEmptyCube(player.level, message.midPoint[0], message.midPoint[1], message.midPoint[2], message.size, message.size, message.size, Blocks.AIR, null);
				AbilityHelper.createEmptyCube(player.level, message.midPoint[0], message.midPoint[1], message.midPoint[2], 1, 1, 1, Blocks.AIR, null);
			}
		}
	}
}