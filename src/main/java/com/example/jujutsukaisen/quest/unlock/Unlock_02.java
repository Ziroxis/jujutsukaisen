package com.example.jujutsukaisen.quest.unlock;

import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.ObtainItemObjective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

public class Unlock_02 extends Quest {

    private Objective objective = new ObtainItemObjective("Prove your kill", 1, Items.BONE);

    public Unlock_02()
    {
        super("ulock_02", "Trial: Collector");
        this.addObjective(this.objective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        player.giveExperienceLevels(10);
        return true;
    }
}
