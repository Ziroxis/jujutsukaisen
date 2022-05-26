package com.example.jujutsukaisen.abilities.reverse_cursed_energy;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class HealOtherRegenerationAbility extends PunchAbility {

    public static final HealOtherRegenerationAbility INSTANCE = new HealOtherRegenerationAbility();

    public HealOtherRegenerationAbility()
    {
        super("Heal Other Regeneration", AbilityCategories.AbilityCategory.REVERSED);
        this.setDescription("Give someone else a bit of your reverse cursed energy to heal them");
        this.setCursedEnergyCost(20);
        this.setMaxCooldown(20);
        this.onHitEntityEvent = this::onHitEntityEvent;
    }

    private float onHitEntityEvent(PlayerEntity player, LivingEntity target)
    {
        target.addEffect(new EffectInstance(Effects.REGENERATION, 80, 2));
        return 2;
    }
}
