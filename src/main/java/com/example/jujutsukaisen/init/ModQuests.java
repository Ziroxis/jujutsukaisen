package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.QuestRegistry;
import com.example.jujutsukaisen.quest.cursed_punches.CursedPunches_01;
import com.example.jujutsukaisen.quest.cursed_punches.CursedPunches_02;
import com.example.jujutsukaisen.quest.cursed_sword.CursedSword_01;
import com.example.jujutsukaisen.quest.cursed_sword.CursedSword_02;
import com.example.jujutsukaisen.quest.obtain_sword.ObtainSword_01;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;

public class ModQuests {

    private static HashMap<String, String> langMap = new HashMap<String, String>();

    public static HashMap<String, String> getLangMap()
    {
        return langMap;
    }
    public static final DeferredRegister<Quest> QUESTS = DeferredRegister.create(QuestRegistry.QUESTS, Main.MODID);

    public static final Quest OBTAIN_SWORD_01 = new ObtainSword_01();
    public static final Quest[] OBTAIN_SWORD = new Quest[] {OBTAIN_SWORD_01};

    public static final Quest CURSED_SWORD_01 = new CursedSword_01();
    public static final Quest CURSED_SWORD_02 = new CursedSword_02();
    public static final Quest[] UNLOCK_SWORD = new Quest[] {CURSED_SWORD_01, CURSED_SWORD_02};

    public static final Quest CURSED_PUNCHES_01 = new CursedPunches_01();
    public static final Quest CURSED_PUNCHES_02 = new CursedPunches_02();
    public static final Quest[] UNLOCK_PUNCHES = new Quest[] {CURSED_PUNCHES_01, CURSED_PUNCHES_02};

    static
    {
        for (Quest quest : OBTAIN_SWORD)
        {
            String resourceName = Beapi.getResourceName(quest.getId());
            langMap.put("quest." + Main.MODID + "." + resourceName, quest.getTitle());

            for(Objective obj : quest.getObjectives())
            {
                langMap.put("quest.objective." + Main.MODID + "." + obj.getId(), obj.getTitle());
            }

            QUESTS.register(resourceName, () -> quest);
        }
        for (Quest quest : UNLOCK_PUNCHES)
        {
            String resourceName = Beapi.getResourceName(quest.getId());
            langMap.put("quest." + Main.MODID + "." + resourceName, quest.getTitle());

            for(Objective obj : quest.getObjectives())
            {
                langMap.put("quest.objective." + Main.MODID + "." + obj.getId(), obj.getTitle());
            }

            QUESTS.register(resourceName, () -> quest);
        }
        for (Quest quest : UNLOCK_SWORD)
        {
            String resourceName = Beapi.getResourceName(quest.getId());
            langMap.put("quest." + Main.MODID + "." + resourceName, quest.getTitle());

            for(Objective obj : quest.getObjectives())
            {
                langMap.put("quest.objective." + Main.MODID + "." + obj.getId(), obj.getTitle());
            }

            QUESTS.register(resourceName, () -> quest);
        }
    }

}
