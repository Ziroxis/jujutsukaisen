package com.example.jujutsukaisen.world.gen;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.world.structure.configured.ConfiguredStructures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

public class ModStructureGeneration {

    //structures that spawn in specific biomes
    public static void generateStructures(final BiomeLoadingEvent event)
    {
        RegistryKey key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set types = BiomeDictionary.getTypes(key);

        if (types.contains(BiomeDictionary.Type.PLAINS)
                && !types.contains(BiomeDictionary.Type.NETHER)
                && !types.contains(BiomeDictionary.Type.END)
                && !types.contains(BiomeDictionary.Type.WET)
                && !types.contains(BiomeDictionary.Type.RIVER)
                && !types.contains(BiomeDictionary.Type.HOT)
                && !types.contains(BiomeDictionary.Type.OCEAN))
        {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_DOJO);
        }

    }
}
