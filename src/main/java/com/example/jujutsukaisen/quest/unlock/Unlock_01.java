package com.example.jujutsukaisen.quest.unlock;

import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.KillEntityObjective;
import com.example.jujutsukaisen.quest.objectives.SharedKillChecks;
import net.minecraft.entity.player.PlayerEntity;

public class Unlock_01 extends Quest {

    private Objective objective = new KillEntityObjective("Prove you can be an unrelentless hunter", 1, SharedKillChecks.EXISTS);

    public Unlock_01()
    {
        super("unlock_01", "Trial: Killer");
        this.addObjectives(this.objective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        player.giveExperienceLevels(5);
        return true;
    }
}
