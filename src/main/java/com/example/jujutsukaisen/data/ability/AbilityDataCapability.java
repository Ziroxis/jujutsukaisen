package com.example.jujutsukaisen.data.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.*;
import com.example.jujutsukaisen.api.ability.sorts.ChargeableAbility;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.registry.GameRegistry;


import java.util.List;
import java.util.stream.Collectors;

public class AbilityDataCapability
{
	@CapabilityInject(IAbilityData.class)
	public static final Capability<IAbilityData> INSTANCE = null;

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IAbilityData.class, new Capability.IStorage<IAbilityData>()
		{
			@Override
			public INBT writeNBT(Capability<IAbilityData> capability, IAbilityData instance, Direction side)
			{
				CompoundNBT props = new CompoundNBT();

				try
				{
					ListNBT unlockedAbilities = new ListNBT();
					for (int i = 0; i < instance.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL).size(); i++)
					{
						Ability ability = instance.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL).get(i);
						String name = Beapi.getResourceName(ability.getName());
						CompoundNBT nbtAbility = new CompoundNBT();
						nbtAbility.putString("name", name);
						nbtAbility.putString("displayName", Beapi.isNullOrEmpty(ability.getDisplayName()) ? "" : ability.getDisplayName());
						nbtAbility.putInt("pool", ability.getPoolId());
						nbtAbility.putString("unlock", ability.getUnlockType().name());
						if(ability instanceof PassiveAbility)
						{
							nbtAbility.putBoolean("isPaused", ((PassiveAbility)ability).isPaused());
						}
						unlockedAbilities.add(nbtAbility);
					}
					props.put("unlocked_abilities", unlockedAbilities);

					ListNBT equippedAbilities = new ListNBT();
					for (int i = 0; i < instance.getEquippedAbilities().length; i++)
					{
						Ability ability = instance.getEquippedAbilities()[i];
						if(ability != null)
						{
							String name = Beapi.getResourceName(ability.getName());
							CompoundNBT nbtAbility = new CompoundNBT();
							nbtAbility.putString("name", name);
							nbtAbility.putString("displayName", ability.getDisplayName() == null ? "" : ability.getDisplayName());
							nbtAbility.putInt("pos", i);
							nbtAbility.putString("state", ability.getState().toString());
							nbtAbility.putInt("pool", ability.getPoolId());
							nbtAbility.putDouble("cooldown", ability.getCooldown());
							nbtAbility.putDouble("maxCooldown", ability.getMaxCooldown());
							if(ability instanceof ContinuousAbility)
							{
								nbtAbility.putDouble("continueTimer", ((ContinuousAbility)ability).getContinueTime());
								nbtAbility.putDouble("threshold", ((ContinuousAbility)ability).getThreshold());
							}
							if(ability instanceof ChargeableAbility)
								nbtAbility.putDouble("chargeTimer", ((ChargeableAbility)ability).getChargeTime());
							equippedAbilities.add(nbtAbility);
						}
						else
						{
							CompoundNBT nbtAbility = new CompoundNBT();
							nbtAbility.putInt("pos", i);
							equippedAbilities.add(nbtAbility);
						}
					}
					props.put("equipped_abilities", equippedAbilities);
					
					props.putInt("combat_bar_set", instance.getCombatBarSet());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				return props;
			}

			@Override
			public void readNBT(Capability<IAbilityData> capability, IAbilityData instance, Direction side, INBT nbt)
			{
				CompoundNBT props = (CompoundNBT) nbt;

				try
				{
					instance.clearEquippedAbilities(AbilityCategories.AbilityCategory.ALL);
					instance.clearUnlockedAbilities(AbilityCategories.AbilityCategory.ALL);

					ListNBT unlockedAbilities = props.getList("unlocked_abilities", Constants.NBT.TAG_COMPOUND);
					for (int i = 0; i < unlockedAbilities.size(); i++)
					{
						CompoundNBT nbtAbility = unlockedAbilities.getCompound(i);

						try
						{
							Ability ability = GameRegistry.findRegistry(Ability.class).getValue(new ResourceLocation(Main.MODID, nbtAbility.getString("name"))).create();
							if(ability == null)
								continue;
							int poolId = nbtAbility.getInt("pool");
							ability.setInPool(poolId);
							AbilityUnlock unlockType = AbilityUnlock.valueOf(nbtAbility.getString("unlock"));
							ability.setUnlockType(unlockType);
							String displayName = nbtAbility.getString("displayName");
							ability.setDisplayName(displayName);
							if(ability instanceof PassiveAbility)
							{
								((PassiveAbility)ability).setPause(nbtAbility.getBoolean("isPaused"));
							}
							instance.addUnlockedAbility(ability);
						}
						catch(Exception e)
						{
							System.out.println(nbtAbility.getString("name") + " not registering");
							e.printStackTrace();
						}
					}

					ListNBT equippedAbilities = props.getList("equipped_abilities", Constants.NBT.TAG_COMPOUND);
					List<Ability> activeAbilitiesUnlocked = instance.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL).parallelStream().filter(ability -> !(ability instanceof PassiveAbility)).collect(Collectors.toList());
					for (int i = 0; i < equippedAbilities.size(); i++)
					{
						CompoundNBT nbtAbility = equippedAbilities.getCompound(i);
						Ability ability = GameRegistry.findRegistry(Ability.class).getValue(new ResourceLocation(Main.MODID, nbtAbility.getString("name")));
						if (ability != null)
						{
							for(Ability abl : activeAbilitiesUnlocked)
							{
								if(abl.equals(ability))
								{
									Ability.State state = Ability.State.valueOf(nbtAbility.getString("state"));
									int cooldown = (int) (nbtAbility.getDouble("cooldown") / 20);
									int maxCooldown = (int) (nbtAbility.getDouble("maxCooldown") / 20);
									int pos = nbtAbility.getInt("pos");
									int poolId = nbtAbility.getInt("pool");
									String displayName = nbtAbility.getString("displayName");
									if (state == null)
										state = Ability.State.STANDBY;
									abl.setState(state);
									abl.setMaxCooldown(maxCooldown);
									abl.setCooldown(cooldown);
									abl.setInPool(poolId);
									abl.setDisplayName(displayName);
									if(abl instanceof ContinuousAbility)
									{
										int continueTime = (int) (nbtAbility.getDouble("continueTime") / 20);
										((ContinuousAbility)abl).setContinueTime(continueTime);
										int threshold = (int) (nbtAbility.getDouble("threshold") / 20);
										((ContinuousAbility)abl).setThreshold(threshold);
									}
									if(abl instanceof ChargeableAbility)
									{
										int chargeTime = (int) (nbtAbility.getDouble("chargeTime") / 20);
										((ChargeableAbility)abl).setChargeTime(chargeTime);
									}
									
									instance.setEquippedAbility(pos, abl);
								}
							}
						}
						else if(ability == null)
						{
							int pos = nbtAbility.getInt("pos");
							instance.setEquippedAbility(pos, null);
						}
					}
					
					instance.setCombatBarSet(props.getInt("combat_bar_set"));
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

		}, AbilityDataBase::new);
	}

	public static IAbilityData get(final LivingEntity entity)
	{
		return entity.getCapability(INSTANCE, null).orElse(new AbilityDataBase());
	}

	public static LazyOptional<IAbilityData> getLazy(final LivingEntity entity)
	{
		return entity.getCapability(INSTANCE, null);
	}
}
