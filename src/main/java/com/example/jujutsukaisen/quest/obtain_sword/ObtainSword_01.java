package com.example.jujutsukaisen.quest.obtain_sword;

import com.example.jujutsukaisen.init.ModEntities;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.KillEntityObjective;

public class ObtainSword_01 extends Quest {
    private Objective killObjective = new KillEntityObjective("Kill 5 roppongis", 5, ModEntities.ROPPONGI.get());

    public ObtainSword_01()
    {
        super("obtainsword_01", "Show you're a true cursed spirits killer!");
        this.setDescription("Kill a few cursed spirits");
    }
}
