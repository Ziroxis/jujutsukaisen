package com.example.jujutsukaisen.abilities.straw_doll;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.RepeaterAbility;
import com.example.jujutsukaisen.entities.projectiles.straw_doll.SmallNailProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class NailShotAbility extends RepeaterAbility {

    public static final NailShotAbility INSTANCE = new NailShotAbility();

    public NailShotAbility()
    {
        super("Nail Shot", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a huge amount of nails");
        this.setMaxCooldown(5);
        this.setCursedEnergyCost(10);
        this.setMaxRepeaterCount(5, 5);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        SmallNailProjectile projectile = new SmallNailProjectile(player.level, player);
        player.level.addFreshEntity(projectile);
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 1f);
        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        return true;
    }
}
