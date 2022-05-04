package com.example.jujutsukaisen.abilities.projection_sorcery;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

import java.util.ArrayList;
import java.util.List;

public class FrameCatchAbility extends Ability {

    public static final FrameCatchAbility INSTANCE = new FrameCatchAbility();
    private List<LivingEntity> entities = new ArrayList<LivingEntity>();

    public FrameCatchAbility()
    {
        super("Frame Catch", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Catches the entity the user is looking at in a frame and deals damage");
        this.setMaxCooldown(20);
        this.setCursedEnergyCost(5);
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
            target.teleportTo(player.getX(), player.getY(), player.getZ());
            target.addEffect(new EffectInstance(ModEffects.MOVEMENT_BLOCKED.get(), 80, 10));
        }

        return true;
    }
}
