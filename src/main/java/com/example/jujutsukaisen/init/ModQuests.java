package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.QuestRegistry;
import com.example.jujutsukaisen.quest.cursed_punches.CursedPunches_01;
import com.example.jujutsukaisen.quest.cursed_punches.CursedPunches_02;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModQuests {

    private static HashMap<String, String> langMap = new HashMap<String, String>();

    public static HashMap<String, String> getLangMap()
    {
        return langMap;
    }
    public static final DeferredRegister<Quest> QUESTS = DeferredRegister.create(QuestRegistry.QUESTS, Main.MODID);

    public static final Quest CURSED_PUNCHES_01 = new CursedPunches_01();
    public static final Quest CURSED_PUNCHES_02 = new CursedPunches_02();
    public static final Quest[] UNLOCK_TRIAL = new Quest[] {CURSED_PUNCHES_01, CURSED_PUNCHES_02};

    static
    {
        for (Quest quest : UNLOCK_TRIAL)
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
