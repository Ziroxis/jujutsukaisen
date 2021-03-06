package com.example.jujutsukaisen.abilities.reverse_cursed_energy;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModAttributes;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

import java.util.UUID;

public class SelfHealingRegenerationPassive extends PassiveAbility {

    public static final SelfHealingRegenerationPassive INSTANCE = new SelfHealingRegenerationPassive();
    private static final AttributeModifier REGENERATION = new AttributeModifier(UUID.fromString("f99b7406-dd33-11ec-9d64-0242ac120002"),
            "Regeneration", 2, AttributeModifier.Operation.ADDITION);

    public SelfHealingRegenerationPassive()
    {
        super("Self Healing Regeneration Passive", AbilityCategories.AbilityCategory.REVERSED);
        this.setDescription("Use your cursed energy passively to constantly regenerate");
        this.hideInGUI(false);
        this.duringPassiveEvent = this::duringPassiveEvent;
        this.offDuringPassive = this::offPassiveEvent;

    }

    private void duringPassiveEvent(PlayerEntity player)
    {
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if (!player.hasEffect(ModEffects.REGENERATION.get()))
            player.addEffect(new EffectInstance(ModEffects.REGENERATION.get(), 100000, 1));

    }
    private void offPassiveEvent(PlayerEntity player)
    {
        if (player.hasEffect(ModEffects.REGENERATION.get()))
            player.removeEffect(ModEffects.REGENERATION.get());

    }
}
