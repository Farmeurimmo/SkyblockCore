package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelAdvRewardManager {

    public static LevelAdvRewardManager instance;
    public ArrayList<LevelAdvReward> rewards = new ArrayList<>();

    public LevelAdvRewardManager() {
        instance = this;

        genRewards();
    }

    //level is between 0 and inf+
    public LevelAdvReward getReward(double level) {
        for (LevelAdvReward reward : rewards) {
            if (reward.getLevel() == level) {
                return reward;
            }
        }
        return null;
    }

    public void giveReward(Player player, double level) {
        LevelAdvReward reward = getReward(level);
        Island island = IslandManager.instance.getPlayerIsland(player);
        if (reward != null) {
            island.getBank().addCrystaux(reward.getCrys());
            if (reward.getItemStacks() != null) {
                for (ItemStack itemStack : reward.getItemStacks()) {
                    if (InventoryUtils.instance.checkPlayerInventoryForSlot(player)) {
                        player.getInventory().addItem(itemStack);
                    } else {
                        player.getWorld().dropItem(player.getLocation(), itemStack);
                    }
                }
            }
            if (reward.getToDo() != null) {
                for (String toDo : reward.getToDo()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set " + toDo);
                }
            }
        }
    }

    public List<String> getLoreForReward(LevelAdvReward levelAdvReward) {
        ArrayList<ItemStack> itemStacks = (levelAdvReward.getItemStacks() != null ? levelAdvReward.getItemStacks() : null);
        ArrayList<String> toDo = (levelAdvReward.getToDo() != null ? levelAdvReward.getToDo() : null);
        return Arrays.asList("§fCrystaux: §6" + levelAdvReward.getCrys(), (itemStacks != null ? "§fItem(s): " +
                        itemStacks.toString().replace("[", "").replace("]", "").replace(",", "") : "§7Aucun item"),
                (toDo != null ? "§fPermission(s): §6" + toDo.toString().replace("[", "").replace("]", "").
                        replace(",", "") : "§7Aucune permission"));
    }

    public ArrayList<ItemStack> getSpecialRewardForLevel(double level) {
        if (level == 10) {
            return new ArrayList<>();
        }
        return null;
    }

    public ArrayList<String> getToDoForLevel(double level) {
        if (level == 5) {
            return null;
        }
        if (level == 10) {
            return null;
        }
        if (level == 15) {
            return null;
        }
        if (level == 20) {
            return null;
        }
        if (level == 25) {
            return null;
        }
        return null;
    }

    public void genRewards() {
        for (int i = 0; i < 250; i++) {
            int crys = (int) ((i == 0 ? 1 : i) * 1.2);
            boolean isMajor = i % 10 == 0;
            boolean isSub = i % 5 == 0;
            ArrayList<ItemStack> itemStacks = getSpecialRewardForLevel(i);
            ArrayList<String> toDo = getToDoForLevel(i);
            rewards.add(new LevelAdvReward(i, itemStacks, crys, isSub, isMajor, toDo));
        }
    }
}
