package com.example.jujutsukaisen.abilities.tenshadow_technique;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import net.minecraft.entity.player.PlayerEntity;

public class DivineDogsAbility extends Ability {

    public static final Ability INSTANCE = new DivineDogsAbility();


    public DivineDogsAbility()
    {
        super("Divine Dogs", AbilityHelper.getTechniqueCategory());
        this.setDescription("Summons two divine dogs");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(20);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        System.out.println("check 1");

        return true;
    }
}
