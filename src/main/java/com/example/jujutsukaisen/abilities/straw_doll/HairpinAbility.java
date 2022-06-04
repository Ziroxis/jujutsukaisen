package com.example.jujutsukaisen.abilities.straw_doll;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class HairpinAbility extends Ability {
    public static final HairpinAbility INSTANCE = new HairpinAbility();

    private List<LivingEntity> entities = new ArrayList<LivingEntity>();

    public HairpinAbility()
    {
        super("Hairpin", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Detonate all nails in the enemies around you for huge damage");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(30);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        List<LivingEntity> targets = Beapi.getEntitiesAround(player.blockPosition(), player.level, 30, LivingEntity.class);
        targets.remove(player);
        this.entities.addAll(targets);

        for(LivingEntity target : this.entities)
        {
            if (target.hasEffect(ModEffects.HAIR_PIN.get()))
            {
                target.hurt(ModDamageSource.causeAbilityDamage(player, INSTANCE), 15);
                target.removeEffect(ModEffects.HAIR_PIN.get());
            }
        }
        return true;
    }
}
