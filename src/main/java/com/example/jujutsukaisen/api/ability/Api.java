package com.example.jujutsukaisen.api.ability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IExtensibleEnum;

import javax.annotation.Nullable;
import java.util.function.Function;

public class Api {
    public static enum AbilityCategory implements IExtensibleEnum
    {
        ALL,
        TECHNIQUE;

        private Function<PlayerEntity, ResourceLocation> iconFunction;

        private AbilityCategory()
        {
            this.iconFunction = null;
        }

        private AbilityCategory(Function<PlayerEntity, ResourceLocation> function)
        {
            this.iconFunction = function;
        }

        @Nullable
        public ResourceLocation getIcon(PlayerEntity player)
        {
            if(this.iconFunction == null)
                return null;
            return this.iconFunction.apply(player);
        }

        public static AbilityCategory create(String name, Function<PlayerEntity, ResourceLocation> function)
        {
            throw new IllegalStateException("Enum not extended");
        }
    }
}
