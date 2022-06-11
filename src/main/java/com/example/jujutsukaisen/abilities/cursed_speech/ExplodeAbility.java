package com.example.jujutsukaisen.abilities.cursed_speech;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import com.example.jujutsukaisen.api.ability.sorts.ExplosionAbility;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.particles.CommonExplosionParticleEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ExplodeAbility extends Ability {

    public static final ExplodeAbility INSTANCE = new ExplodeAbility();
    private List<LivingEntity> entities = new ArrayList<LivingEntity>();
    ModDamageSource modDamageSource;

    public ExplodeAbility()
    {
        super("Explode", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user screams explode which makes the target explode");
        this.setMaxCooldown(30);
        this.setCursedEnergyCost(30);
        this.setExperiencePoint(40);
        this.setExperienceGainLevelCap(50);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        RayTraceResult mop = Beapi.rayTraceBlocksAndEntities(player, 32);

        double i = mop.getLocation().x;
        double j = mop.getLocation().y - (mop instanceof EntityRayTraceResult ? 1 : 0);
        double k = mop.getLocation().z;

        List<LivingEntity> targets = Beapi.getEntitiesNear(new BlockPos(i, j, k), player.level, 1, LivingEntity.class);
        targets.remove(player);
        this.entities.addAll(targets);


        for(LivingEntity target : this.entities)
        {
            double targetX = target.getX();
            double targetY = target.getY();
            double targetZ = target.getZ();
            double playerX = player.getX();
            double playerY = player.getY();
            double playerZ = player.getZ();
            double distance = Math.sqrt(Math.pow(targetX - playerX, 2) + Math.pow(targetZ - playerZ, 2));

            if (distance <= 32)
                player.sendMessage(new StringTextComponent("EXPLODE!"), Util.NIL_UUID);

            ExplosionAbility explosionAbility = AbilityHelper.newExplosion(player, player.level, targetX, targetY, targetZ, 3);
            explosionAbility.setStaticDamage(15);
            explosionAbility.setExplosionSound(true);
            explosionAbility.setSmokeParticles(new CommonExplosionParticleEffect(8));
            explosionAbility.setDamageSource(ModDamageSource.causeAbilityDamage(player, this, "player"));
            explosionAbility.doExplosion();
        }

        return true;
    }
}

