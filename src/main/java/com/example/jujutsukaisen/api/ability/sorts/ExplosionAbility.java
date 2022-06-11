package com.example.jujutsukaisen.api.ability.sorts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.example.jujutsukaisen.data.world.ExtendedWorldData;
import com.example.jujutsukaisen.particles.ParticleEffect;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

public class ExplosionAbility extends Explosion
{
	private World world;
	private Entity exploder;
	private double explosionX;
	private double explosionY;
	private double explosionZ;
	private float explosionSize;
	private ParticleEffect particles;
	private DamageSource damageSource;
	
	private final List<BlockPos> affectedBlockPositions = Lists.newArrayList();
	private final Map<PlayerEntity, Vector3d> playerKnockbackMap = Maps.newHashMap();
	public List<FallingBlockEntity> removedBlocks = Lists.newArrayList();
	private final Random random = new Random();

	private boolean canStartFireAfterExplosion = false;
	private boolean canDestroyBlocks = true;
	private boolean canDropBlocksAfterExplosion = false;
	private boolean canDamageEntities = true;
	private boolean canDamageOwner = false;
	private boolean canAlwaysDamage = true;
	private boolean canProduceExplosionSound = true;
	private boolean protectOwnerFromFalling = false;
	private boolean canCauseKnockback = true;
	private boolean addRemovedBlocksToList = false;
	private float staticDamage = 0;
	private float staticBlockResistance = 0;
	private int heightDifference = 0;
	private int explodedBlocksLimit;
	private int size = 52;
	private int explodedBlocks;
	public ArrayList<Entity> immuneEntities = new ArrayList();
	public ArrayList<Entity> damagedEntities = new ArrayList();

	public ExplosionAbility(Entity entity, World world, double posX, double posY, double posZ, float power)
	{
		super(world, entity, (DamageSource)null, (ExplosionContext)null, posX, posY, posZ, power, false, Mode.DESTROY);
		this.world = world;
		this.exploder = entity;
		this.explosionSize = power;
		this.explosionX = posX;
		this.explosionY = posY;
		this.explosionZ = posZ;
		
		this.damageSource = DamageSource.explosion(this);
	}

	public void setExplosionPos(double posX, double posY, double posZ)
	{
		this.explosionX = posX;
		this.explosionY = posY;
		this.explosionZ = posZ;
	}
	
	public void setExplosionSize(float explosionSize)
	{
		this.explosionSize = explosionSize;
	}
	
	public void setExplodedBlocksLimit(int limit)
	{
		this.explodedBlocksLimit = limit;
	}
	
	public double getStaticDamage()
	{
		return this.staticDamage;
	}

	public void setStaticDamage(float damage)
	{
		this.staticDamage = damage;
	}

	public double getStaticBlockResistance()
	{
		return this.staticBlockResistance;
	}

	public void setStaticBlockResistance(float damage)
	{
		this.staticBlockResistance = damage;
	}

	public void setHeightDifference(int heightDifference)
	{
		this.heightDifference = heightDifference;
	}

	public void setDamageOwner(boolean damageOwner)
	{
		this.canDamageOwner = damageOwner;
	}

	public void setDamageEntities(boolean damageEntities)
	{
		this.canDamageEntities = damageEntities;
	}

	public void setDropBlocksAfterExplosion(boolean canDrop)
	{
		this.canDropBlocksAfterExplosion = canDrop;
	}

	public void setFireAfterExplosion(boolean hasFire)
	{
		this.canStartFireAfterExplosion = hasFire;
	}

	public void setDestroyBlocks(boolean canDestroyBlocks)
	{
		this.canDestroyBlocks = canDestroyBlocks;
	}

	public void setSmokeParticles(ParticleEffect particle)
	{
		this.particles = particle;
	}

	public boolean getAlwaysDamage()
	{
		return this.canAlwaysDamage;
	}

	public void setAlwaysDamage(boolean flag)
	{
		this.canAlwaysDamage = flag;
	}

	public void addRemovedBlocksToList()
	{
		this.addRemovedBlocksToList = true;
	}
	
	public void setProtectOwnerFromFalling(boolean flag)
	{
		this.protectOwnerFromFalling = flag;
	}

	public boolean hasSmokeParticles()
	{
		return this.particles != null;
	}

	public void setExplosionSound(boolean hasSound)
	{
		this.canProduceExplosionSound = hasSound;
	}

	private void resetDamage(LivingEntity entity)
	{
		entity.hurtTime = entity.invulnerableTime = 0;
	}

	public void disableExplosionKnockback()
	{
		this.canCauseKnockback = false;
	}

	@Override
	public DamageSource getDamageSource()
	{
		return this.damageSource;
	}

	public void setDamageSource(DamageSource damageSourceIn)
	{
		this.damageSource = damageSourceIn;
	}
	
