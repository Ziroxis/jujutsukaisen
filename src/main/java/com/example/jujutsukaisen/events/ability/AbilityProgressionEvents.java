package com.example.jujutsukaisen.events.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.tenshadow_technique.DivineDogsAbility;
import com.example.jujutsukaisen.abilities.tenshadow_technique.ShadowInventoryAbility;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityHelper;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.events.ExperienceEvent;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class AbilityProgressionEvents
{

	@SubscribeEvent
	public static void onDorikiGained(ExperienceEvent event)
	{
		IEntityStats props = EntityStatsCapability.get(event.getPlayer());
		if (props.isZenin())
		{
			gainAbility(event.getPlayer(), 5, DivineDogsAbility.INSTANCE);
			gainAbility(event.getPlayer(), 10, ShadowInventoryAbility.INSTANCE);
		}


	}

	private static void gainAbility(PlayerEntity player, int experience, Ability ability)
	{
		IEntityStats props = EntityStatsCapability.get(player);
		IAbilityData abilityProps = AbilityDataCapability.get(player);

		if (props.getExperience() >= experience && !abilityProps.hasUnlockedAbility(ability) )
			abilityProps.addUnlockedAbility(ability);
		if ((props.getExperience() < experience && abilityProps.hasUnlockedAbility(ability)))
			abilityProps.removeUnlockedAbility(ability);
		
		PacketHandler.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityProps), player);
	}
}
