package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.BlockProtectionRule;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.interfaces.IBodyOverlayAbility;
import com.example.jujutsukaisen.api.ability.interfaces.IPunchOverlayAbility;
import com.example.jujutsukaisen.api.ability.sorts.ChargeableAbility;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.api.ability.sorts.ExplosionAbility;
import com.example.jujutsukaisen.api.ability.sorts.RepeaterAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.world.ExtendedWorldData;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.ability.SUpdateEquippedAbilityPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AbilityHelper {


    public static Predicate<Ability> getAbilityFromCategoryPredicate(AbilityCategories.AbilityCategory category)
    {
        // Removes all non-command abilities obtained from category
        return (ability) ->
        {
            if(ability.getUnlockType() == AbilityUnlock.COMMAND)
                return false;

            if(ability.getCategory() == category)
                return true;

            return false;
        };
    }
    @Nullable
    public static AbilityOverlay getCurrentOverlay(PlayerEntity player)
    {
        AbilityOverlay overlay = null;
        Ability[] list = AbilityDataCapability.get(player).getEquippedAbilities();
        for (Ability ability : list)
        {
            if (ability == null || (ability instanceof ContinuousAbility &&  !ability.isContinuous()))
                continue;

            if (ability instanceof IPunchOverlayAbility)
                overlay = ((IPunchOverlayAbility) ability).getPunchOverlay(player);
            else if (ability instanceof IBodyOverlayAbility)
                overlay = ((IBodyOverlayAbility) ability).getBodyOverlay();
        }

        return overlay;
    }

    public static void enableAbilities(PlayerEntity player, Predicate<Ability> check)
    {
        IAbilityData abilityData = AbilityDataCapability.get(player);

        for (Ability ability : abilityData.getEquippedAbilities(check))
        {
            if (ability != null && ability.isDisabled())
            {
                ability.startCooldown(player);
                if(!player.level.isClientSide)
                    PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, ability), player);
            }
        }
    }

    public static void disableAbilities(PlayerEntity player, int duration, Predicate<Ability> check)
    {
        IAbilityData abilityData = AbilityDataCapability.get(player);

        for (Ability ability : abilityData.getEquippedAbilities(check))
        {
            if (ability != null && !ability.isDisabled())
            {
                if (ability instanceof ContinuousAbility && ability.getState() == Ability.State.CONTINUOUS)
                    ((ContinuousAbility) ability).stopContinuity(player);
                if (ability instanceof RepeaterAbility && ability.getState() == Ability.State.CONTINUOUS)
                    ((RepeaterAbility) ability).setRepeaterCount(((RepeaterAbility) ability).getMaxRepeaterCount());
                if (ability instanceof ChargeableAbility && ability.getState() == Ability.State.CHARGING)
                {
                    ((ChargeableAbility) ability).setChargeTime(((ChargeableAbility) ability).getMaxChargeTime() / 20);
                    ability.startCooldown(player);
                }
                ability.startDisable(duration);

                if(!player.level.isClientSide)
                    PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, ability), player);
            }
        }
    }

    public static void causeDamageWithPiercing(LivingEntity target, DamageSource source, float damage, float pierce)
    {
        if(source.isBypassArmor())
        {
            target.hurt(source, damage);
            return;
        }

        DamageSource piercingSource;
        if(source instanceof IndirectEntityDamageSource)
            piercingSource = new IndirectEntityDamageSource(source.getMsgId(), source.getDirectEntity(), source.getEntity()).bypassArmor();
        else if(source instanceof EntityDamageSource)
            piercingSource = new EntityDamageSource(source.getMsgId(), source.getEntity()).bypassArmor();
        else
            piercingSource = new DamageSource(source.getMsgId()).bypassArmor();

        if(source.isFire())
            piercingSource.setIsFire();
        if(source.isExplosion())
            piercingSource.setExplosion();
        if(source.isProjectile())
            piercingSource.setProjectile();

        target.hurt(piercingSource, damage * pierce);
        target.hurtTime = target.invulnerableTime = 0;
        target.hurt(source, damage * (1.0f - pierce));
    }

    public static List<BlockPos> createEmptyCube(World world, double posX, double posY, double posZ, int sizeX, int sizeY, int sizeZ, Block blockToPlace, BlockProtectionRule rule)
    {
        List<BlockPos> blockPositions = new ArrayList<BlockPos>();
        for (int x = -sizeX; x <= sizeX; x++)
        {
            for (int y = -sizeY; y <= sizeY; y++)
            {
                for (int z = -sizeZ; z <= sizeZ; z++)
                {
                    if (x == -sizeX || x == sizeX || y == -sizeY || y == sizeY || z == -sizeZ || z == sizeZ)
                    {
                        BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);
                        if(placeBlockIfAllowed(world, posX + x, posY + y, posZ + z, blockToPlace, rule))
                            blockPositions.add(pos);
                    }
                }
            }
        }
        return blockPositions;
    }

    public static List<BlockPos> createFilledCube(World world, double posX, double posY, double posZ, int sizeX, int sizeY, int sizeZ, Block blockToPlace, BlockProtectionRule rule)
    {
        List<BlockPos> blockPositions = new ArrayList<BlockPos>();
        for (int x = -sizeX; x <= sizeX; x++)
            for (int y = -sizeY; y <= sizeY; y++)
                for (int z = -sizeZ; z <= sizeZ; z++)
                {
                    BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);
                    if(placeBlockIfAllowed(world, posX + x, posY + y, posZ + z, blockToPlace, rule))
                        blockPositions.add(pos);
                }

        return blockPositions;
    }

    public static List<BlockPos> createSphere(World world, BlockPos center, int radius, boolean hollow, final Block block, int flags, BlockProtectionRule rule)
    {
        int x0 = center.getX();
        int y0 = center.getY();
        int z0 = center.getZ();

        List<BlockPos> blockPositions = new ArrayList<BlockPos>();
        for (int y = y0 - radius; y <= y0 + radius; y++)
        {
            for (int x = x0 - radius; x <= x0 + radius; x++)
            {
                for (int z = z0 - radius; z <= z0 + radius; z++)
                {
                    double distance = ((x0 - x) * (x0 - x) + ((z0 - z) * (z0 - z)) + ((y0 - y) * (y0 - y)));

                    if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1))))
                    {
                        BlockPos pos = new BlockPos(x, y, z);
                        if(placeBlockIfAllowed(world, pos.getX(), pos.getY(), pos.getZ(), block, flags, rule))
                            blockPositions.add(pos);
                    }
                }
            }
        }

        return blockPositions;
    }

    public static boolean placeBlockIfAllowed(World world, double posX, double posY, double posZ, Block toPlace, BlockProtectionRule rule)
    {
        return placeBlockIfAllowed(world, posX, posY, posZ, toPlace, 2, rule);
    }

    public static boolean placeBlockIfAllowed(World world, double posX, double posY, double posZ, Block toPlace, int flag, BlockProtectionRule rule)
    {
        BlockPos pos = new BlockPos(posX, posY, posZ);
        if(World.isOutsideBuildHeight(pos))
            return false;

        BlockState currentBlockState = world.getBlockState(pos);

        ExtendedWorldData worldData = ExtendedWorldData.get(world);
        boolean inProtectedAreaFlag = worldData.isInsideRestrictedArea((int) posX, (int) posY, (int) posZ);
        boolean isGriefDisabled = false;
        boolean isGriefBypass = rule.getBypassGriefRule();

        if ((!isGriefBypass && inProtectedAreaFlag) || (!isGriefBypass && isGriefDisabled))
            return false;

        if (rule.check(world, pos, currentBlockState))
        {
            BlockState state = toPlace.defaultBlockState();
            Beapi.setBlockStateInChunk(world, pos, state, flag);
            //world.setBlockAndUpdate(pos, state, flag);
            return true;
        }

        return false;
    }

    @Deprecated
    public static List<BlockPos> createEmptySphere(World world, int posX, int posY, int posZ, int size, final Block block, BlockProtectionRule rule)
    {
        return AbilityHelper.createSphere(world, new BlockPos(posX, posY, posZ), size, true, block, 2, rule);
    }

    @Deprecated
    public static List<BlockPos> createFilledSphere(World world, int posX, int posY, int posZ, int size, final Block block, BlockProtectionRule rule)
    {
        return AbilityHelper.createSphere(world, new BlockPos(posX, posY, posZ), size, false, block, 2, rule);
    }

    public static double[] propulsion(LivingEntity entity, double extraMX, double extraMZ)
    {
        double mX = -MathHelper.sin(entity.xRot / 180.0F * (float) Math.PI) * MathHelper.cos(entity.yRot / 180.0F * (float) Math.PI) * 0.4;
        double mZ = MathHelper.cos(entity.xRot / 180.0F * (float) Math.PI) * MathHelper.cos(entity.yRot / 180.0F * (float) Math.PI) * 0.4;

        double f2 = MathHelper.sqrt(mX * mX + entity.getDeltaMovement().y * entity.getDeltaMovement().y + mZ * mZ);
        mX /= f2;
        mZ /= f2;
        mX += entity.level.random.nextGaussian() * 0.007499999832361937D * 1.0;
        mZ += entity.level.random.nextGaussian() * 0.007499999832361937D * 1.0;
        mX *= extraMX;
        mZ *= extraMZ;

        return new double[]
                {
                        mX, mZ
                };
    }

    public static AbilityCategories.AbilityCategory getTechniqueCategory()
    {
        return AbilityCategories.AbilityCategory.valueOf("TECHNIQUE");
    }

    public static ExplosionAbility newExplosion(Entity entity, World world, double posX, double posY, double posZ, float size)
    {
        return new ExplosionAbility(entity, world, posX, posY, posZ, size);
    }

    public static boolean canUseBrawlerAbilities(LivingEntity entity)
    {
        return entity.getMainHandItem().isEmpty();
    }


    /**
     * @Deprecated I mean...its literally a different way of saying ExtendedWorldData::isInsideRestrictedArea so its just boilerplate at this point
     */
    @Deprecated
    public static boolean checkForRestriction(World world, int posX, int posY, int posZ)
    {
        ExtendedWorldData worldData = ExtendedWorldData.get(world);

        if (worldData.isInsideRestrictedArea(posX, posY, posZ))
            return true;

        return false;
    }

    public static boolean isAffectedByWater(LivingEntity entity)
    {
        boolean isUnderWater = entity.isEyeInFluid(FluidTags.WATER);
        boolean waterAbove = entity.level.getBlockState(entity.blockPosition().above()).getBlock() == Blocks.WATER;
        boolean inWater = entity.level.getBlockState(entity.blockPosition()).getBlock() == Blocks.WATER;
        boolean waterUnder = entity.level.getBlockState(entity.blockPosition().below()).getBlock() == Blocks.WATER;
        int total = 0;
        if(waterAbove)
            total++;
        if(inWater)
            total++;
        if(waterUnder)
            total++;
        boolean hasWaterUnder = entity.isInWater() && total >= 2;

        return entity.getVehicle() == null && (isUnderWater || hasWaterUnder);
    }






        /**
         * Use the RoomAbility::isEntityInThisRoom() method for precise room size checks
         */


        /**
         * Use the TorikagoAbility::isEntityInThisTorikago() method for precise room size checks
         */




}

