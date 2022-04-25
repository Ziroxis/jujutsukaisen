package com.example.jujutsukaisen.abilities.blood_manipulation;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.RepeaterAbility;
import com.example.jujutsukaisen.entities.projectiles.blood_manipulation.BloodMeteoriteProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class BloodMeteoriteAbility extends RepeaterAbility {

    public static final Ability INSTANCE = new BloodMeteoriteAbility();

    public BloodMeteoriteAbility()
    {
        super("Blood Meteorite", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user fires multiple densely compressed clutters of blood");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(20);
        this.setMaxRepeaterCount(8, 5);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        BloodMeteoriteProjectile projectile = new BloodMeteoriteProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 0.5f);

        return true;
    }
}
