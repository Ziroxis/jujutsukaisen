package com.example.jujutsukaisen.abilities.disaster_tides;

import com.example.jujutsukaisen.abilities.blood_manipulation.BloodMeteoriteAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.RepeaterAbility;
import com.example.jujutsukaisen.entities.projectiles.disaster_tides.CursedFishProjectile;
import com.example.jujutsukaisen.entities.projectiles.disaster_tides.CursedSharkProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.world.server.ServerWorld;

public class DeathSwarmAbility extends RepeaterAbility {
    public static final DeathSwarmAbility INSTANCE = new DeathSwarmAbility();

    public DeathSwarmAbility()
    {
        super("Death Swarm", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Shoots a huge amount of sea shinigamis");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(20);
        this.setMaxRepeaterCount(15, 4);
        this.setExperiencePoint(30);
        this.setExperienceGainLevelCap(50);

        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        int rng = Beapi.RNG(2);
        switch (rng)
        {
            case 0:
                CursedSharkProjectile projectileShark = new CursedSharkProjectile(player.level, player);
                player.level.addFreshEntity(projectileShark);
                projectileShark.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 1.5f);
                break;
            case 1:
                CursedFishProjectile projectileFish = new CursedFishProjectile(player.level, player);
                player.level.addFreshEntity(projectileFish);
                projectileFish.shootFromRotation(player, player.xRot, player.yRot, 0, 1f, 1.5f);
        }

        ((ServerWorld) player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
        return true;
    }
}
