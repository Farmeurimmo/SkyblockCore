package main.java.fr.verymc.island.challenges;

import org.bukkit.Material;

public class IslandChallenge {

    private String name;
    private int progress;
    private Material material;
    private int palier;
    private int id;
    private boolean isActive;
    private int maxProgress;

    public IslandChallenge(String name, int progress, Material material, int palier, int id, boolean isActive, int maxProgress) {
        this.name = name;
        this.progress = progress;
        this.material = material;
        this.palier = palier;
        this.id = id;
        this.isActive = isActive;
        this.maxProgress = maxProgress;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void addProgress(int progress) {
        this.progress += progress;
    }

    public int getPalier() {
        return this.palier;
    }

    public void setPalier(int palier) {
        this.palier = palier;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getMaxProgress() {
        return this.maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

}
