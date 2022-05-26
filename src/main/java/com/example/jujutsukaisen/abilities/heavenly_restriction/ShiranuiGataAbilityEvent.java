package com.example.jujutsukaisen.abilities.heavenly_restriction;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.basic.punch.CursedEnergyContinuousPunchAbility;
import com.example.jujutsukaisen.abilities.basic.punch.CursedPunchAbility;
import com.example.jujutsukaisen.abilities.basic.punch.DivergentFistAbility;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ShiranuiGataAbilityEvent {

    @SubscribeEvent
    public static void gettingHurt(LivingHurtEvent event)
    {
        System.out.println("Check 1");
        if (!(event.getEntityLiving() instanceof PlayerEntity))
            return;
        System.out.println("Check 2");
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        IEntityStats statsProps = EntityStatsCapability.get(player);
        IAbilityData abilityProps = AbilityDataCapability.get(player);
        // The entity that got damaged is an entity and has the restriction of Heavenly
        for (Ability ability : abilityProps.getEquippedAbilities(AbilityCategories.AbilityCategory.ALL))
        {
            if (ability == null)
                continue;

            try
            {
                if (ability instanceof ShiranuiGataAbility && ability.isContinuous())
                {
                    System.out.println("Check 3");
                    float damage = event.getAmount();
                    Entity attacker = event.getSource().getEntity();
                    assert attacker != null;
                    attacker.hurt(ModDamageSource.causeAbilityDamage(player, ShiranuiGataAbility.INSTANCE), damage * 2);
                    ability.setState(Ability.State.COOLDOWN);
                    PacketHandler.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityProps), player);
                    event.setCanceled(true);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
