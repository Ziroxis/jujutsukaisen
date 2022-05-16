package com.example.jujutsukaisen.entities.npc;

import com.example.jujutsukaisen.init.ModQuests;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class MaidQuesterEntity extends Quester {
    public MaidQuesterEntity(EntityType type, World world) {
        super(type, world);
        this.before = "Hey, could you help me?";
        this.during = "Could you do what I ask please?";
        this.after = "Many thanks!";
        this.quest = ModQuests.CURSED_PUNCHES_01;
    }

}