	public void doExplosion()
	{
		boolean flag = this.exploder != null && this.exploder instanceof PlayerEntity && ExtendedWorldData.get(world).isInsideRestrictedArea((int) this.explosionX, (int) this.explosionY, (int) this.explosionZ);
		if (flag || (this.heightDifference > 0 && this.exploder != null && this.exploder.getY() - this.heightDifference > this.explosionY))
			return;
				
		Set<BlockPos> set = Sets.newHashSet();

		// this code should make the loop smaller for, well smaller explosions.
		if(this.size + 4 > this.explosionSize)
			this.size = Math.max((int) (this.explosionSize + 4), 16);

		for (int j = 0; j < this.size; ++j)
		{
			for (int k = 0; k < this.size; ++k)
			{
				for (int l = 0; l < this.size; ++l)
				{
					if (j == 0 || j == (this.size - 1) || k == 0 || k == (this.size - 1) || l == 0 || l == (this.size - 1))
					{
						double d0 = j / (float)(this.size - 1) * 2.0F - 1.0F;
						double d1 = k / (float)(this.size - 1) * 2.0F - 1.0F;
						double d2 = l / (float)(this.size - 1) * 2.0F - 1.0F;
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 = d0 / d3;
						d1 = d1 / d3;
						d2 = d2 / d3;
						float f = this.explosionSize * (0.7F + this.world.random.nextFloat() * 0.6F);
						double eX = this.explosionX;
						double eY = this.explosionY;
						double eZ = this.explosionZ;

						for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F)
						{
							BlockPos blockpos = new BlockPos(eX, eY, eZ);
							BlockState blockState = this.world.getBlockState(blockpos);
							FluidState ifluidstate = this.world.getFluidState(blockpos);
							if (!blockState.isAir() || !ifluidstate.isEmpty())
							{
								float f2 = Math.max(blockState.getExplosionResistance(this.world, blockpos, this), ifluidstate.getExplosionResistance(this.world, blockpos, this));
								if (this.exploder != null)
									f2 = this.exploder.getBlockExplosionResistance(this, this.world, blockpos, blockState, ifluidstate, f2);
								
								f -= this.getStaticBlockResistance() > 0 ? this.getStaticBlockResistance() : (f2 + 0.3F) * 0.3F;
							}

							if (f > 0.0F && (this.exploder == null || this.exploder.shouldBlockExplode(this, this.world, blockpos, blockState, f)))
							{
								set.add(blockpos);
							}

							eX += d0 * 0.3F;
							eY += d1 * 0.3F;
							eZ += d2 * 0.3F;
						}
					}
				}
			}
		}

