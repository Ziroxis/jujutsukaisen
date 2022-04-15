package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.COpenPlayerScreenPacket;
import com.example.jujutsukaisen.networking.client.CRequestSyncWorldDataPacket;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.*;
import com.example.jujutsukaisen.networking.server.SOpenPlayerScreenPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.SSyncWorldDataPacket;
import com.example.jujutsukaisen.networking.server.ability.*;

public class ModNetwork {
    public static void init()
    {
        //CLIENT PACKETS
        PacketHandler.registerPacket(CSyncentityStatsPacket.class, CSyncentityStatsPacket::encode, CSyncentityStatsPacket::decode, CSyncentityStatsPacket::handle);
        PacketHandler.registerPacket(CToggleCombatModePacket.class, CToggleCombatModePacket::encode, CToggleCombatModePacket::decode, CToggleCombatModePacket::handle);
        PacketHandler.registerPacket(CUseAbilityPacket.class, CUseAbilityPacket::encode, CUseAbilityPacket::decode, CUseAbilityPacket::handle);
        PacketHandler.registerPacket(CSyncAbilityDataPacket.class, CSyncAbilityDataPacket::encode, CSyncAbilityDataPacket::decode, CSyncAbilityDataPacket::handle);
        PacketHandler.registerPacket(CRemoveAbilityPacket.class, CRemoveAbilityPacket::encode, CRemoveAbilityPacket::decode, CRemoveAbilityPacket::handle);
        PacketHandler.registerPacket(CChangeCombatBarPacket.class, CChangeCombatBarPacket::encode, CChangeCombatBarPacket::decode, CChangeCombatBarPacket::handle);
        PacketHandler.registerPacket(CEquipAbilityPacket.class, CEquipAbilityPacket::encode, CEquipAbilityPacket::decode, CEquipAbilityPacket::handle);
        PacketHandler.registerPacket(CTogglePassiveAbilityPacket.class, CTogglePassiveAbilityPacket::encode, CTogglePassiveAbilityPacket::decode, CTogglePassiveAbilityPacket::handle);
        PacketHandler.registerPacket(COpenPlayerScreenPacket.class, COpenPlayerScreenPacket::encode, COpenPlayerScreenPacket::decode, COpenPlayerScreenPacket::handle);
        PacketHandler.registerPacket(CRequestSyncWorldDataPacket.class, CRequestSyncWorldDataPacket::encode, CRequestSyncWorldDataPacket::decode, CRequestSyncWorldDataPacket::handle);


        //SERVER PACKETS
        PacketHandler.registerPacket(SSyncEntityStatsPacket.class, SSyncEntityStatsPacket::encode, SSyncEntityStatsPacket::decode, SSyncEntityStatsPacket::handle);
        PacketHandler.registerPacket(SSyncAbilityDataPacket.class, SSyncAbilityDataPacket::encode, SSyncAbilityDataPacket::decode, SSyncAbilityDataPacket::handle);
        PacketHandler.registerPacket(SSyncEntityStatsPacket.class, SSyncEntityStatsPacket::encode, SSyncEntityStatsPacket::decode, SSyncEntityStatsPacket::handle);
        PacketHandler.registerPacket(SRecalculateEyeHeightPacket.class, SRecalculateEyeHeightPacket::encode, SRecalculateEyeHeightPacket::decode, SRecalculateEyeHeightPacket::handle);
        PacketHandler.registerPacket(SViewProtectionPacket.class, SViewProtectionPacket::encode, SViewProtectionPacket::decode, SViewProtectionPacket::handle);
        PacketHandler.registerPacket(SAnimeScreamPacket.class, SAnimeScreamPacket::encode, SAnimeScreamPacket::decode, SAnimeScreamPacket::handle);
        PacketHandler.registerPacket(SUpdateEquippedAbilityPacket.class, SUpdateEquippedAbilityPacket::encode, SUpdateEquippedAbilityPacket::decode, SUpdateEquippedAbilityPacket::handle);
        PacketHandler.registerPacket(SOpenPlayerScreenPacket.class, SOpenPlayerScreenPacket::encode, SOpenPlayerScreenPacket::decode, SOpenPlayerScreenPacket::handle);
        PacketHandler.registerPacket(SChangeCombatBarPacket.class, SChangeCombatBarPacket::encode, SChangeCombatBarPacket::decode, SChangeCombatBarPacket::handle);
        PacketHandler.registerPacket(SSyncWorldDataPacket.class, SSyncWorldDataPacket::encode, SSyncWorldDataPacket::decode, SSyncWorldDataPacket::handle);


    }
}
