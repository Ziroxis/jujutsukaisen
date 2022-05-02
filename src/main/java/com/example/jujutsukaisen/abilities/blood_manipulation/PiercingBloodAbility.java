package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ChargeableAbility;
import com.example.jujutsukaisen.entities.projectiles.blood_manipulation.PiercingBloodProjectile;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PiercingBloodAbility extends ChargeableAbility {

    public static final PiercingBloodAbility INSTANCE = new PiercingBloodAbility();
    private boolean cancelled = false;

    public PiercingBloodAbility()
    {
        super("Piercing Blood", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Concetrates everything on throwing one Piercing Blood projectile");
        this.setMaxCooldown(15);
        this.setMaxChargeTime(10);
        this.setCursedEnergyCost(20);
        this.setCancelable();

        this.onStartChargingEvent = this::onStartChargingEvent;
        this.onEndChargingEvent = this::onEndChargingEvent;
        this.duringChargingEvent = this::duringChargingEvent;

    }

    private boolean onStartChargingEvent(PlayerEntity player)
    {
        this.cancelled = false;
        return true;
    }

    private void duringChargingEvent(PlayerEntity player, int chargeTimer)
    {
        if (player.invulnerableTime > 0)
        {
            this.cancelled = true;
            this.stopCharging(player);
        }

        player.addEffect(new EffectInstance(ModEffects.MOVEMENT_BLOCKED.get(), 200, 10));
    }

    private boolean onEndChargingEvent(PlayerEntity player)
    {
        if (this.cancelled)
            return true;

        player.removeEffect(ModEffects.MOVEMENT_BLOCKED.get());

        int charge = this.getMaxChargeTime() - this.getChargeTime();

        if (charge < 20 * 15)
            return false;

        PiercingBloodProjectile projectile = new PiercingBloodProjectile(player.level, player);
        projectile.setDamage(charge / 10f);
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 3, 0.1f);
        player.level.addFreshEntity(projectile);

        int cooldown = (int) Math.round(charge / 20.0) + 5;
        this.setMaxCooldown(cooldown);
        return true;
    }
}
