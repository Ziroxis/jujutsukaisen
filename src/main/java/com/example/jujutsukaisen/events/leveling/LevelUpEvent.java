package com.example.jujutsukaisen.events.leveling;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LevelUpEvent extends PlayerEvent
{
    public int level;

    public LevelUpEvent(PlayerEntity player, int level)
    {
        super(player);
        this.level = level;
    }
}
