package com.example.jujutsukaisen.data.entity.entitystats;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.init.ModValues;

public class EntityStatsBase implements IEntityStats{

    private boolean inCombatMode = false;
    private String clan = "";
    private String technique = "";
    private String grade = "";
    private String curse = "";
    private String restriction = "";
    private int level;
    private int experience;
    private int maxExperience;
    private int cursedEnergy;
    private int maxCursedEnergy;
    private int alterMaxExperience;
    private double damageMultiplier = 1;


    @Override
    public boolean isInCombatMode() {
        return this.inCombatMode;
    }

    @Override
    public void setCombatMode(boolean value) {
        this.inCombatMode = value;
    }

    @Override
    public boolean isCurse() {
        return !this.curse.equals(ModValues.HUMAN);
    }

    @Override
    public void setCurse(String value) {
        this.curse = value;
    }

    @Override
    public String getCurse() {
        return this.curse;
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
    public boolean hasRestriction() {
        if (Beapi.isNullOrEmpty(this.restriction))
            return false;

        return true;
    }

    @Override
    public void setRestriction(String value) {
        this.restriction = value;
    }

    @Override
    public String getRestriction() {
        return this.restriction;
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


    @Override
    public void alterCursedEnergy(int value) {
        this.cursedEnergy += value;

        if (this.cursedEnergy > this.maxCursedEnergy)
            this.cursedEnergy = this.maxCursedEnergy;

        if (this.cursedEnergy < 0)
            this.cursedEnergy = 0;
    }

    @Override
    public void setCursedEnergy(int value) {
        this.cursedEnergy = value;
    }

    @Override
    public void setMaxCursedEnergy(int value) {
        this.maxCursedEnergy = value;
    }

    @Override
    public int getMaxCursedEnergy() {
        return this.maxCursedEnergy;
    }

    @Override
    public int returnCursedEnergy() {
        return this.cursedEnergy;
    }

    @Override
    public void setTechnique(String value)
    {
        this.technique = value;
    }

    @Override
    public String getTechnique()
    {
        return this.technique;
    }

    @Override
    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    @Override
    public void setDamageMultiplier(double multiplier) {
        this.damageMultiplier = multiplier;
    }
}
