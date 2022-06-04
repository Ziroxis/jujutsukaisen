package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.QuestRegistry;
import com.example.jujutsukaisen.quest.cursed_punches.continuous_punch_questline.ContinuousPunch_01;
import com.example.jujutsukaisen.quest.cursed_punches.continuous_punch_questline.ContinuousPunch_02;
import com.example.jujutsukaisen.quest.cursed_punches.cursed_punch_questline.CursedPunch_01;
import com.example.jujutsukaisen.quest.cursed_punches.divergent_fist_questline.DivergentFist_01;
import com.example.jujutsukaisen.quest.cursed_sword.batto_sword_questline.BattoSword_01;
import com.example.jujutsukaisen.quest.cursed_sword.continuous_sword_questline.ContinuousSword_01;
import com.example.jujutsukaisen.quest.cursed_sword.continuous_sword_questline.ContinuousSword_02;
import com.example.jujutsukaisen.quest.cursed_sword.evening_moon_questline.EveningMoon_01;
import com.example.jujutsukaisen.quest.obtain_sword.ObtainSword_01;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;

public class ModQuests {

    private static HashMap<String, String> langMap = new HashMap<String, String>();

    public static HashMap<String, String> getLangMap()
    {
        return langMap;
    }
    public static final DeferredRegister<Quest> QUESTS = DeferredRegister.create(QuestRegistry.QUESTS, Main.MODID);

    public static final Quest BATTO_SWORD_01 = new BattoSword_01();
    public static final Quest[] BATTO_SWORD = new Quest[] {BATTO_SWORD_01};
    public static final Quest EVENING_MOON_01 = new EveningMoon_01();
    public static final Quest[] EVENING_MOON = new Quest[] {EVENING_MOON_01};

    public static final Quest CURSED_PUNCH_01 = new CursedPunch_01();
    public static final Quest[] CURSED_PUNCH = new Quest[] {CURSED_PUNCH_01};
    public static final Quest DIVERGENT_FIST_01 = new DivergentFist_01();
    public static final Quest[] DIVERGENT_FIST = new Quest[] {DIVERGENT_FIST_01};


    public static final Quest OBTAIN_SWORD_01 = new ObtainSword_01();
    public static final Quest[] OBTAIN_SWORD = new Quest[] {OBTAIN_SWORD_01};

    public static final Quest CURSED_SWORD_01 = new ContinuousSword_01();
    public static final Quest CURSED_SWORD_02 = new ContinuousSword_02();
    public static final Quest[] UNLOCK_SWORD = new Quest[] {CURSED_SWORD_01, CURSED_SWORD_02};

    public static final Quest CURSED_PUNCHES_01 = new ContinuousPunch_01();
    public static final Quest CURSED_PUNCHES_02 = new ContinuousPunch_02();
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
        for (Quest quest : BATTO_SWORD)
        {
            String resourceName = Beapi.getResourceName(quest.getId());
            langMap.put("quest." + Main.MODID + "." + resourceName, quest.getTitle());

            for(Objective obj : quest.getObjectives())
            {
                langMap.put("quest.objective." + Main.MODID + "." + obj.getId(), obj.getTitle());
            }

            QUESTS.register(resourceName, () -> quest);
        }
        for (Quest quest : EVENING_MOON)
        {
            String resourceName = Beapi.getResourceName(quest.getId());
            langMap.put("quest." + Main.MODID + "." + resourceName, quest.getTitle());

            for(Objective obj : quest.getObjectives())
            {
                langMap.put("quest.objective." + Main.MODID + "." + obj.getId(), obj.getTitle());
            }

            QUESTS.register(resourceName, () -> quest);
        }
        for (Quest quest : CURSED_PUNCH)
        {
            String resourceName = Beapi.getResourceName(quest.getId());
            langMap.put("quest." + Main.MODID + "." + resourceName, quest.getTitle());

            for(Objective obj : quest.getObjectives())
            {
                langMap.put("quest.objective." + Main.MODID + "." + obj.getId(), obj.getTitle());
            }

            QUESTS.register(resourceName, () -> quest);
        }
        for (Quest quest : DIVERGENT_FIST)
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
