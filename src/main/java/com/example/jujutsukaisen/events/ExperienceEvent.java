package com.example.jujutsukaisen.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ExperienceEvent extends PlayerEvent {
    public int experience;

    public ExperienceEvent(PlayerEntity player, int doriki)
    {
        super(player);
        this.experience = doriki;
    }

}
