package com.example.jujutsukaisen.abilities.basic.punch;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
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

public class CursedPunchAbility extends PunchAbility {
    public static final CursedPunchAbility INSTANCE = new CursedPunchAbility();

    public CursedPunchAbility()
    {
        super("Cursed Punch", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("You envelop your next punch with an extra amount of cursed energy");
        this.setCursedEnergyCost(5);
        this.setMaxCooldown(5);
        this.setExperiencePoint(4);
        this.setExperienceGainLevelCap(15);
        this.onHitEntityEvent = this::onHitEntity;
    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        Vector3d speed = Beapi.propulsion(target, -1, -1);
        target.setDeltaMovement(speed.x, 0.2, speed.z);
        propsEntity.alterCursedEnergy(-10);
        PacketHandler.sendToServer(new CursedEnergySync(propsEntity.returnCursedEnergy()));

        return 5;
    }
}
