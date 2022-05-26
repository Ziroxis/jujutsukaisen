package com.example.jujutsukaisen.abilities.reverse_cursed_energy;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SelfHealingRegenerationAbility extends Ability {

    public static final Ability INSTANCE = new SelfHealingRegenerationAbility();

    public SelfHealingRegenerationAbility()
    {
        super("Self Healing Regeneration", AbilityCategories.AbilityCategory.REVERSED);
        this.setDescription("Give yourself a short regeneration boost");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(35);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        player.addEffect(new EffectInstance(Effects.REGENERATION, 80, 1));

        return true;
    }
}
