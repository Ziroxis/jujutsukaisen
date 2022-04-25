package com.example.jujutsukaisen.api;


import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.init.ModRegistry;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;

public class Beapi
{
    public static <T extends Entity> EntityType.Builder createEntityType(EntityType.IFactory<T> factory)
    {
        return createEntityType(factory, EntityClassification.MISC);
    }
    public static <T extends Entity> EntityType.Builder createEntityType(EntityType.IFactory<T> factory, EntityClassification classification)
    {
        EntityType.Builder<T> builder = EntityType.Builder.<T>of(factory, classification);

        builder.setTrackingRange(128).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).sized(0.6F, 1.8F);

        return builder;
    }
    public static <T extends Entity> List<T> getEntitiesNear(BlockPos pos, World world, double radius, Predicate<Entity> predicate, Class<? extends T>... classEntities)
    {
        if(predicate != null)
            predicate = predicate.and(EntityPredicates.NO_SPECTATORS);
        else
            predicate = EntityPredicates.NO_SPECTATORS;

        AxisAlignedBB aabb = new AxisAlignedBB(pos).expandTowards(radius, radius, radius);
        List<T> list = new ArrayList<T>();
        for (Class<? extends T> clzz : classEntities)
        {
            list.addAll(world.getEntitiesOfClass(clzz, aabb, predicate));
        }
        return list;
    }


    public static boolean isPosClearForPlayer(World world, BlockPos pos)
    {
        return (world.isEmptyBlock(pos) || world.getBlockState(pos).getCollisionShape(world, pos).isEmpty())
                && (world.isEmptyBlock(pos.above()) || world.getBlockState(pos.above()).getCollisionShape(world, pos.above()).isEmpty());
    }

    public static BlockPos rayTraceBlockSafe(PlayerEntity player, float range)
    {
        World world = player.level;
        Vector3d startVec = player.position().add(0.0, player.getEyeHeight(), 0.0);
        Vector3d endVec = startVec.add(player.getLookAngle().scale(range));
        BlockRayTraceResult result = world.clip(new RayTraceContext(startVec, endVec,  RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, player));
        BlockPos dashPos = result.getDirection().equals(Direction.DOWN) ? result.getBlockPos().below(2) : result.getBlockPos().offset(result.getDirection().getNormal());

        boolean posIsFree = Beapi.isPosClearForPlayer(world, dashPos);
        boolean tryUp = true;

        while (!posIsFree)
        {
            if(tryUp)
            {
                dashPos = dashPos.above();
                BlockPos bpb = dashPos.below();
                Vector3d v3d = new Vector3d(bpb.getX(), bpb.getY(), bpb.getZ());
                posIsFree = Beapi.isPosClearForPlayer(world, dashPos) && world.clip(new RayTraceContext(startVec, v3d,
                        RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, player)).getType().equals(RayTraceResult.Type.MISS);
                if(world.getMaxBuildHeight() >= dashPos.getY())
                    tryUp = false;
            } else
            {
                dashPos = dashPos.below();
                BlockPos bpa = dashPos.above();
                Vector3d v3d = new Vector3d(bpa.getX(), bpa.getY(), bpa.getZ());
                posIsFree = Beapi.isPosClearForPlayer(world, dashPos) && world.clip(new RayTraceContext(startVec, v3d,
                        RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, player)).getType().equals(RayTraceResult.Type.MISS);
                if(dashPos.getY() <= 0)
                    break;
            }
        }

        return (posIsFree) ? dashPos : null;
    }

    public static Vector3d propulsion(LivingEntity entity, double extraVelX, double extraVelZ)
    {
        return propulsion(entity, extraVelX, 0, extraVelZ);
    }


    public static <T extends Entity> List<T> getEntitiesNear(BlockPos pos, World world, double radius, Class<? extends T>... classEntities)
    {
        AxisAlignedBB aabb = new AxisAlignedBB(pos).expandTowards(radius, radius, radius);
        List<T> list = new ArrayList<T>();
        for (Class<? extends T> clzz : classEntities)
        {
            list.addAll(world.getEntitiesOfClass(clzz, aabb));
        }
        return list;
    }


    public static Vector3d propulsion(LivingEntity entity, double extraVelX, double extraVelY, double extraVelZ)
    {
        return entity.getLookAngle().multiply(extraVelX, extraVelY, extraVelZ);
    }

    public static final int getIndexOfItemStack(Item item, IInventory inven)
    {
        for (int i = 0; i < inven.getContainerSize(); i++)
        {
            if (inven.getItem(i).getItem() == item)
            {
                return i;
            }
        }
        return -1;
    }


    public static boolean isDebug()
    {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    }

    private static HashMap<String, String> langMap = new HashMap<String, String>();

    public static HashMap<String, String> getLangMap()
    {
        return langMap;
    }

    public static List<String> splitString(FontRenderer font, String text, int posX, int wrapWidth)
    {
        while (text != null && text.endsWith("\n"))
        {
            text = text.substring(0, text.length() - 1);
        }

        List<String> newText = new ArrayList<String>();

        //for (IReorderingProcessor proc : font.split(new StringTextComponent(text), wrapWidth))
        //{
        //}

		/*for (String s : font.listFormattedStringToWidth(text, wrapWidth))
		{
			if (font.isBidirectional())
			{
				int i = font.width(font.bidirectionalShaping(s));
				posX += wrapWidth - i;
			}

			newText.add(s);
		}*/

        return newText;
    }

    public static void drawStringWithBorder(FontRenderer font, MatrixStack matrixStack, IReorderingProcessor text, int posX, int posY, int color)
    {
        matrixStack.pushPose();
        font.drawShadow(matrixStack, text, posX, posY - 0.7f, 1);
        font.drawShadow(matrixStack, text, posX, posY + 0.7f, 1);
        font.drawShadow(matrixStack, text, posX + 0.7f, posY, 1);
        font.drawShadow(matrixStack, text, posX - 0.7f, posY, 1);
        matrixStack.translate(0, 0, 1);
        font.draw(matrixStack, text, posX, posY, color);
        matrixStack.popPose();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
    }

    public static void drawIcon(Matrix4f matrix, ResourceLocation rs, int x, int y, int u, int v)
    {
        Minecraft.getInstance().getTextureManager().bind(rs);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(matrix, x, y + v, 1).uv(0.0f, 1.0f).endVertex();
        bufferbuilder.vertex(matrix, x + u, y + v, 1).uv(1.0f, 1.0f).endVertex();
        bufferbuilder.vertex(matrix, x + u, y, 1).uv(1.0f, 0.0f).endVertex();
        bufferbuilder.vertex(matrix, x, y, 1).uv(0.0f, 0.0f).endVertex();
        Tessellator.getInstance().end();
    }

    public static void drawIcon(ResourceLocation rs, int x, int y, int u, int v)
    {
        Minecraft.getInstance().getTextureManager().bind(rs);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(x, y + v, 1).uv(0.0f, 1.0f).endVertex();
        bufferbuilder.vertex(x + u, y + v, 1).uv(1.0f, 1.0f).endVertex();
        bufferbuilder.vertex(x + u, y, 1).uv(1.0f, 0.0f).endVertex();
        bufferbuilder.vertex(x, y, 1).uv(0.0f, 0.0f).endVertex();
        Tessellator.getInstance().end();
    }

    public static Color hexToRGB(String hexColor)
    {
        if (hexColor.startsWith("#"))
            hexColor = hexColor.substring(1);

        if (hexColor.length() == 8)
        {
            int r = Integer.parseInt(hexColor.substring(0, 2), 16);
            int g = Integer.parseInt(hexColor.substring(2, 4), 16);
            int b = Integer.parseInt(hexColor.substring(4, 6), 16);
            int a = Integer.parseInt(hexColor.substring(6, 8), 16);
            return new Color(r, g, b, a);
        }
        else
            return Color.decode("#" + hexColor);
    }

    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity) {
        return Beapi.rayTraceBlocksAndEntities(entity, 1024, 0.4f);
    }

    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity, double distance) {
        return Beapi.rayTraceBlocksAndEntities(entity, distance, 0.2f);
    }

    public static void drawStringWithBorder(FontRenderer font, MatrixStack matrixStack, String text, int posX, int posY, int color)
    {
        matrixStack.pushPose();
        String unformattedText = escapeTextFormattingChars(text);
        font.drawShadow(matrixStack, unformattedText, posX, posY - 0.7f, 0);
        font.drawShadow(matrixStack, unformattedText, posX, posY + 0.7f, 0);
        font.drawShadow(matrixStack, unformattedText, posX + 0.7f, posY, 0);
        font.drawShadow(matrixStack, unformattedText, posX - 0.7f, posY, 0);
        matrixStack.translate(0, 0, 1);
        font.draw(matrixStack, unformattedText, posX, posY, -1);
        matrixStack.popPose();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
    }

    public static String escapeTextFormattingChars(String text)
    {
        return text.replaceAll("§[0-9a-f]", "");
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

    public static void drawAbilityIcon(String iconName, int x, int y, int z, int u, int v)
    {
        drawAbilityIcon(iconName, x, y, z, u, v, 1, 1, 1);
    }

    public static void drawAbilityIcon(String iconName, int x, int y, int z, int u, int v, float red, float green, float blue)
    {
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(Main.MODID, "textures/abilities/" + Beapi.getResourceName(iconName) + ".png"));
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferbuilder.vertex(x, y + v, z).color(red, green, blue, 1f).uv(0.0f, 1.0f).endVertex();
        bufferbuilder.vertex(x + u, y + v, z).color(red, green, blue, 1f).uv(1.0f, 1.0f).endVertex();
        bufferbuilder.vertex(x + u, y, z).color(red, green, blue, 1f).uv(1.0f, 0.0f).endVertex();
        bufferbuilder.vertex(x, y, z).color(red, green, blue, 1f).uv(0f, 0f).endVertex();
        Tessellator.getInstance().end();
    }

    public static void drawAbilityIcon(String iconName, int x, int y, int u, int v)
    {
        drawAbilityIcon(iconName, x, y, 1, u, v);
    }

    public static <T extends Entity> RegistryObject<EntityType<T>> registerEntityType(String localizedName, Supplier<EntityType<T>> supp)
    {
        String resourceName = Beapi.getResourceName(localizedName);
        Beapi.getLangMap().put("entity." + Main.MODID + "." + resourceName, localizedName);

        RegistryObject<EntityType<T>> reg = ModRegistry.ENTITY_TYPES.register(resourceName, supp);

        return reg;
    }

}
