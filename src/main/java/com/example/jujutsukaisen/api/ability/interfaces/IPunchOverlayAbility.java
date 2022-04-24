package com.example.jujutsukaisen.api.ability.interfaces;

import com.example.jujutsukaisen.api.ability.AbilityOverlay;
import net.minecraft.entity.LivingEntity;

public interface IPunchOverlayAbility
{
	AbilityOverlay getPunchOverlay(LivingEntity player);
}
