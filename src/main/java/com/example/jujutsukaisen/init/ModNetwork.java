package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.networking.CursedEnergyMaxSync;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.*;
import com.example.jujutsukaisen.networking.client.ability.*;
import com.example.jujutsukaisen.networking.server.*;
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
        PacketHandler.registerPacket(COpenCursedSpiritAcceptanceScreenPacket.class, COpenCursedSpiritAcceptanceScreenPacket::encode, COpenCursedSpiritAcceptanceScreenPacket::decode, COpenCursedSpiritAcceptanceScreenPacket::handle);
        PacketHandler.registerPacket(COpenPlayerScreenPacket.class, COpenPlayerScreenPacket::encode, COpenPlayerScreenPacket::decode, COpenPlayerScreenPacket::handle);
        PacketHandler.registerPacket(CRequestSyncWorldDataPacket.class, CRequestSyncWorldDataPacket::encode, CRequestSyncWorldDataPacket::decode, CRequestSyncWorldDataPacket::handle);
        PacketHandler.registerPacket(CSyncQuestDataPacket.class, CSyncQuestDataPacket::encode, CSyncQuestDataPacket::decode, CSyncQuestDataPacket::handle);
        PacketHandler.registerPacket(CAbandonQuestPacket.class, CAbandonQuestPacket::encode, CAbandonQuestPacket::decode, CAbandonQuestPacket::handle);
        PacketHandler.registerPacket(CRequestSyncQuestDataPacket.class, CRequestSyncQuestDataPacket::encode, CRequestSyncQuestDataPacket::decode, CRequestSyncQuestDataPacket::handle);
        PacketHandler.registerPacket(CStartObjectiveEventPacket.class, CStartObjectiveEventPacket::encode, CStartObjectiveEventPacket::decode, CStartObjectiveEventPacket::handle);
        PacketHandler.registerPacket(CGiveItemStackPacket.class, CGiveItemStackPacket::encode, CGiveItemStackPacket::decode, CGiveItemStackPacket::handle);


        //SERVER PACKETS
        PacketHandler.registerPacket(SSyncEntityStatsPacket.class, SSyncEntityStatsPacket::encode, SSyncEntityStatsPacket::decode, SSyncEntityStatsPacket::handle);
        PacketHandler.registerPacket(SSyncAbilityDataPacket.class, SSyncAbilityDataPacket::encode, SSyncAbilityDataPacket::decode, SSyncAbilityDataPacket::handle);
        PacketHandler.registerPacket(SSyncEntityStatsPacket.class, SSyncEntityStatsPacket::encode, SSyncEntityStatsPacket::decode, SSyncEntityStatsPacket::handle);
        PacketHandler.registerPacket(SRecalculateEyeHeightPacket.class, SRecalculateEyeHeightPacket::encode, SRecalculateEyeHeightPacket::decode, SRecalculateEyeHeightPacket::handle);
        PacketHandler.registerPacket(SViewProtectionPacket.class, SViewProtectionPacket::encode, SViewProtectionPacket::decode, SViewProtectionPacket::handle);
        PacketHandler.registerPacket(SAnimeScreamPacket.class, SAnimeScreamPacket::encode, SAnimeScreamPacket::decode, SAnimeScreamPacket::handle);
        PacketHandler.registerPacket(SUpdateEquippedAbilityPacket.class, SUpdateEquippedAbilityPacket::encode, SUpdateEquippedAbilityPacket::decode, SUpdateEquippedAbilityPacket::handle);
        PacketHandler.registerPacket(SOpenCursedSpiritAcceptanceScreenPacket.class, SOpenCursedSpiritAcceptanceScreenPacket::encode, SOpenCursedSpiritAcceptanceScreenPacket::decode, SOpenCursedSpiritAcceptanceScreenPacket::handle);
        PacketHandler.registerPacket(SOpenPlayerScreenPacket.class, SOpenPlayerScreenPacket::encode, SOpenPlayerScreenPacket::decode, SOpenPlayerScreenPacket::handle);
        PacketHandler.registerPacket(SChangeCombatBarPacket.class, SChangeCombatBarPacket::encode, SChangeCombatBarPacket::decode, SChangeCombatBarPacket::handle);
        PacketHandler.registerPacket(SSyncWorldDataPacket.class, SSyncWorldDataPacket::encode, SSyncWorldDataPacket::decode, SSyncWorldDataPacket::handle);
        PacketHandler.registerPacket(SSyncQuestDataPacket.class, SSyncQuestDataPacket::encode, SSyncQuestDataPacket::decode, SSyncQuestDataPacket::handle);
        PacketHandler.registerPacket(SSyncTriggerQuest.class, SSyncTriggerQuest::encode, SSyncTriggerQuest::decode, SSyncTriggerQuest::handle);

        //SYNC
        PacketHandler.registerPacket(CursedEnergySync.class, CursedEnergySync::encode, CursedEnergySync::decode, CursedEnergySync::handle);
        PacketHandler.registerPacket(CursedEnergyMaxSync.class, CursedEnergyMaxSync::encode, CursedEnergyMaxSync::decode, CursedEnergyMaxSync::handle);

    }
}
