package com.example.jujutsukaisen.abilities.disaster_plants;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.ArrayList;
import java.util.List;

public class FlowerFieldAbility extends Ability {
    public static final FlowerFieldAbility INSTANCE = new FlowerFieldAbility();

    private List<LivingEntity> entities = new ArrayList<LivingEntity>();

    public FlowerFieldAbility()
    {
        super("Flower Field", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Extend a flower field around you causing the enemies to be weakened");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(15);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        //TODO add visuals to this
        List<LivingEntity> targets = Beapi.getEntitiesAround(player.blockPosition(), player.level, 10, LivingEntity.class);
        targets.remove(player);
        this.entities.addAll(targets);

        for (LivingEntity target : this.entities)
        {
            target.addEffect(new EffectInstance(Effects.WEAKNESS, 200, 1));
            target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 1));
        }
        return true;
    }
}
