package com.example.jujutsukaisen.quest;

import com.example.jujutsukaisen.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

public class QuestRegistry
{
    static
    {
        make(new ResourceLocation(Main.MODID, "quests"), Quest.class);
    }

    public static final IForgeRegistry<Quest> QUESTS = RegistryManager.ACTIVE.getRegistry(Quest.class);

    public static <T extends IForgeRegistryEntry<T>> void make(ResourceLocation name, Class<T> type)
    {
        new RegistryBuilder<T>().setName(name).setType(type).setMaxID(Integer.MAX_VALUE - 1).create();
    }
}

