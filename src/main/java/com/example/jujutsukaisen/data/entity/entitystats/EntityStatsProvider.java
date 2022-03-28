package com.example.jujutsukaisen.data.entity.entitystats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class EntityStatsProvider implements ICapabilitySerializable<CompoundNBT> {

    private IEntityStats instance = EntityStatsCapability.INSTANCE.getDefaultInstance();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        return EntityStatsCapability.INSTANCE.orEmpty(cap, LazyOptional.of(() -> instance));
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) EntityStatsCapability.INSTANCE.getStorage().writeNBT(EntityStatsCapability.INSTANCE, instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        EntityStatsCapability.INSTANCE.getStorage().readNBT(EntityStatsCapability.INSTANCE, instance, null, nbt);
    }
}
