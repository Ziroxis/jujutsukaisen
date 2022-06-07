package com.example.jujutsukaisen.abilities.basic.punch;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousPunchAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.MinecraftForge;

/**
 * Continuously imbue your first with cursed energy
 */
public class CursedEnergyContinuousPunchAbility extends ContinuousPunchAbility {

    public static final CursedEnergyContinuousPunchAbility INSTANCE = new CursedEnergyContinuousPunchAbility();

    public CursedEnergyContinuousPunchAbility()
    {
        super("Cursed punch continuous", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("The user envelops his next punches with an amount of cursed energy");
        this.setMaxCooldown(5);
        this.setCursedEnergyCost(2);
        this.setExperiencePoint(3);
        this.setExperienceGainLevelCap(10);
        this.onHitEntityEvent = this::onHitEntity;

    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        Vector3d speed = Beapi.propulsion(target, -1, -1);
        target.setDeltaMovement(speed.x, 0.2, speed.z);
        propsEntity.alterCursedEnergy(-10);
        PacketHandler.sendToServer(new CursedEnergySync(propsEntity.returnCursedEnergy()));


        return 3;
    }
}
