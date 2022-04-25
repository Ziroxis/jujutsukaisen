package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.projectiles.blood_manipulation.BloodShurikenProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class BloodShurikenAbility extends Ability {

    public static final Ability INSTANCE = new BloodShurikenAbility();

    public BloodShurikenAbility()
    {
        super("Blood Shuriken", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a shuriken made from blood and imbued in cursed energy");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(15);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        BloodShurikenProjectile projectile = new BloodShurikenProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 2f, 1);

        return true;
    }
}
