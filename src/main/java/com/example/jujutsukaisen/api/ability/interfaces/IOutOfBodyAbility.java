package com.example.jujutsukaisen.api.ability.interfaces;

import com.example.jujutsukaisen.api.ability.Ability;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public interface IOutOfBodyAbility
{
	final static Predicate<Ability> IS_ACTIVE = (ability) -> {
		if(!(ability instanceof IOutOfBodyAbility))
			return false;
		
		return ability.isContinuous();
	};
	
	float getMaxRange();
	
	@Nullable
	BlockPos getPivotPoint();
	
	default double getDistanceFromPivot(Entity entity)
	{
		if(this.getPivotPoint() == null)
			return -1;
		return Math.sqrt(entity.distanceToSqr(this.getPivotPoint().getX(), this.getPivotPoint().getY(), this.getPivotPoint().getZ()));
	}
}
