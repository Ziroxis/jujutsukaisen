package com.example.jujutsukaisen.data.quest;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class QuestDataProvider implements ICapabilitySerializable<CompoundNBT>
{

	private IQuestData instance = QuestDataCapability.INSTANCE.getDefaultInstance();

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return QuestDataCapability.INSTANCE.orEmpty(cap, LazyOptional.of(() -> instance));
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		return (CompoundNBT) QuestDataCapability.INSTANCE.getStorage().writeNBT(QuestDataCapability.INSTANCE, instance, null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		QuestDataCapability.INSTANCE.getStorage().readNBT(QuestDataCapability.INSTANCE, instance, null, nbt);
	}
	
}