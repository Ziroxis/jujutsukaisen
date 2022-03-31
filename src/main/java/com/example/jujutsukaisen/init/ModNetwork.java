package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;

public class ModNetwork {
    public static void init()
    {
        //CLIENT PACKETS
        PacketHandler.registerPacket(CSyncentityStatsPacket.class, CSyncentityStatsPacket::encode, CSyncentityStatsPacket::decode, CSyncentityStatsPacket::handle);

        //SERVER PACKETS
        PacketHandler.registerPacket(SSyncEntityStatsPacket.class, SSyncEntityStatsPacket::encode, SSyncEntityStatsPacket::decode, SSyncEntityStatsPacket::handle);

    }
}
