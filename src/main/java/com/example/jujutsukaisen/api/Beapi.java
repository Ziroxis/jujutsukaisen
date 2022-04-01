package com.example.jujutsukaisen.api;


import com.example.jujutsukaisen.Main;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Beapi
{
    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity) {
        return Beapi.rayTraceBlocksAndEntities(entity, 1024, 0.4f);
    }

    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity, double distance) {
        return Beapi.rayTraceBlocksAndEntities(entity, distance, 0.2f);
    }

    public static EntityRayTraceResult rayTraceEntities(Entity entity, double distance, float entityBoxRange) {
        Vector3d lookVec = entity.getLookAngle();
        Vector3d startVec = entity.position().add(0, entity.getEyeHeight(), 0);
        Vector3d endVec = startVec.add(entity.getLookAngle().scale(distance));
        EntityRayTraceResult entityResult = null;

        for (int i = 0; i < distance * 2; i++)
        {
            if (entityResult != null)
                break;

            float scale = i / 2F;
            Vector3d pos = startVec.add(lookVec.scale(scale));

            Vector3d min = pos.add(entityBoxRange, entityBoxRange, entityBoxRange);
            Vector3d max = pos.add(-entityBoxRange, -entityBoxRange, -entityBoxRange);
            List<Entity> list = entity.level.getEntities(entity, new AxisAlignedBB(min.x, min.y, min.z, max.x, max.y, max.z));
            list.remove(entity);
            for (Entity e : list) {
                entityResult = new EntityRayTraceResult(e, pos);
                break;
            }
        }

        return entityResult;

    }

    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity, double distance, float entityBoxRange) {
        Vector3d lookVec = entity.getLookAngle();
        Vector3d startVec = entity.position().add(0, entity.getEyeHeight(), 0);
        Vector3d endVec = startVec.add(entity.getLookAngle().scale(distance));
        RayTraceResult blockResult = entity.level.clip(new RayTraceContext(startVec, endVec,  RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity));
        RayTraceResult entityResult = null;

        for (int i = 0; i < distance * 2; i++)
        {
            if (entityResult != null)
                break;

            float scale = i / 2F;
            Vector3d pos = startVec.add(lookVec.scale(scale));

            Vector3d min = pos.add(entityBoxRange, entityBoxRange, entityBoxRange);
            Vector3d max = pos.add(-entityBoxRange, -entityBoxRange, -entityBoxRange);
            List<Entity> list = entity.level.getEntities(entity, new AxisAlignedBB(min.x, min.y, min.z, max.x, max.y, max.z));
            list.remove(entity);
            for (Entity e : list) {


                entityResult = new EntityRayTraceResult(e, pos);
                break;
            }
        }

        if (entityResult != null && entityResult.getLocation().distanceTo(startVec) <= blockResult.getLocation().distanceTo(startVec)) {
            return entityResult;
        } else {
            return blockResult;
        }

    }

    public static boolean saveNBTStructure(ServerWorld world, String name, BlockPos pos, BlockPos size, List<Block> toIgnore)
    {
        if (!world.isClientSide)
        {
            ServerWorld serverworld = world;
            TemplateManager templatemanager = serverworld.getStructureManager();
            ResourceLocation res = new ResourceLocation(Main.MODID, name);

            Template template;
            try
            {
                template = templatemanager.get(res);
            }
            catch (ResourceLocationException ex)
            {
                ex.printStackTrace();
                return false;
            }

            toIgnore.add(Blocks.STRUCTURE_VOID);
            toIgnore.add(Blocks.BEDROCK);
            //.add(Blocks.WATER);
            takeBlocksFromWorld(template, world, pos, size, toIgnore);
            template.setAuthor("?");
            try
            {
                return templatemanager.save(res);
            }
            catch (ResourceLocationException var7)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public static boolean loadNBTStructure(ServerWorld world, String name, BlockPos pos)
    {
        if (!world.isClientSide)
        {
            BlockPos blockpos = pos;
            TemplateManager templatemanager = world.getStructureManager();
            ResourceLocation res = new ResourceLocation(Main.MODID, name);

            Template template;
            try
            {
                template = templatemanager.get(res);
            }
            catch (ResourceLocationException ex)
            {
                ex.printStackTrace();
                return false;
            }

            if (template == null)
            {
                return false;
            }
            else
            {
                BlockState blockstate = world.getBlockState(blockpos);
                world.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
            }

            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(true).setChunkPos((ChunkPos) null);
            placementsettings.clearProcessors()
                    .addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR)
                    .addProcessor(new IntegrityProcessor(1f)).setRandom(new Random(Util.getMillis()));
            //placementsettings.clearProcessors().addProcessor(new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.SAND)));

            template.placeInWorldChunk(world, pos, placementsettings, new Random(Util.getMillis()));
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void takeBlocksFromWorld(Template template, World world, BlockPos startPos, BlockPos size, @Nullable List<Block> toIgnore)
    {
        if (size.getX() >= 1 && size.getY() >= 1 && size.getZ() >= 1)
        {
            BlockPos blockpos = startPos.offset(size).offset(-1, -1, -1);
            List<Template.BlockInfo> list = Lists.newArrayList();
            List<Template.BlockInfo> list1 = Lists.newArrayList();
            List<Template.BlockInfo> list2 = Lists.newArrayList();
            BlockPos blockpos1 = new BlockPos(Math.min(startPos.getX(), blockpos.getX()), Math.min(startPos.getY(), blockpos.getY()), Math.min(startPos.getZ(), blockpos.getZ()));
            BlockPos blockpos2 = new BlockPos(Math.max(startPos.getX(), blockpos.getX()), Math.max(startPos.getY(), blockpos.getY()), Math.max(startPos.getZ(), blockpos.getZ()));
            //((TemplateMixin)template).setSize(size);

            for (BlockPos blockpos3 : BlockPos.betweenClosed(blockpos1, blockpos2))
            {
                BlockPos blockpos4 = blockpos3.subtract(blockpos1);
                BlockState blockstate = world.getBlockState(blockpos3);

                if (toIgnore != null && toIgnore.contains(blockstate.getBlock()))
                {
                    world.setBlockAndUpdate(blockpos3, Blocks.AIR.defaultBlockState());
                    blockstate = world.getBlockState(blockpos3);
                    //if(blockstate.has(BlockStateProperties.WATERLOGGED))
                    //	blockstate.with(BlockStateProperties.WATERLOGGED, false);
                }

                TileEntity tileentity = world.getBlockEntity(blockpos3);
                if (tileentity != null)
                {
                    CompoundNBT compoundnbt = tileentity.save(new CompoundNBT());
                    compoundnbt.remove("x");
                    compoundnbt.remove("y");
                    compoundnbt.remove("z");
                    list1.add(new Template.BlockInfo(blockpos4, blockstate, compoundnbt));
                }
                else if (!blockstate.propagatesSkylightDown(world, blockpos3) && !blockstate.isCollisionShapeFullBlock(world, blockpos3))
                {
                    list2.add(new Template.BlockInfo(blockpos4, blockstate, (CompoundNBT) null));
                }
                else
                {
                    list.add(new Template.BlockInfo(blockpos4, blockstate, (CompoundNBT) null));
                }
            }

            List<Template.BlockInfo> list3 = Lists.newArrayList();
            list3.addAll(list);
            list3.addAll(list1);
            list3.addAll(list2);
            //((TemplateMixin)template).getBlocks().clear();
            //((TemplateMixin)template).getBlocks().add(list3);
            //((TemplateMixin)template).getEntities().clear();
        }
    }
    public static double randomWithRange(int min, int max)
    {
        return new Random().nextInt(max + 1 - min) + min;
    }

    public static int RNG(int cap)
    {
        Random rand = new Random();
        int int_random = rand.nextInt(cap);

        return int_random;
    }

    public static boolean isNullOrEmpty(String str)
    {
        if (str != null && !str.isEmpty() && !str.equalsIgnoreCase("n/a"))
            return false;
        return true;
    }

    public static boolean setBlockStateInChunk(World world, BlockPos pos, BlockState newState, int flags)
    {
        if (World.isOutsideBuildHeight(pos))
            return false;
        else if (!world.isClientSide && world.isDebug())
            return false;
        else
        {
            Chunk chunk = world.getChunkAt(pos);
            pos = pos.immutable();

            BlockState blockstate = chunk.setBlockState(pos, newState, (flags & 64) != 0);
            if (blockstate != null)
            {
                //world.markAndNotifyBlock(pos, chunk, blockstate, newState, flags);
                if ((flags & 2) != 0 && (!world.isClientSide || (flags & 4) == 0))
                    world.markAndNotifyBlock(pos, chunk, blockstate, newState, flags, 256);
            }
            return true;
        }
    }

    public static String getResourceName(String text)
    {
        return text
                .replaceAll("[ \\t]+$", "")
                .replaceAll("\\(","")
                .replaceAll("\\)","")
                .replaceAll("\\s+", "_")
                .replaceAll("[\\'\\:\\-\\,\\#]", "")
                .replaceAll("\\&", "and").toLowerCase();
    }
}
