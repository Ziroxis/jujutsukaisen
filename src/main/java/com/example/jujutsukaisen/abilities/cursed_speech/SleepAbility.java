package com.example.jujutsukaisen.abilities.cursed_speech;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class SleepAbility extends Ability {

    public static final SleepAbility INSTANCE = new SleepAbility();
    private List<LivingEntity> entities = new ArrayList<LivingEntity>();

    public SleepAbility()
    {
        super("Sleep", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("The user screams sleep which makes the target not be able to move for a few seconds");
        this.setMaxCooldown(10);
        this.setCursedEnergyCost(30);
        this.setExperiencePoint(10);
        this.setExperienceGainLevelCap(10);
        this.onUseEvent = this::onUseEvent;
    }

    private boolean onUseEvent(PlayerEntity player)
    {
        RayTraceResult mop = Beapi.rayTraceBlocksAndEntities(player, 32);

        double i = mop.getLocation().x;
        double j = mop.getLocation().y - (mop instanceof EntityRayTraceResult ? 1 : 0);
        double k = mop.getLocation().z;

        List<LivingEntity> targets = Beapi.getEntitiesNear(new BlockPos(i, j, k), player.level, 5, LivingEntity.class);
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
                player.sendMessage(new StringTextComponent("SLEEP!"), Util.NIL_UUID);
            target.addEffect(new EffectInstance(ModEffects.SLEEP.get(), 80, 10));
        }

        return true;
    }
}
