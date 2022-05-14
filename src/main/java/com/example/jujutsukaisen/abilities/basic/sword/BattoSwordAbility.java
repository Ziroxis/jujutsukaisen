package com.example.jujutsukaisen.abilities.basic.sword;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.interfaces.IMultiTargetAbility;
import com.example.jujutsukaisen.init.ModDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

/**
 * Simple ability that throws the user forward dealing damage to players
 */
public class BattoSwordAbility extends Ability implements IMultiTargetAbility {

    public static final BattoSwordAbility INSTANCE = new BattoSwordAbility();

    public BattoSwordAbility()
    {
        super("Batto Sword", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("The user dashes forward and rapidly slashes his opponent with his sword imbued with cursed energy");
        this.setMaxCooldown(5);
        this.setCursedEnergyCost(10);
        this.onUseEvent = this::onUseEvent;
        this.duringCooldownEvent = this::duringCooldown;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        if (!(player.getMainHandItem().getItem() instanceof SwordItem))
        {
            player.sendMessage(new StringTextComponent("Need to hold a sword!"), Util.NIL_UUID);
            return false;
        }


        this.clearTargets();

        Vector3d speed = Beapi.propulsion(player, 3, 3);
        player.setDeltaMovement(speed.x, 0.2, speed.z);
        player.hurtMarked = true;
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));

        return true;
    }

    private void duringCooldown(PlayerEntity player, int cooldownTimer)
    {
        if (this.canDealDamage())
        {
            List<LivingEntity> list = Beapi.getEntitiesNear(player.blockPosition(), player.level, 1.5, LivingEntity.class);
            list.remove(player);

            list.forEach(entity ->
            {
                if(this.isTarget(entity) && player.canSee(entity))
                    entity.hurt(ModDamageSource.causeAbilityDamage(player, INSTANCE), 10);
            });
        }
    }

    public boolean canDealDamage()
    {
        return this.cooldown > this.getMaxCooldown() * 0.9;
    }

}
