package com.example.jujutsukaisen.entities.npc;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SenseiEntity extends CreatureEntity {

    public SenseiEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 4));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 30)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.FOLLOW_RANGE, 100)
                .add(Attributes.MOVEMENT_SPEED, 2);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity p, Hand hand) {
        if (hand != Hand.MAIN_HAND)
            return ActionResultType.PASS;

        if (!p.level.isClientSide) {
            System.out.println("Hello young traveler");
        }
        return ActionResultType.PASS;

    }
}
