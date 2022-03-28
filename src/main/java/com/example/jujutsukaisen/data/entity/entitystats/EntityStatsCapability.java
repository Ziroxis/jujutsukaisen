package com.example.jujutsukaisen.data.entity.entitystats;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class EntityStatsCapability {

    @CapabilityInject(IEntityStats.class)
    public static final Capability<IEntityStats> INSTANCE = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IEntityStats.class, new Capability.IStorage<IEntityStats>()
        {
            @Override
            public INBT writeNBT(Capability<IEntityStats> capability, IEntityStats instance, Direction side)
            {
                CompoundNBT props = new CompoundNBT();

                props.putString("clan", instance.getClan());
                props.putString("technique", instance.getTechnique());
                props.putInt("level", instance.getLevel());
                props.putInt("experience", instance.getExperience());
                props.putInt("maxexperience", instance.getMaxExperience());

                return props;
            }

            @Override
            public void readNBT(Capability<IEntityStats> capability, IEntityStats instance, Direction side, INBT nbt)
            {
                CompoundNBT props = (CompoundNBT) nbt;

                instance.setClan(props.getString("clan"));
                instance.setTechnique(props.getString("technique"));
                instance.setLevel(props.getInt("level"));
                instance.setExperience(props.getInt("experience"));
                instance.setMaxExperience(props.getInt("maxexperience"));
            }
        }, () -> new EntityStatsBase());

    }
    public static IEntityStats get(final LivingEntity entity)
    {
        return entity.getCapability(INSTANCE, null).orElse(new EntityStatsBase());
    }
}
