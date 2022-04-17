package com.example.jujutsukaisen.events.leveling;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MaxExperienceUpEvent extends PlayerEvent
{
    public int maxExperience;

    public MaxExperienceUpEvent(PlayerEntity player, int maxExperience)
    {
        super(player);
        this.maxExperience = maxExperience;
    }
}
