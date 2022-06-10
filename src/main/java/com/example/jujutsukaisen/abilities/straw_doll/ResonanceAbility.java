package com.example.jujutsukaisen.abilities.straw_doll;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.interfaces.IParallelContinuousAbility;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import com.example.jujutsukaisen.init.ModItems;
import com.example.jujutsukaisen.items.other.StrawDollItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

public class ResonanceAbility extends PunchAbility implements IParallelContinuousAbility {
    public static final ResonanceAbility INSTANCE = new ResonanceAbility();

    public ResonanceAbility()
    {
        super("Resonance", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Take a straw doll and resonate it with the player you just punched");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(20);
        this.setExperiencePoint(5);
        this.setExperienceGainLevelCap(40);
        this.onHitEntityEvent = this::onHitEntity;
    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        ItemStack doll = new ItemStack(ModItems.STRAW_DOLL.get());
        ((StrawDollItem) doll.getItem()).setDollOwner(doll, target);
        doll.setHoverName(new StringTextComponent(target.getDisplayName().getString() + "'s Doll"));
        player.inventory.add(doll);

        return 1;
    }
}
