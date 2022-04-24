package com.example.jujutsukaisen.events.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.ability.*;
import com.example.jujutsukaisen.api.ability.interfaces.IChangeDamageSourceAbility;
import com.example.jujutsukaisen.api.ability.interfaces.IFallDamageBlockingAbility;
import com.example.jujutsukaisen.api.ability.interfaces.ISniperAbility;
import com.example.jujutsukaisen.api.ability.sorts.*;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.Arrays;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class AbilitiesEvents
{
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			IAbilityData ablProps = AbilityDataCapability.get(player);

			/*//For debug reasons, literally a copy pasted but automated version of forge's CommandTps
			if(!player.level.isClientSide && player.tickCount % 20 == 0)
			{
				DecimalFormat TIME_FORMATTER = new DecimalFormat("########0.000");
				DimensionType dim = DimensionType.OVERWORLD;
				long[] times = player.getServer().getTickTime(dim);

		        if (times == null)
		            times = new long[] {0};

		        long sum = 0L;
		        for (long v : times)
		            sum += v;
		       	        
		        double worldTickTime = (sum / times.length) * 1.0E-6D;
		        double worldTPS = Math.min(1000.0 / worldTickTime, 20);
		        player.sendMessage(new TranslationTextComponent("commands.forge.tps.summary.named", dim.getId(), DimensionType.getKey(dim), TIME_FORMATTER.format(worldTickTime), TIME_FORMATTER.format(worldTPS)));
			}
			*/

			player.level.getProfiler().push("abilityCooldown");

			for (Ability ability : ablProps.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL))
			{
				if (ability == null)
					continue;

				try
				{
					if (ability instanceof PassiveAbility)
						((PassiveAbility) ablProps.getUnlockedAbility(ability)).tick(player);


				}
				catch (Exception e)
				{
					e.printStackTrace();
					ability.startCooldown(player);
				}
			}
			player.level.getProfiler().pop();

			player.level.getProfiler().push("abilityTick");
			for (Ability ability : ablProps.getEquippedAbilities(AbilityCategories.AbilityCategory.ALL))
			{
				if (ability == null)
					continue;

				try
				{
					if (ability instanceof ChargeableAbility && ability.isCharging())
						((ChargeableAbility) ablProps.getEquippedAbility(ability)).charging(player);

					if (ability instanceof ContinuousAbility && ability.isContinuous())
						((ContinuousAbility) ablProps.getEquippedAbility(ability)).tick(player);

					if(ability.isDisabled())
						ablProps.getEquippedAbility(ability).disableTick(player);
					
					if (ability.isOnCooldown())
						ablProps.getEquippedAbility(ability).cooldown(player);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					ability.startCooldown(player);
				}
			}
			player.level.getProfiler().pop();
		}
	}

	@SubscribeEvent
	public static void onPlayerDies(LivingDeathEvent event)
	{
		if (event.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			IAbilityData ablProps = AbilityDataCapability.get(player);
			
			for (Ability ability : ablProps.getEquippedAbilities(AbilityCategories.AbilityCategory.ALL))
			{
				if (ability == null)
					continue;

				try
				{
					if(ability.getState() == Ability.State.CONTINUOUS)
					{
						if (ability instanceof ContinuousAbility)
							((ContinuousAbility) ability).stopContinuity(player);
						
						if (ability instanceof RepeaterAbility)
							((RepeaterAbility) ability).setRepeaterCount(((RepeaterAbility) ability).getMaxRepeaterCount());					
					}
					else if (ability instanceof ChargeableAbility && ability.getState() == Ability.State.CHARGING)
					{
						((ChargeableAbility) ability).setChargeTime(((ChargeableAbility) ability).getMaxChargeTime() / 20);
						ability.startCooldown(player);
					}
					else
						ability.startCooldown(player);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					ability.startCooldown(player);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onEntityAttackEvent(LivingHurtEvent event)
	{
		if (event.getEntityLiving() != null && !event.getEntityLiving().level.isClientSide)
		{
			LivingEntity entity = event.getEntityLiving();
			IAbilityData ablProps = AbilityDataCapability.get(entity);
			
			for (Ability ability : ablProps.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL))
			{
				if (ability == null)
					continue;

				try
				{
					if (ability instanceof HurtPassiveAbility)
					{
						HurtPassiveAbility hurtAbility = (HurtPassiveAbility) ablProps.getUnlockedAbility(ability);
						boolean result = hurtAbility.hurt(entity, event.getSource().getEntity(), event.getAmount());
						event.setAmount(hurtAbility.getAmount());
						event.setCanceled(!result);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityAttackEvent(LivingAttackEvent event)
	{
		if (event.getEntityLiving() != null && !event.getEntityLiving().level.isClientSide)
		{
			LivingEntity entity = event.getEntityLiving();
			IAbilityData ablProps = AbilityDataCapability.get(entity);

			
			for (Ability ability : ablProps.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL))
			{
				if (ability == null)
					continue;

				try
				{
					if (ability instanceof DamagedPassiveAbility)
					{
						boolean result = ((DamagedPassiveAbility) ablProps.getUnlockedAbility(ability)).damage(entity, event.getSource());
						event.setCanceled(!result);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			for (Ability ability : ablProps.getEquippedAbilities())
			{
				if (ability == null)
					continue;

				try
				{
					if (ability instanceof DamagedContinuousAbility && ability.isContinuous())
					{
						boolean result = ((DamagedContinuousAbility) ablProps.getUnlockedAbility(ability)).damage(entity, event.getSource(), event.getAmount());
						event.setCanceled(!result);
					}
					
					if (ability instanceof IFallDamageBlockingAbility && event.getSource() == DamageSource.FALL)
					{
						boolean blockFallDamage = !((IFallDamageBlockingAbility) ability).hasFallDamage();
						if (blockFallDamage)
						{
							entity.fallDistance = 0;
							((IFallDamageBlockingAbility) ability).resetFallDamage(entity);
							event.setCanceled(true);
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			if (event.getSource().getDirectEntity() instanceof PlayerEntity && (event.getSource().getMsgId().equals("player") || event.getSource().getMsgId().equals("mob")) && event.getAmount() > 0)
			{
				PlayerEntity attacker = (PlayerEntity) event.getSource().getDirectEntity();
				ablProps = AbilityDataCapability.get(attacker);
				
				Arrays.stream(ablProps.getEquippedAbilities(AbilityCategories.AbilityCategory.ALL)).filter(Objects::nonNull).forEach(ability ->
				{
					try
					{
						if (ability instanceof IChangeDamageSourceAbility)
						{
							IChangeDamageSourceAbility abl = ((IChangeDamageSourceAbility) ability);
							if (abl.isSourceChangeEnabled())
							{
								//TODO make groups/partys

								//boolean sameGroup = FactionHelper.getSameGroupPredicate(attacker).test(entity);
								boolean sameGroup = false;
								if(sameGroup)
									return;

								double strength = attacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
								if(strength == 0)
									return;

								float damage = (float) (abl.damageToEntityWithSource(attacker, entity) * (event.getAmount() / strength));
								DamageSource source = abl.getSourceToUse(attacker);

								boolean damaged = true;
								// this hurt time check is to prevent you from spamming the attack to nulify hurt times
								// if you want to handle the hurt time in other way you can use damageToEntityWithSource
								// to modify it.
								if(entity.invulnerableTime == 0 || abl.cancelsOriginalDamage())
								{
									damaged = entity.hurt(source, damage);
									entity.hurtTime = entity.invulnerableTime = 0;
								}
								
								if (!damaged || abl.cancelsOriginalDamage())
									event.setCanceled(true);
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				});
				
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onAttackedByPlayer(AttackEntityEvent event)
	{
		if (!event.getPlayer().level.isClientSide && event.getTarget() instanceof LivingEntity)
		{
			PlayerEntity player = event.getPlayer();
			ItemStack heldItem = player.getMainHandItem();

			if (!heldItem.isEmpty())
				return;
			
			IEntityStats statProps = EntityStatsCapability.get(player);
			IAbilityData props = AbilityDataCapability.get(player);
			LivingEntity target = (LivingEntity) event.getTarget();


			
			for (Ability ability : props.getEquippedAbilities(AbilityCategories.AbilityCategory.ALL))
			{
				if (ability == null)
					continue;

				try
				{
					if (ability instanceof ContinuousPunchAbility && ability.isContinuous())
					{

						float damage = ((ContinuousPunchAbility) props.getEquippedAbility(ability)).hitEntity(player, target);

						if (damage <= 0)
						{
							event.setCanceled(true);
							return;
						}

						float strength = (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
						float finalDamage = (damage + strength) * (float)statProps.getDamageMultiplier();
						System.out.println("Final damage is +" + finalDamage);
						target.hurt(ModDamageSource.causeAbilityDamage(player, ability), finalDamage);
					}
					if (ability instanceof PunchAbility && ability.isContinuous())
					{

						float damage = ((PunchAbility) props.getEquippedAbility(ability)).hitEntity(player, target);

						if (damage <= 0)
						{
							event.setCanceled(true);
							return;
						}

						float strength = (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
						float finalDamage = (damage + strength) * (float)statProps.getDamageMultiplier();
						System.out.println("Final damage is +" + finalDamage);
						target.hurt(ModDamageSource.causeAbilityDamage(player, ability), finalDamage);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					ability.startCooldown(player);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayarLogsOut(PlayerLoggedOutEvent event)
	{
		if (event.getPlayer().level.isClientSide)
			return;

		PlayerEntity player = event.getPlayer();

		IAbilityData props = AbilityDataCapability.get(player);

		for (Ability ability : props.getEquippedAbilities(AbilityCategories.AbilityCategory.ALL))
		{
			if (ability == null)
				continue;

			try
			{
				if (ability instanceof ChargeableAbility && ability.isCharging())
					((ChargeableAbility) props.getEquippedAbility(ability)).stopCharging(player);

				if (ability instanceof ContinuousAbility && ability.isContinuous())
					((ContinuousAbility) props.getEquippedAbility(ability)).stopContinuity(player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				ability.startCooldown(player);
			}
		}
	}

	@SubscribeEvent
	public static void onPotionEvent(PotionApplicableEvent event)
	{
		if (!(event.getEntityLiving() instanceof PlayerEntity))
			return;

		PlayerEntity player = (PlayerEntity) event.getEntityLiving();

		IAbilityData props = AbilityDataCapability.get(player);

		for (Ability ability : props.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL))
		{
			if (ability == null)
				continue;

			try
			{
				if (ability instanceof PotionPassiveAbility)
				{
					boolean applied = ((PotionPassiveAbility) props.getUnlockedAbility(ability)).check(player, event.getPotionEffect());
					if (applied)
						event.setResult(Result.ALLOW);
					else
						event.setResult(Result.DENY);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public static void onEntityShootProjectile(ArrowLooseEvent event)
	{
		if (event.getPlayer() != null)
		{
			IAbilityData props = AbilityDataCapability.get(event.getPlayer());
			
			for(Ability abl : props.getEquippedAbilities())
			{
				if(props.hasEquippedAbility(abl) && props.getEquippedAbility(abl).isContinuous())
				{			
					((ISniperAbility)props.getEquippedAbility(abl)).shoot(event.getPlayer());
					props.getEquippedAbility(abl).use(event.getPlayer());
					event.setCanceled(true);
				}
			}
		}
	}
}
