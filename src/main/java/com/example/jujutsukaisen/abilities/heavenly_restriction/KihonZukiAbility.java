package com.example.jujutsukaisen.abilities.heavenly_restriction;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class KihonZukiAbility extends PunchAbility {

    public static final KihonZukiAbility INSTANCE = new KihonZukiAbility();

    public KihonZukiAbility()
    {
        super("Kihon Zuki", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Leap towards your enemy and throw a devastating punch");
        this.setMaxCooldown(5);
        this.setCursedEnergyCost(0);

        this.onStartContinuityEvent = this::onStartContinuityEvent;
        this.onHitEntityEvent = this::onHitEntityEvent;
    }

    private boolean onStartContinuityEvent(PlayerEntity player)
    {
        Vector3d speed = Beapi.propulsion(player, 3, 3);
        player.setDeltaMovement(speed.x, 0.2, speed.z);

        return true;
    }

    private float onHitEntityEvent(PlayerEntity player, LivingEntity target)
    {


        return 25;
    }
}
