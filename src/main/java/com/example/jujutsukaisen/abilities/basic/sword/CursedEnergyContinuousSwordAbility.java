package com.example.jujutsukaisen.abilities.basic.sword;

import com.example.jujutsukaisen.abilities.disaster_flames.EmberInsectsAbility;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousPunchAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
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
import net.minecraft.util.text.StringTextComponent;

public class CursedEnergyContinuousSwordAbility extends ContinuousPunchAbility {

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
        ItemStack weapon = player.getItemInHand(player.getUsedItemHand());
        float damage = weapon.getDamageValue();
        if (!(player.getMainHandItem().getItem() instanceof SwordItem))
        {
            player.sendMessage(new StringTextComponent("Need to hold a sword!"), Util.NIL_UUID);
            return 0;
        }

        IEntityStats propsEntity = EntityStatsCapability.get(player);
        float strength = (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        float finalDamage = (damage + strength) * (float)propsEntity.getDamageMultiplier();

        target.knockback(1, 2, 2);
        target.hurt(ModDamageSource.causeAbilityDamage(player, this), finalDamage);
        propsEntity.alterCursedEnergy(-1);
        PacketHandler.sendToServer(new CursedEnergySync(propsEntity.returnCursedEnergy()));

        return 5;
    }
}
