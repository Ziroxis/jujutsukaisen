package com.example.jujutsukaisen.abilities.basic.punch;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class DivergentFistAbility extends PunchAbility {
    public static final DivergentFistAbility INSTANCE = new DivergentFistAbility();

    public DivergentFistAbility()
    {
        super("Divergent Fist", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("You punch your enemy in two times");
        this.setCursedEnergyCost(1);
        this.setMaxCooldown(5);
        this.onHitEntityEvent = this::onHitEntity;
    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        Vector3d speed = Beapi.propulsion(target, -1, -1);
        target.setDeltaMovement(speed.x, 0.2, speed.z);
        propsEntity.alterCursedEnergy(-5);
        PacketHandler.sendToServer(new CursedEnergySync(propsEntity.returnCursedEnergy()));

        return 8;
    }
}
