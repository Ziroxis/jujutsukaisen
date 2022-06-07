package com.example.jujutsukaisen.abilities.basic.sword;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.basic.EveningMoonProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class EveningMoonAbility extends Ability {

    public static final EveningMoonAbility INSTANCE = new EveningMoonAbility();

    public EveningMoonAbility()
    {
        super("Evening Moon", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("Shoots a crescent air slash made by a sword slash");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(15);
        this.setExperiencePoint(20);
        this.setExperienceGainLevelCap(20);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        if (!(player.getMainHandItem().getItem() instanceof SwordItem))
        {
            player.sendMessage(new StringTextComponent("Need to hold a sword!"), Util.NIL_UUID);
            return false;
        }
        EveningMoonProjectile projectile = new EveningMoonProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 2f, 1);

        return true;
    }
}
