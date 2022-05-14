package com.example.jujutsukaisen.data.quest.objectives;

import com.example.jujutsukaisen.api.ability.Ability;
import net.minecraft.entity.player.PlayerEntity;

public interface IUseAbilityObjective
{
	boolean checkAbility(PlayerEntity player, Ability ability);
}
