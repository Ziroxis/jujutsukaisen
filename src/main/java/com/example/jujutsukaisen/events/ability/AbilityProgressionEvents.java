package com.example.jujutsukaisen.events.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodEdgeAbility;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodMeteoriteAbility;
import com.example.jujutsukaisen.abilities.blood_manipulation.FlowingRedScaleAbility;
import com.example.jujutsukaisen.abilities.blood_manipulation.PiercingBloodAbility;
import com.example.jujutsukaisen.abilities.cursed_speech.*;
import com.example.jujutsukaisen.abilities.disaster_flames.EmberInsectsAbility;
import com.example.jujutsukaisen.abilities.disaster_flames.FlameArrowAbility;
import com.example.jujutsukaisen.abilities.disaster_flames.FlameBallAbility;
import com.example.jujutsukaisen.abilities.disaster_flames.PurpleFlamesAbility;
import com.example.jujutsukaisen.abilities.disaster_plants.*;
import com.example.jujutsukaisen.abilities.disaster_tides.*;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameBreakAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameCatchAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameSpeedAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameTeleportationAbility;
import com.example.jujutsukaisen.abilities.straw_doll.GiantNailAbility;
import com.example.jujutsukaisen.abilities.straw_doll.HairpinAbility;
import com.example.jujutsukaisen.abilities.straw_doll.ResonanceAbility;
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
		if (props.getTechnique().equals(ModValues.CURSED_SPEECH))
		{
			gainAbility(event.getPlayer(), 5, SleepAbility.INSTANCE);
			gainAbility(event.getPlayer(), 10, GetTwistedAbility.INSTANCE);
			gainAbility(event.getPlayer(), 15, FallDownUnderAbility.INSTANCE);
			gainAbility(event.getPlayer(), 25, BlastAwayAbility.INSTANCE);
			gainAbility(event.getPlayer(), 30, ExplodeAbility.INSTANCE);
		}
		if (props.getTechnique().equals(ModValues.DISASTER_TIDES))
		{
			gainAbility(event.getPlayer(), 5, WaterChargeAbility.INSTANCE);
			gainAbility(event.getPlayer(), 8, WaterFlowAbility.INSTANCE);
			gainAbility(event.getPlayer(), 10, CursedFishAbility.INSTANCE);
			gainAbility(event.getPlayer(), 15, WaterShieldAbility.INSTANCE);
			gainAbility(event.getPlayer(), 25, CursedSharkAbility.INSTANCE);
			gainAbility(event.getPlayer(), 30, DeathSwarmAbility.INSTANCE);
		}
		if (props.getTechnique().equals(ModValues.DISASTER_FLAMES))
		{
			gainAbility(event.getPlayer(), 5, EmberInsectsAbility.INSTANCE);
			gainAbility(event.getPlayer(), 10, FlameBallAbility.INSTANCE);
			gainAbility(event.getPlayer(), 15, PurpleFlamesAbility.INSTANCE);
			gainAbility(event.getPlayer(), 20, FlameArrowAbility.INSTANCE);
		}
		if (props.getTechnique().equals(ModValues.DISASTER_PLANTS))
		{
			gainAbility(event.getPlayer(), 5, CursedBudsAbility.INSTANCE);
			gainAbility(event.getPlayer(), 8, WoodenBallAbility.INSTANCE);
			gainAbility(event.getPlayer(), 10, EnergyAbsorptionAbility.INSTANCE);
			gainAbility(event.getPlayer(), 15, FlowerFieldAbility.INSTANCE);
			gainAbility(event.getPlayer(), 20, RootEncasementAbility.INSTANCE);
			gainAbility(event.getPlayer(), 25, WoodArmorAbility.INSTANCE);
		}
		if (props.getTechnique().equals(ModValues.STRAW_DOLL))
		{
			gainAbility(event.getPlayer(), 5, GiantNailAbility.INSTANCE);
			gainAbility(event.getPlayer(), 10, ResonanceAbility.INSTANCE);
			gainAbility(event.getPlayer(), 20, HairpinAbility.INSTANCE);
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
