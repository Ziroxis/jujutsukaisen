package com.example.jujutsukaisen.quest.cursed_sword;

import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.KillEntityObjective;
import com.example.jujutsukaisen.quest.objectives.SharedKillChecks;
import net.minecraft.entity.player.PlayerEntity;

public class CursedSword_01 extends Quest {

    private Objective objective = new KillEntityObjective("Kill any entity once", 1, SharedKillChecks.EXISTS);

    public CursedSword_01()
    {
        super("cursedsword_01", "Proving your worth by a slash");
        this.setDescription("Prove your worth by killing an entity");
        this.addObjectives(this.objective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        player.giveExperienceLevels(50);
        IAbilityData propsAbility = AbilityDataCapability.get(player);
        IEntityStats propsStats = EntityStatsCapability.get(player);
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(propsAbility));
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));
        return true;
    }
}