		this.affectedBlockPositions.addAll(set);
		float f3 = this.explosionSize * 2.0F;
		int k1 = MathHelper.floor(this.explosionX - f3 - 1.0D);
		int l1 = MathHelper.floor(this.explosionX + f3 + 1.0D);
		int i2 = MathHelper.floor(this.explosionY - f3 - 1.0D);
		int i1 = MathHelper.floor(this.explosionY + f3 + 1.0D);
		int j2 = MathHelper.floor(this.explosionZ - f3 - 1.0D);
		int j1 = MathHelper.floor(this.explosionZ + f3 + 1.0D);
		List<Entity> list;
		if(this.canDamageOwner)
			list = this.world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
		else
			list = this.world.getEntities(this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));

		if(this.immuneEntities.size() > 0)
			list.removeAll(this.immuneEntities);

		ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
		Vector3d v3d = new Vector3d(this.explosionX, this.explosionY, this.explosionZ);

		if(this.canDamageEntities)
		{
			for (int k2 = 0; k2 < list.size(); ++k2)
			{
				Entity entity = list.get(k2);
				boolean isInProtection = ExtendedWorldData.get(world).isInsideRestrictedArea((int) entity.getX(), (int) entity.getY(), (int) entity.getZ());

				if (!entity.ignoreExplosion() && !isInProtection)
				{
					double distance = entity.distanceToSqr(this.explosionX, this.explosionY, this.explosionZ) / f3;
					if (distance <= 1.0D)
					{
						double xDistance = entity.getX() - this.explosionX;
						double yDistance = entity.getY() + entity.getEyeHeight() - this.explosionY;
						double zDistance = entity.getZ() - this.explosionZ;
						double squareDistance = MathHelper.sqrt(xDistance * xDistance + yDistance * yDistance + zDistance * zDistance);
						if (squareDistance != 0.0D)
						{
							xDistance = xDistance / squareDistance;
							yDistance = yDistance / squareDistance;
							zDistance = zDistance / squareDistance;
							double blockDensity = this.getStaticBlockResistance() > 0 ? 0 : Explosion.getSeenPercent(v3d, entity);
							double power = (1.0D - distance) * blockDensity;
							
							
							if (entity instanceof LivingEntity && this.getAlwaysDamage())
								this.resetDamage((LivingEntity) entity);

							if (this.staticDamage > 0)
							{
								entity.hurt(this.getDamageSource(), this.staticDamage);
								this.damagedEntities.add(entity);
							}
							else
							{
								float damage = ((float) ((power * power + power) / 2.0D * 7.0D * f3 + 1.0D));
								entity.hurt(this.getDamageSource(), damage);
								this.damagedEntities.add(entity);
							}
							
							double blastDamageReduction = power;

							if (entity instanceof LivingEntity)
								blastDamageReduction = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, power);
							
							if(this.canCauseKnockback)
							{
								entity.setDeltaMovement(entity.getDeltaMovement().add(xDistance * blastDamageReduction, yDistance * blastDamageReduction, zDistance * blastDamageReduction));
								if (entity instanceof PlayerEntity)
								{
									PlayerEntity playerEntity = (PlayerEntity) entity;
									if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.abilities.flying))
									{
										this.playerKnockbackMap.put(playerEntity, new Vector3d(xDistance * power, yDistance * power, zDistance * power));
									}
								}
							}
						}
					}
				}
			}
		}

		if (this.canProduceExplosionSound)
			this.world.playSound((PlayerEntity) null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);

		if (this.hasSmokeParticles())
			this.particles.spawn(this.world, this.explosionX, this.explosionY, this.explosionZ, 0, 0, 0);
		//	ModNetwork.sendToAllAround(new SParticlesPacket(this.smokeParticles, this.explosionX, this.explosionY, this.explosionZ), (LivingEntity) this.exploder);

		if (this.canDestroyBlocks && false && (this.explodedBlocksLimit <= 0 || this.explodedBlocks < this.explodedBlocksLimit))
		{
			ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
			Collections.shuffle(this.affectedBlockPositions, this.world.random);

			for (BlockPos blockpos : this.affectedBlockPositions)
			{
				BlockState blockstate = this.world.getBlockState(blockpos);

				if(blockstate.getMaterial() == Material.WATER && false)
					continue;

				//boolean blockIsRestricted = RestrictedBlockProtectionRule.INSTANCE.isBanned(blockstate.getBlock());
				//boolean hardBlockRestriction = this.addRemovedBlocksToList && HardThrowableProtectionRule.INSTANCE.isBanned(blockstate.getBlock());
				boolean inProtectedAreaFlag = ExtendedWorldData.get(this.world).isInsideRestrictedArea(blockpos.getX(), blockpos.getY(), blockpos.getZ());

				boolean fallingProtection = true;
				if(this.protectOwnerFromFalling && this.exploder != null)
					fallingProtection = Math.sqrt(this.exploder.distanceToSqr(blockpos.getX(), this.exploder.getY(), blockpos.getZ())) > 1.5F;

				if (!blockstate.isAir() && fallingProtection && !inProtectedAreaFlag)
				{
					if (this.world instanceof ServerWorld && blockstate.canDropFromExplosion(this.world, blockpos, this))
					{
						TileEntity tileentity = blockstate.hasTileEntity() ? this.world.getBlockEntity(blockpos) : null;
						LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.world)).withRandom(this.world.random).withParameter(LootParameters.ORIGIN, Vector3d.atCenterOf(blockpos)).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withOptionalParameter(LootParameters.BLOCK_ENTITY, tileentity).withOptionalParameter(LootParameters.THIS_ENTITY, this.exploder);
						lootcontext$builder.withParameter(LootParameters.EXPLOSION_RADIUS, this.explosionSize);

						if(this.canDropBlocksAfterExplosion)
							blockstate.getDrops(lootcontext$builder).forEach((p_229977_2_) ->
									func_229976_a_(objectarraylist, p_229977_2_, blockpos));

						if (this.addRemovedBlocksToList)
							this.removedBlocks.add(new FallingBlockEntity(this.world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), blockstate));
					}

					blockstate.onBlockExploded(this.world, blockpos, this);
					this.explodedBlocks++;
				}
			}
		}

		if (this.canStartFireAfterExplosion && false)
		{
			Iterator<BlockPos> positions = this.affectedBlockPositions.iterator();
			while(positions.hasNext())
			{
				BlockPos blockpos1 = positions.next();
				boolean inProtectedAreaFlag = ExtendedWorldData.get(this.world).isInsideRestrictedArea(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());

				if (this.world.getBlockState(blockpos1).isAir() && this.world.getBlockState(blockpos1.below()).isSolidRender(this.world, blockpos1.below()) && this.random.nextInt(5) == 0 && !inProtectedAreaFlag)
				{
					this.world.setBlockAndUpdate(blockpos1, AbstractFireBlock.getState(this.world, blockpos1));
					positions.remove();
				}
			}
		}		
	}

	private static void func_229976_a_(ObjectArrayList<Pair<ItemStack, BlockPos>> drops, ItemStack itemStack, BlockPos pos)
	{
		int i = drops.size();

		for (int j = 0; j < i; ++j)
		{
			Pair<ItemStack, BlockPos> pair = drops.get(j);
			ItemStack itemstack = pair.getFirst();
			if (ItemEntity.areMergable(itemstack, itemStack))
			{
				ItemStack itemstack1 = ItemEntity.merge(itemstack, itemStack, 16);
				drops.set(j, Pair.of(itemstack1, pair.getSecond()));
				if (itemStack.isEmpty())
				{
					return;
				}
			}
		}

		drops.add(Pair.of(itemStack, pos));
	}

	@Override
	public Map<PlayerEntity, Vector3d> getHitPlayers()
	{
		return this.playerKnockbackMap;
	}

}