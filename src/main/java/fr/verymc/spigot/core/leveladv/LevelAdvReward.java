package main.java.fr.verymc.spigot.core.leveladv;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class LevelAdvReward {

    private double level;
    private ArrayList<ItemStack> itemStacks;
    private int crys;
    private boolean subReward; //level = 5, 15 25 etc
    private boolean majorReward; //level = 10, 20, 30 etc
    private ArrayList<String> toDo;

    public LevelAdvReward(double level, ArrayList<ItemStack> itemStacks, int crys, boolean subReward, boolean majorReward, ArrayList<String> toDo) {
        this.level = level;
        this.itemStacks = itemStacks;
        this.crys = crys;
        this.subReward = subReward;
        this.majorReward = majorReward;
        this.toDo = toDo;
    }

    public double getLevel() {
        return level;
    }

    public ArrayList<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public boolean isSubReward() {
        return subReward;
    }

    public boolean isMajorReward() {
        return majorReward;
    }

    public ArrayList<String> getToDo() {
        return toDo;
    }

    public int getCrys() {
        return crys;
    }
}
