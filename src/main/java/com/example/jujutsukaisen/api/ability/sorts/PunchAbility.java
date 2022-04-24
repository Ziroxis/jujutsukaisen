package com.example.jujutsukaisen.api.ability.sorts;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class PunchAbility extends ContinuousAbility{

    protected IOnHitEntity onHitEntityEvent = (player, target) -> { return 0; };

    public PunchAbility(String name, AbilityCategories.AbilityCategory category)
    {
        super(name, category);
    }

    public float hitEntity(PlayerEntity player, LivingEntity target)
    {
        float result = this.onHitEntityEvent.onHitEntity(player, target);


        this.stopContinuity(player);
        return result;
    }

    public interface IOnHitEntity extends Serializable
    {
        float onHitEntity(PlayerEntity player, LivingEntity target);
    }
}
