package com.example.jujutsukaisen.api.data;

import net.minecraft.nbt.CompoundNBT;

public interface IExtraUpdateData {
    public CompoundNBT getExtraData();

    public void setExtraData(CompoundNBT nbt);
}
