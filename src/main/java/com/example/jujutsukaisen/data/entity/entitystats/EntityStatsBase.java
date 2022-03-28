package com.example.jujutsukaisen.data.entity.entitystats;

public class EntityStatsBase implements IEntityStats{

    private String clan = "";
    private String technique = "";
    private int level;
    private int experience;
    private int maxExperience;
    private int alterMaxExperience;

    @Override
    public boolean hasCursedEnergy() {
        return false;
    }

    @Override
    public boolean hasCursedEnergyUnlocked() {
        return false;
    }

    @Override
    public boolean hasClan() {
        return false;
    }

    @Override
    public void setClan(String value) {

    }

    @Override
    public String getClan() {
        return null;
    }

    @Override
    public boolean hasTechnique() {
        return false;
    }

    @Override
    public void setTechnique(String value) {

    }

    @Override
    public String getTechnique() {
        return null;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void alterLevel(int value) {

    }

    @Override
    public void setLevel(int value) {

    }

    @Override
    public int getExperience() {
        return 0;
    }

    @Override
    public void alterExperience(int value) {

    }

    @Override
    public void setExperience(int value) {

    }

    @Override
    public int getMaxExperience() {
        return 0;
    }

    @Override
    public void alterMaxExperience(int value) {

    }

    @Override
    public void setMaxExperience(int value) {

    }
}
