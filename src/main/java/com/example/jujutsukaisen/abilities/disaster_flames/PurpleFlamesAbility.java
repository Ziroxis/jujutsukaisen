package com.example.jujutsukaisen.abilities.disaster_flames;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.init.ModDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class PurpleFlamesAbility extends Ability {
    public static final PurpleFlamesAbility INSTANCE = new PurpleFlamesAbility();
    private List<LivingEntity> entities = new ArrayList<LivingEntity>();

    public PurpleFlamesAbility()
    {
        super("Purple Flames", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Burn the enemies around you for a long period");
        this.setMaxCooldown(15);
        this.setCursedEnergyCost(17);

        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        List<LivingEntity> targets = Beapi.getEntitiesAround(player.blockPosition(), player.level, 10, LivingEntity.class);
        targets.remove(player);
        this.entities.addAll(targets);

        for(LivingEntity target : this.entities)
        {
            target.hurt(ModDamageSource.causeAbilityDamage(player, this), 10);
            target.setSecondsOnFire(12);
        }
        return true;
    }
}
