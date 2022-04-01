package com.example.jujutsukaisen;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BlockProtectionRule {

    private boolean bypassGriefRule = false;
    private List<IReplaceBlockRule> approvedRules = new ArrayList<IReplaceBlockRule>();
    private HashSet<Block> blocks = new HashSet<Block>();
    private HashSet<Block> bannedBlocks = new HashSet<Block>();

    public BlockProtectionRule(BlockProtectionRule... rules)
    {
        for(BlockProtectionRule rule : rules)
        {
            this.blocks.addAll(rule.getApprovedBlocks());
            this.approvedRules.addAll(rule.getApprovedRules());
        }

    }

    public HashSet<Block> getBannedBlocks()
    {
        return this.bannedBlocks;
    }

    public BlockProtectionRule addBannedBlocks(HashSet<Block> set)
    {
        Block[] arr = new Block[set.size()];
        set.toArray(arr);
        return this.addBannedBlocks(arr);
    }

    public BlockProtectionRule addBannedBlocks(Block... blocks)
    {
        for(Block block : blocks)
        {
            if(this.blocks.contains(block))
                this.blocks.remove(block);
        }
        this.bannedBlocks.addAll(Lists.newArrayList(blocks));
        return this;
    }

    public HashSet<Block> getApprovedBlocks()
    {
        return this.blocks;
    }

    public List<IReplaceBlockRule> getApprovedRules()
    {
        return this.approvedRules;
    }

    public BlockProtectionRule addApprovedRules(IReplaceBlockRule fn)
    {
        this.approvedRules.add(fn);
        return this;
    }

    public BlockProtectionRule addApprovedBlocks(Block... blocks)
    {
        this.blocks.addAll(Lists.newArrayList(blocks));
        return this;
    }

    public BlockProtectionRule addApprovedMaterials(Material... materials)
    {
        for(Block block : ForgeRegistries.BLOCKS.getValues())
        {
            for(Material mat : materials)
            {
                if(block.defaultBlockState().getMaterial() == mat && !this.blocks.contains(block))
                    this.blocks.add(block);
            }
        }
        return this;
    }

    public boolean getBypassGriefRule()
    {
        return this.bypassGriefRule;
    }

    public BlockProtectionRule setBypassGriefRule()
    {
        this.bypassGriefRule = true;
        return this;
    }

    public boolean check(World world, BlockPos pos, BlockState state)
    {
        for(IReplaceBlockRule fn : this.approvedRules)
            fn.replace(world, pos, state);

        return this.isPresent(state);
    }

    public boolean isPresent(BlockState state)
    {
        if(this.blocks.contains(state.getBlock()))
            return true;

        return false;
    }

    public boolean isBanned(Block block)
    {
        return this.bannedBlocks.contains(block);
    }

    @FunctionalInterface
    public interface IReplaceBlockRule
    {
        boolean replace(World world, BlockPos pos, BlockState oldState);
    }
}
