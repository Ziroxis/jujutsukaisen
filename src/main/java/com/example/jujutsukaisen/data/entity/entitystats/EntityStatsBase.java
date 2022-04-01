package com.example.jujutsukaisen.data.entity.entitystats;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.init.ModValues;

public class EntityStatsBase implements IEntityStats{

    private boolean inCombatMode = false;
    private String clan = "";
    private String technique = "";
    private String grade = "";
    private int level;
    private int experience;
    private int maxExperience;
    private int alterMaxExperience;


    @Override
    public boolean isInCombatMode() {
        return this.inCombatMode;
    }

    @Override
    public void setCombatMode(boolean value) {
        this.inCombatMode = value;
    }

    @Override
    public boolean hasCursedEnergy() {
        return true;
    }

    @Override
    public boolean hasCursedEnergyUnlocked() {
        if (this.getCurseGrade().equals("0"))
            return false;

        return true;
    }

    @Override
    public boolean hasClan() {
        if (Beapi.isNullOrEmpty(this.clan))
            return false;

        return true;
    }



    @Override
    public void setClan(String value) {
        this.clan = value;
    }

    @Override
    public String getClan() {
        return this.clan;
    }

    @Override
    public boolean isKamo() {
        if (Beapi.isNullOrEmpty(this.clan))
            return false;

        return this.clan.equalsIgnoreCase(ModValues.Kamo);
    }

    @Override
    public boolean isGojo() {
        if (Beapi.isNullOrEmpty(this.clan))
            return false;

        return this.clan.equalsIgnoreCase(ModValues.Gojo);
    }

    @Override
    public boolean isZenin() {
        if (Beapi.isNullOrEmpty(this.clan))
            return false;

        return this.clan.equalsIgnoreCase(ModValues.Zenin);
    }

    @Override
    public boolean isInumaki() {
        if (Beapi.isNullOrEmpty(this.clan))
            return false;

        return this.clan.equalsIgnoreCase(ModValues.Inumaki);
    }

    @Override
    public boolean isSuguru() {
        if (Beapi.isNullOrEmpty(this.clan))
            return false;

        return this.clan.equalsIgnoreCase(ModValues.Suguru);
    }




    @Override
    public boolean hasCurseGrade() {
        if (Beapi.isNullOrEmpty(this.grade))
            return false;

        return true;
    }

    @Override
    public void setCurseGrade(String value) {
        this.grade = value;
    }

    @Override
    public String getCurseGrade() {
        return this.grade;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void alterLevel(int value) {
        this.level = (this.level + value);
    }

    @Override
    public void setLevel(int value) {
        this.level = value;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    @Override
    public void alterExperience(int value) {
        this.experience = (this.experience + value);
    }

    @Override
    public void setExperience(int value) {
        this.experience = value;
    }

    @Override
    public int getMaxExperience() {
        return this.maxExperience;
    }

    @Override
    public void alterMaxExperience(int value) {
        this.maxExperience = (this.maxExperience + value);
    }

    @Override
    public void setMaxExperience(int value) {
        this.maxExperience = value;
    }
}
