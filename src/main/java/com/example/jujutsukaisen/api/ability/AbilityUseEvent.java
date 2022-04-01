package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.api.ability.Ability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class AbilityUseEvent extends AbilityEvent {
    public AbilityUseEvent(PlayerEntity player, Ability ability)
    {
        super(player, ability);
    }

}
