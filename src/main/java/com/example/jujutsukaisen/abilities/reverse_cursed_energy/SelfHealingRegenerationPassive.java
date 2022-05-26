package com.example.jujutsukaisen.abilities.reverse_cursed_energy;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class SelfHealingRegenerationPassive extends PassiveAbility {

    public static final SelfHealingRegenerationPassive INSTANCE = new SelfHealingRegenerationPassive();
    private static final AttributeModifier REGENERATION = new AttributeModifier(UUID.fromString("f99b7406-dd33-11ec-9d64-0242ac120002"),
            "Regeneration", 1, AttributeModifier.Operation.ADDITION);

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

        if(!player.getAttribute(ModAttributes.REGEN_RATE.get()).hasModifier(REGENERATION))
            player.getAttribute(ModAttributes.REGEN_RATE.get()).addTransientModifier(REGENERATION);

    }
    private void offPassiveEvent(PlayerEntity player)
    {
        if(player.getAttribute(ModAttributes.REGEN_RATE.get()).hasModifier(REGENERATION))
            player.getAttribute(ModAttributes.REGEN_RATE.get()).removeModifier(REGENERATION);

    }
}
