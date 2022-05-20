package com.example.jujutsukaisen.abilities.basic.sword;

import com.example.jujutsukaisen.abilities.disaster_flames.EmberInsectsAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousPunchAbility;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousSwordAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.entities.CurseEntity;
import com.example.jujutsukaisen.events.CursedSpiritInvincibility;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

public class CursedEnergyContinuousSwordAbility extends ContinuousSwordAbility {

    public static final CursedEnergyContinuousSwordAbility INSTANCE = new CursedEnergyContinuousSwordAbility();

    public CursedEnergyContinuousSwordAbility()
    {
        super("Cursed Sword", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("Imbue your sword with cursed energy");
        this.setMaxCooldown(5);
        this.setCursedEnergyCost(4);
        this.onHitEntityEvent = this::onHitEntity;
    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        if (!(player.getMainHandItem().getItem() instanceof SwordItem))
        {
            player.sendMessage(new StringTextComponent("Need to hold a sword!"), Util.NIL_UUID);
            return 0;
        }

        IEntityStats propsEntity = EntityStatsCapability.get(player);
        if (target instanceof CurseEntity)
        {
            target.invulnerableTime = 0;
            target.hurt(ModDamageSource.causeAbilityDamage(player, new Ability("Cursed sword attack", AbilityCategories.AbilityCategory.ALL)), 5);
        }
        Vector3d speed = Beapi.propulsion(target, -1, -1);
        target.setDeltaMovement(speed.x, 0.2, speed.z);
        propsEntity.alterCursedEnergy(-1);
        PacketHandler.sendToServer(new CursedEnergySync(propsEntity.returnCursedEnergy()));

        return 5;
    }
}
