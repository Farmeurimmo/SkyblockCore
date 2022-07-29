package main.java.fr.verymc.island.challenges;

import main.java.fr.verymc.utils.ObjectConverter;
import org.bukkit.Material;

import java.util.ArrayList;

public class IslandChallenge {

    private String name;
    private int progress;
    private Material material;
    private int palier;
    private int id;
    private boolean isActive;
    private int maxProgress;
    private int type;
    private ArrayList<Material> toGet;

    public IslandChallenge(String name, int progress, Material material, int palier, int id, boolean isActive,
                           int maxProgress, int type, ArrayList<Material> toGet) {
        this.name = name;
        this.progress = progress;
        this.material = material;
        this.palier = palier;
        this.id = id;
        this.isActive = isActive;
        this.maxProgress = maxProgress;
        this.type = type;
        this.toGet = toGet;
    }

    public static String toString(IslandChallenge isC) {
        return isC.getName() + ObjectConverter.SEPARATOR + isC.getProgress() + ObjectConverter.SEPARATOR + isC.getMaterial().toString() + ObjectConverter.SEPARATOR +
                isC.getPalier() + ObjectConverter.SEPARATOR + isC.getId() + ObjectConverter.SEPARATOR + isC.isActive() + ObjectConverter.SEPARATOR + isC.getMaxProgress()
                + ObjectConverter.SEPARATOR + isC.getType() + ObjectConverter.SEPARATOR + (isC.getToGet() == null ? new ArrayList<>() : isC.getToGet().toString());
    }

    public static IslandChallenge fromString(String string) {
        String[] splited = string.split(ObjectConverter.SEPARATOR);
        String name = splited[0];
        int progress = Integer.parseInt(splited[1]);
        Material material = Material.valueOf(splited[2]);
        int palier = Integer.parseInt(splited[3]);
        int id = Integer.parseInt(splited[4]);
        boolean isActive = Boolean.parseBoolean(splited[5]);
        int maxProgress = Integer.parseInt(splited[6]);
        int type = Integer.parseInt(splited[7]);
        ArrayList<Material> toGet = new ArrayList<>();
        for (String str : splited[8].replace("{", "").replace("}", "").split(",")) {
            toGet.add(Material.valueOf(str));
        }
        return new IslandChallenge(name, progress, material, palier, id, isActive, maxProgress, type, toGet);
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Material> getToGet() {
        return this.toGet;
    }

    public void setToGet(ArrayList<Material> toGet) {
        this.toGet = toGet;
    }

    public void addToGet(Material material) {
        this.toGet.add(material);
    }

}
