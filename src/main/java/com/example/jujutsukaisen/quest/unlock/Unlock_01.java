package com.example.jujutsukaisen.quest.unlock;

import com.example.jujutsukaisen.abilities.basic.CursedEnergyContinuousPunchAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.KillEntityObjective;
import com.example.jujutsukaisen.quest.objectives.SharedKillChecks;
import net.minecraft.entity.player.PlayerEntity;

public class Unlock_01 extends Quest {

    private Objective objective = new KillEntityObjective("Kill any entity once", 1, SharedKillChecks.EXISTS);

    public Unlock_01()
    {
        super("unlock_01", "Proving your worth by blood");
        this.setDescription("Prove your worth by killing an entity");
        this.addObjectives(this.objective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        player.giveExperienceLevels(5);
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(CursedEnergyContinuousPunchAbility.INSTANCE);
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(props));
        return true;
    }
}
