package com.example.jujutsukaisen.data.entity.entitystats;

public interface IEntityStats {

    //Things related with the cursed energy unlock
    boolean hasCursedEnergy();
    boolean hasCursedEnergyUnlocked();

    //Things related with the clan
    boolean hasClan();
    void setClan(String value);
    String getClan();

    //Things related with the technique
    boolean hasTechnique();
    void setTechnique(String value);
    String getTechnique();

    //Things related with the levels
    int getLevel();
    void alterLevel(int value);
    void setLevel(int value);

    //Things related with the levels
    int getExperience();
    void alterExperience(int value);
    void setExperience(int value);

    //Things related with leveling up
    int getMaxExperience();
    void alterMaxExperience(int value);
    void setMaxExperience(int value);
}
