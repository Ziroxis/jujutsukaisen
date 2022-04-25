package com.example.jujutsukaisen.events.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodEdgeAbility;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodMeteoriteAbility;
import com.example.jujutsukaisen.abilities.blood_manipulation.FlowingRedScaleAbility;
import com.example.jujutsukaisen.abilities.blood_manipulation.PiercingBloodAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameBreakAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameCatchAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameSpeedAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameTeleportationAbility;
import com.example.jujutsukaisen.abilities.tenshadow_technique.ShadowInventoryAbility;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class AbilityProgressionEvents
{

	@SubscribeEvent
	public static void onLevelGained(ExperienceUpEvent event)
	{
		IEntityStats props = EntityStatsCapability.get(event.getPlayer());
		if (props.getTechnique().equals(ModValues.BLOOD_MANIPULATION))
		{
			gainAbility(event.getPlayer(), 5, BloodEdgeAbility.INSTANCE);
			gainAbility(event.getPlayer(), 7, BloodMeteoriteAbility.INSTANCE);
			gainAbility(event.getPlayer(), 10, PiercingBloodAbility.INSTANCE);
			gainAbility(event.getPlayer(), 15, FlowingRedScaleAbility.INSTANCE);

		}
		if (props.getTechnique().equals(ModValues.PROJECTION_SORCERY))
		{
			gainAbility(event.getPlayer(), 5, FrameBreakAbility.INSTANCE);
			gainAbility(event.getPlayer(), 15, FrameTeleportationAbility.INSTANCE);
			gainAbility(event.getPlayer(), 25, FrameCatchAbility.INSTANCE);
		}
	}

	private static void gainAbility(PlayerEntity player, int level, Ability ability)
	{
		IEntityStats props = EntityStatsCapability.get(player);
		IAbilityData abilityProps = AbilityDataCapability.get(player);

		if (props.getLevel() >= level && !abilityProps.hasUnlockedAbility(ability) )
		{
			player.sendMessage(new StringTextComponent("You unlocked a new move."), player.getUUID());
			abilityProps.addUnlockedAbility(ability);
		}
		if ((props.getLevel() < level && abilityProps.hasUnlockedAbility(ability)))
			abilityProps.removeUnlockedAbility(ability);
		
		PacketHandler.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityProps), player);
	}
}
