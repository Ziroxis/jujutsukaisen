package com.example.jujutsukaisen.entities.spells;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.world.ExtendedWorldData;
import com.example.jujutsukaisen.events.projectile.ProjectileBlockEvent;
import com.example.jujutsukaisen.events.projectile.ProjectileHitEvent;
import com.example.jujutsukaisen.events.projectile.ProjectileShootEvent;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbilityProjectileEntity extends ThrowableEntity
{
	private int life = 64;
	private int maxLife = 64;
	private int knockbackStrength = 0;
	private double collisionSize = 1;
	private float damage = 0.1F;
	private float gravity = 0.0001F;
	private boolean canPassThroughBlocks = false;
	private boolean canPassThroughEntities = false;
	private boolean canGetStuckInGround = false;
	protected boolean stuckInGround = false;
	private boolean changeHurtTime = false;
	private boolean armorPiercing = false;
	private boolean canHurtThrower = false;
	// For reference default hurt time is 20
	private int hurtTime = 10;
	boolean entityDamaged = false;
	boolean applyOnlyOnce = true;
	private List<Integer> targets = new ArrayList<>();
	private int targetResetTime = 20;
	private static final DataParameter<Integer> OWNER = EntityDataManager.defineId(AbilityProjectileEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> IS_PHYSICAL = EntityDataManager.defineId(AbilityProjectileEntity.class, DataSerializers.BOOLEAN);

	// Setting the defaults so that no crash occurs and so they will be null safe.
	public IOnEntityImpact onEntityImpactEvent = (hitEntity) ->
	{
		if(!this.targets.contains(hitEntity.getId()))
			this.onBlockImpactEvent.onImpact(hitEntity.blockPosition());
	};
	public IOnBlockImpact onBlockImpactEvent = (hit) -> {};
	public IOnTick onTickEvent = () -> {};
	public IWithEffects withEffects = () -> { return new EffectInstance[0]; };
	
	public DamageSource source;
	public DamageSource bypassingSource;

	private static final Block[] NON_SOLID_BLOCKS = new Block[] { Blocks.GRASS, Blocks.TALL_GRASS, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.VINE, Blocks.REDSTONE_WIRE, Blocks.DEAD_BUSH, Blocks.ROSE_BUSH };

	public AbilityProjectileEntity(EntityType type, World world)
	{
		super(type, world);
	}

	public AbilityProjectileEntity(EntityType type, World world, double x, double y, double z)
	{
		super(type, x, y, z, world);
	}

	public AbilityProjectileEntity(EntityType type, World world, LivingEntity thrower)
	{
		super(type, thrower, world);
		this.maxLife = this.life;
		this.damage = 0.1f;
		this.setThrower(thrower);
		
		this.source = new IndirectEntityDamageSource("ability_projectile", this, thrower).setProjectile();
		this.bypassingSource = new IndirectEntityDamageSource("ability_projectile", this, thrower).setProjectile().bypassArmor();		
	}

	@Override
	public void tick()
	{
		super.tick();
		
		if (!this.level.isClientSide)
		{
			if (this.life <= 0)
			{
				this.life = this.maxLife;
				//this.onBlockImpactEvent.onImpact(this.blockPosition());
				this.remove();
				return;
			}
			else
				this.life--;
			
			if(ExtendedWorldData.get(this.level).isInsideRestrictedArea((int)this.getX(), (int)this.getY(), (int)this.getZ()))
			{
				this.remove();
				return;
			}
		}

		Vector3d vec31 = new Vector3d(this.getX(), this.getY(), this.getZ());
		Vector3d vec3 = new Vector3d(this.getX() + this.getDeltaMovement().x, this.getY() + this.getDeltaMovement().y, this.getZ() + this.getDeltaMovement().z);
		RayTraceResult hit = this.level.clip(new RayTraceContext(vec3, vec31, BlockMode.OUTLINE, FluidMode.ANY, this));

		double sizeX = this.collisionSize;
		double sizeY = this.collisionSize;
		double sizeZ = this.collisionSize;

		AxisAlignedBB aabb = new AxisAlignedBB(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY(), this.getZ()).expandTowards(sizeX, sizeY, sizeZ);
		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb);

		Entity entity = null;

		for (Entity target : list)
			if (target.canBeCollidedWith() && (target != this.getOwner() || this.tickCount >= 5)) entity = target;
		
		if (entity == this.getOwner())
			return;

		if (entity != null)
			hit = new EntityRayTraceResult(entity);

		if (hit.getType() == RayTraceResult.Type.ENTITY)
			this.onHit(hit);

		if(this.tickCount % this.getTargetResetTime() == 0)
			this.clearTargets();

		this.onTickEvent.onTick();

	}

	@Override
	public void shootFromRotation(Entity thrower, float yRotIn, float xRotIn, float pitchOffset, float velocity, float inaccuracy)
	{
		ProjectileShootEvent event = new ProjectileShootEvent(this, velocity, inaccuracy);
		if (MinecraftForge.EVENT_BUS.post(event))
			return;
		this.clearTargets();
		super.shootFromRotation(thrower, yRotIn, xRotIn, pitchOffset, velocity, inaccuracy);
	}
	
	// XXX(wynd) - Due to legacy reasons this method will remain as 'shoot' despite being more correctly to name it 'shootFromRotation'. Over time the name change should be a priority to avoid further confusions
	@Deprecated
	public void shoot(Entity thrower, float yRotIn, float xRotIn, float pitchOffset, float velocity, float inaccuracy)
	{
		this.shootFromRotation(thrower, yRotIn, xRotIn, pitchOffset, velocity, inaccuracy);
	}

	@Override
	public boolean updateInWaterStateAndDoFluidPushing()
	{
		if (this.isInWater())
			this.doWaterSplashEffect();
		return false;
	}

	@Override
	protected void onHit(RayTraceResult hit)
	{
		if (!this.level.isClientSide)
		{
			if (hit.getType() == RayTraceResult.Type.ENTITY)
			{
				EntityRayTraceResult entityHit = (EntityRayTraceResult) hit;

				if (entityHit.getEntity() instanceof LivingEntity && this.getOwner() != null && this.getOwner() instanceof LivingEntity)
				{
					LivingEntity hitEntity = (LivingEntity) entityHit.getEntity();
					IEntityStats statProps = EntityStatsCapability.get((LivingEntity) this.getOwner());
					
					if (hitEntity == this.getOwner() && !this.canHurtThrower)
						return;

					ProjectileHitEvent event = new ProjectileHitEvent(this, hit);
					if (MinecraftForge.EVENT_BUS.post(event))
						return;

					if (!this.entityDamaged && !targets.contains(hitEntity.getEntity().getId()) && hitEntity.getEntity().isAlive())
					{
						if(this.source == null)
							this.source = new IndirectEntityDamageSource("ability_projectile", this, this.getOwner()).setProjectile();
												
						if(this.bypassingSource == null)
							this.bypassingSource = new IndirectEntityDamageSource("ability_projectile", this, this.getOwner()).setProjectile().bypassArmor();
						
						if (!this.canBlockDamageSource(this.source, hitEntity)) {
							if (this.isPhysical() && this.getOwner() != null && this.applyOnlyOnce)
							{

							}

							this.damage *= statProps.getDamageMultiplier();
							if (this.armorPiercing)
							{
								float reduction = this.getArmorDamage();
								this.entityDamaged = hitEntity.hurt(this.source, this.damage - reduction);
								hitEntity.hurtTime = hitEntity.invulnerableTime = 0;
								hitEntity.hurt(this.bypassingSource, reduction);
							}
							else
							{
								this.entityDamaged = hitEntity.hurt(this.source, this.damage);
							}

						}
						else
						{
							ItemStack stack = hitEntity.getItemInHand(hitEntity.getUsedItemHand());
							if (stack.getItem() instanceof ShieldItem)
							{
								stack.hurtAndBreak((int) (this.getDamage() + 17), hitEntity, (entity) ->
								{
									entity.broadcastBreakEvent(hitEntity.getUsedItemHand());
								});

							}
						}
					}

					if (this.entityDamaged)
					{
						this.triggerEffects(hitEntity);

						this.onEntityImpactEvent.onImpact(hitEntity);

						if (this.knockbackStrength > 0)
						{
							Vector3d v3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
							if (v3d.lengthSqr() > 0.0D)
							{
								hitEntity.push(v3d.x, 0.1D, v3d.z);
							}
						}

						if (!this.canPassThroughEntities)
							this.remove();
						else
						{
							this.targets.add(entityHit.getEntity().getId());
							this.entityDamaged = false;
						}
					}

					if (this.changeHurtTime)
						hitEntity.invulnerableTime = this.hurtTime;
				}
				else if (entityHit.getEntity() instanceof AbilityProjectileEntity)
				{
					AbilityProjectileEntity entity = (AbilityProjectileEntity) entityHit.getEntity();
					this.onProjectileCollision(this, entity);
				}
			}
			else if (hit.getType() == RayTraceResult.Type.BLOCK)
			{
				BlockRayTraceResult blockHit = (BlockRayTraceResult) hit;

				ProjectileHitEvent event = new ProjectileHitEvent(this, hit);
				if (MinecraftForge.EVENT_BUS.post(event))
					return;

				if (!this.passedThroughNonSolidBlock(blockHit.getBlockPos()))
				{
					if (!this.canPassThroughBlocks)
					{
						this.onBlockImpactEvent.onImpact(blockHit.getBlockPos());
						if (!this.canGetStuckInGround)
							this.remove();
					}
				}
			}
		}
	}

	public void onProjectileCollision(AbilityProjectileEntity owner, AbilityProjectileEntity target)
	{

		boolean isPhysical = owner.isPhysical();
		boolean isTargetPhysical = target.isPhysical();
		boolean isDamageLarger = owner.getDamage() > target.getDamage();

		if(isPhysical)
		{
			if(isTargetPhysical)
			{
				if(isDamageLarger)
				{
					target.remove();
				} else
				{
					owner.remove();
				}
			} else
			{
				owner.remove();
			}
		} else
		{
			if(isTargetPhysical) {
				target.remove();
			} else
			{
				if(isDamageLarger)
					target.remove();
				else
					owner.remove();
			}
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public float getArmorDamage()
	{
		float reduction = (float) (this.damage * (0.1 + this.damage / 150));
		if (reduction > this.damage)
			reduction = this.damage;
		return reduction;
	}

	public void triggerEffects(LivingEntity hitEntity)
	{
		if (this.withEffects.getEffects().length > 0)
		{
			for (EffectInstance instance : this.withEffects.getEffects())
			{
				hitEntity.addEffect(instance);
				if (this.getOwner() instanceof ServerPlayerEntity)
					((ServerPlayerEntity) this.getOwner()).connection.send(new SPlayEntityEffectPacket(hitEntity.getId(), instance));
			}
		}
	}

	public boolean canBlockDamageSource(DamageSource damageSource, LivingEntity target)
	{
		if(damageSource == null)
			return false;
		
		ProjectileBlockEvent event = new ProjectileBlockEvent(damageSource.getDirectEntity());
		boolean flag = event.canBlock();

		if (!damageSource.isBypassArmor() && target.isBlocking() && flag)
		{
			Vector3d Vector3d2 = damageSource.getSourcePosition();
			if (Vector3d2 != null)
			{
				Vector3d Vector3d = this.getLookAngle();
				Vector3d Vector3d1 = Vector3d2.subtract(new Vector3d(target.getX(), target.getY(), target.getZ())).reverse().normalize();
				Vector3d1 = new Vector3d(Vector3d1.x, 0.0D, Vector3d1.z);
				return Vector3d1.dot(Vector3d) < 0.0D;
			}
		}

		return false;
	}

	public void setDamageSource(DamageSource s)
	{
		this.source = s;
	}

	private boolean passedThroughNonSolidBlock(BlockPos pos)
	{
		return Arrays.stream(NON_SOLID_BLOCKS).anyMatch(block -> this.level.getBlockState(pos).getBlock() == block);
	}

	@Override
	public void remove()
	{
		super.remove();
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound)
	{
		super.addAdditionalSaveData(compound);
		compound.putInt("life", this.life);
		compound.putInt("maxLife", this.maxLife);
		compound.putInt("hurtTime", this.hurtTime);
		compound.putInt("knockbackStrength", this.knockbackStrength);
		compound.putFloat("damage", this.damage);
		compound.putFloat("gravity", this.gravity);
		compound.putDouble("collisionSize", this.collisionSize);
		compound.putBoolean("isPhysical", this.entityData.get(IS_PHYSICAL));
		compound.putBoolean("canPassThroughBlocks", this.canPassThroughBlocks);
		compound.putBoolean("canPassThroughEntities", this.canPassThroughEntities);
		compound.putBoolean("canGetStuckInGround", this.canGetStuckInGround);
		compound.putBoolean("changeHurtTime", this.changeHurtTime);
		compound.putBoolean("armorPiercing", this.armorPiercing);
		compound.putBoolean("canHurtThrower", this.canHurtThrower);
		compound.putInt("ownerUUID", this.entityData.get(OWNER));
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound)
	{
		super.readAdditionalSaveData(compound);
		this.life = compound.getInt("life");
		this.maxLife = compound.getInt("maxLife");
		this.hurtTime = compound.getInt("hurtTime");
		this.knockbackStrength = compound.getInt("knockbackStrength");
		this.damage = compound.getFloat("damage");
		this.gravity = compound.getFloat("gravity");
		this.collisionSize = compound.getDouble("collisionSize");
		this.entityData.set(IS_PHYSICAL, compound.getBoolean("isPhysical"));
		this.canPassThroughBlocks = compound.getBoolean("canPassThroughBlocks");
		this.canPassThroughEntities = compound.getBoolean("canPassThroughEntities");
		this.canGetStuckInGround = compound.getBoolean("canGetStuckInGround");
		this.changeHurtTime = compound.getBoolean("changeHurtTime");
		this.armorPiercing = compound.getBoolean("armorPiercing");
		this.canHurtThrower = compound.getBoolean("canHurtThrower");
		this.entityData.set(OWNER, compound.getInt("ownerUUID"));
	}

	@Override
	protected float getGravity()
	{
		return this.gravity;
	}

	@Override
	public boolean ignoreExplosion()
	{
		return true;
	}
	
	public void clearTargets()
	{
		this.targets.clear();
	}
	
	@Override
	public void defineSynchedData()
	{
		this.entityData.define(OWNER, -1);
		this.entityData.define(IS_PHYSICAL, false);
	}

	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/*
	 * Setters/Getters
	 */
	
	public void setThrower(LivingEntity entity)
	{
		this.entityData.set(OWNER, entity.getId());
		this.setOwner(entity);
	}

	@Nullable
	public LivingEntity getThrower()
	{
		if(this.getOwner() instanceof LivingEntity)
			return (LivingEntity) this.getOwner();
		else 
			return null;
	}
	
	@Nullable
	@Override
	public Entity getOwner()
	{
		return this.getEntityData().get(OWNER) != null && this.level.getEntity(this.getEntityData().get(OWNER)) instanceof LivingEntity ? (LivingEntity) this.level.getEntity(this.getEntityData().get(OWNER)) : super.getOwner();
	}
	
	public void setKnockbackStrength(int knockbackStrength)
	{
		this.knockbackStrength = knockbackStrength;
	}
	
	public double getCollisionSize()
	{
		return this.collisionSize;
	}

	public void setCollisionSize(double val)
	{
		this.collisionSize = val;
	}

	public int getLife()
	{
		return this.life;
	}

	public int getMaxLife()
	{
		return this.maxLife;
	}

	public void setMaxLife(int life)
	{
		this.maxLife = life;
		this.life = this.maxLife;
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}
	
	public void setPhysical(boolean affectedByHardening)
	{
		this.entityData.set(IS_PHYSICAL, true);
	}



	public void setHurtThrower()
	{
		this.canHurtThrower = true;
	}

	public boolean isPhysical()
	{
		return this.entityData.get(IS_PHYSICAL);
	}

	public void setPassThroughBlocks()
	{
		this.canPassThroughBlocks = true;
	}

	public void setArmorPiercing()
	{
		this.armorPiercing = true;
	}

	public void setPassThroughEntities()
	{
		this.canPassThroughEntities = true;
	}

	public void setCanGetStuckInGround()
	{
		this.canGetStuckInGround = true;
	}

	public void setDamage(float damage)
	{
		this.damage = damage;
	}

	public float getDamage()
	{
		return this.damage;
	}

	public void setGravity(float gravity)
	{
		this.gravity = gravity;
	}

	public boolean isStuckInGround()
	{
		return this.stuckInGround;
	}

	public void setChangeHurtTime(boolean flag)
	{
		this.changeHurtTime = flag;
	}

	public void setHurtTime(int time)
	{
		this.hurtTime = time;
	}
	
	public void setTargetResetTime(int time)
	{
		this.targetResetTime = time;
	}
	
	public int getTargetResetTime()
	{
		return this.targetResetTime;
	}

	/*
	 * Interfaces
	 */
	public interface IOnEntityImpact extends Serializable
	{
		void onImpact(LivingEntity hitEntity);
	}

	public interface IOnBlockImpact extends Serializable
	{
		void onImpact(BlockPos hitPos);
	}

	public interface IOnTick extends Serializable
	{
		void onTick();
	}

	public interface IWithEffects extends Serializable
	{
		EffectInstance[] getEffects();
	}
}