package com.example.jujutsukaisen.api.ability.sorts;

import com.example.jujutsukaisen.api.ability.AbilityHelper;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.particles.ParticleEffect;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class ExplosionAbility extends Explosion {

    private World world;
    private Entity exploder;
    private double explosionX;
    private double explosionY;
    private double explosionZ;
    private float explosionSize;
    private ParticleEffect particles;
    private DamageSource damageSource;
    private ModDamageSource modDamageSource;

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
    private int size = 52;
    public ArrayList<Entity> damagedEntities = new ArrayList();


    public ExplosionAbility(Entity entity, World world, double posX, double posY, double posZ, float power)
    {
        super(world, entity, posX, posY, posZ, power, false, Mode.DESTROY);
        this.world = world;
        this.exploder = entity;
        this.explosionSize = power;
        this.explosionX = posX;
        this.explosionY = posY;
        this.explosionZ = posZ;
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
        boolean flag = this.exploder != null && this.exploder instanceof PlayerEntity && AbilityHelper.checkForRestriction(this.world, (int) this.explosionX, (int) this.explosionY, (int) this.explosionZ);
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
                            if (!blockState.isAir(this.world, blockpos) || !ifluidstate.isEmpty())
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
        ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
        Vector3d v3d = new Vector3d(this.explosionX, this.explosionY, this.explosionZ);

        if(this.canDamageEntities)
        {
            for (int k2 = 0; k2 < list.size(); ++k2)
            {
                Entity entity = list.get(k2);
                boolean isInProtection = AbilityHelper.checkForRestriction(this.world, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ());

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
                                entity.hurt(modDamageSource, this.staticDamage);
                                damagedEntities.add(entity);

                            }
                            else
                            {
                                float damage = ((float) ((power * power + power) / 2.0D * 7.0D * f3 + 1.0D));
                                entity.hurt(modDamageSource, damage);
                                damagedEntities.add(entity);
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
