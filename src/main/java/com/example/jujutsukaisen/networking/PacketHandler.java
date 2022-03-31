package com.example.jujutsukaisen.networking;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler {

    private static int packet = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("jujutsukaisen", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static <MSG> void registerPacket(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer)
    {
        INSTANCE.registerMessage(packet++, messageType, encoder, decoder, messageConsumer);
    }

    public static <MSG> void sendToServer(MSG msg)
    {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendTo(MSG msg, PlayerEntity player)
    {
        if(!(player instanceof ServerPlayerEntity))
            return;

        INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), msg);
    }

    public static <MSG> void sendToAll(MSG msg)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendToAllTrackingAndSelf(MSG msg, LivingEntity tracked)
    {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> tracked), msg);
    }

    public static <MSG> void sendToAllAround(MSG msg, LivingEntity sender)
    {
        try
        {
            INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(sender.getX(), sender.getY(), sender.getZ(), 250, sender.level.dimension())), msg);
            if (sender instanceof ServerPlayerEntity)
                sendTo(msg, (ServerPlayerEntity) sender);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
    }
}
