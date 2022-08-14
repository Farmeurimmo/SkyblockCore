package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LevelAdvManager {

    public static final double exp_multiplier = 2.25;
    public static final double exp_base_for_level_0 = 500;
    public static final double exp_gained = 10;
    public static final double exp_multiplier_in_dungeon = 5;
    public static LevelAdvManager instance;
    public static HashMap<Material, Double> matPer = new HashMap<>();

    public LevelAdvManager() {
        instance = this;

        matPer.put(Material.WHEAT, 0.2);
        matPer.put(Material.CARROTS, 0.2);
        matPer.put(Material.POTATOES, 0.2);
        matPer.put(Material.BEETROOT, 0.2);
        matPer.put(Material.NETHER_WART, 0.2);
        matPer.put(Material.COCOA, 0.2);
        matPer.put(Material.SUGAR_CANE, 0.2);
        matPer.put(Material.CACTUS, 0.2);

        matPer.put(Material.COBBLESTONE, 0.1);
        matPer.put(Material.COAL_ORE, 0.3);
        matPer.put(Material.IRON_ORE, 0.6);
        matPer.put(Material.GOLD_ORE, 0.6);
        matPer.put(Material.LAPIS_ORE, 0.5);
        matPer.put(Material.DIAMOND_ORE, 0.8);
        matPer.put(Material.EMERALD_ORE, 0.9);
        matPer.put(Material.ANCIENT_DEBRIS, 1.0);

    }

    public Double getPlayerLevel(SkyblockUser skyblockUser) {
        return skyblockUser.getLevel();
    }

    public Double getPlayerExp(SkyblockUser skyblockUser) {
        return skyblockUser.getExp();
    }

    public Double expToGetForNextLevel(final Double level) {
        return exp_base_for_level_0 * (level + 1) * exp_multiplier;
    }

    public void setLevel(SkyblockUser skyblockUser, Double level) {
        skyblockUser.setLevel(level);
    }

    public String getLevelFormatted(SkyblockUser skyblockUser) {
        return NumberFormat.getInstance().format(getPlayerLevel(skyblockUser));
    }

    public String getExpFormatted(SkyblockUser skyblockUser) {
        return NumberFormat.getInstance().format(getPlayerExp(skyblockUser));
    }

    public String getExpToGetForNextLevelFormatted(Double level) {
        return NumberFormat.getInstance().format(expToGetForNextLevel(level));
    }

    public void addExpToPlayer(Player player, Double exp) {
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player);
        skyblockUser.addExp(exp);
        player.sendActionBar("§a+" + exp + " §6exp §7(§a" + getExpFormatted(skyblockUser) + "§7/§c" +
                (getExpToGetForNextLevelFormatted(skyblockUser.getLevel())) + "§7)");
        checkForLevelUp(skyblockUser, player);
    }

    public void checkForLevelUp(SkyblockUser skyblockUser, Player player) {
        double expForNextLevel = expToGetForNextLevel(skyblockUser.getLevel());
        if (expForNextLevel <= skyblockUser.getExp()) {
            skyblockUser.removeExp(expForNextLevel);
            skyblockUser.incrementLevel();
            player.sendActionBar("§a§lLevel up! §7(§a" + getLevelFormatted(skyblockUser) + "§7)");
            player.sendTitle("§aLevel up", "§6Prochain niveau " + getLevelFormatted(skyblockUser));
        }
    }

    public void blockEvent(Block block, Player player) {
        if (!matPer.containsKey(block.getType())) return;
        if (block.hasMetadata("placed")) {
            final Ageable ageable = (Ageable) block.getState().getBlockData();
            int age = ageable.getAge();
            if (age != ageable.getMaximumAge()) {
                return;
            }
        }
        Double value = matPer.get(block.getType());
        if (value == 1) {
            addExpToPlayer(player, exp_gained);
            return;
        }
        if (new Random().nextDouble() >= value) return;
        addExpToPlayer(player, exp_gained);
    }

    public void entityEvent(Player player, Double exp) {
        addExpToPlayer(player, (exp > 0.0 ? exp : exp_gained));
    }

    public void dungeonEnd(Player player, Double exp) {
        addExpToPlayer(player, (exp > 0.0 ? exp : exp_gained));
    }

    public List<String> getRewardForNextLevel(Double level) {
        return null;
    }
}
