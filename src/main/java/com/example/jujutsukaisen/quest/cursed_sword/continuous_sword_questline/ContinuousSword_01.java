package com.example.jujutsukaisen.quest.cursed_sword.continuous_sword_questline;

import com.example.jujutsukaisen.abilities.basic.punch.CursedPunchAbility;
import com.example.jujutsukaisen.abilities.basic.sword.BattoSwordAbility;
import com.example.jujutsukaisen.abilities.basic.sword.CursedEnergyContinuousSwordAbility;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousSwordAbility;
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

public class ContinuousSword_01 extends Quest {

    private Objective objective = new KillEntityObjective("Kill any entity once", 1, SharedKillChecks.EXISTS);

    public ContinuousSword_01()
    {
        super("continuoussword_01", "Proving your worth by a slash");
        this.addObjectives(this.objective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        IAbilityData propsAbility = AbilityDataCapability.get(player);
        IEntityStats propsStats = EntityStatsCapability.get(player);
        propsStats.alterExperience(50);
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(propsAbility));
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));
        return true;
    }
}
