package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import main.java.fr.verymc.spigot.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class LevelAdvGuis implements Listener {

    public static LevelAdvGuis instance;
    HashMap<UUID, Integer> openedRewards = new HashMap<>();

    public LevelAdvGuis() {
        instance = this;
    }

    public void openPlayerMainLevel(Player player) {
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player);

        Inventory inv = Bukkit.createInventory(null, 27, "§6Niveau d'aventure");

        ItemStack playerInfo = PreGenItems.instance.getHead(player);
        playerInfo.setDisplayName("§6" + player.getName());
        playerInfo.setLore(Arrays.asList("§7Niveau: §6" + LevelAdvManager.instance.getLevelFormatted(skyblockUser), "§7Expérience: §a" +
                LevelAdvManager.instance.getExpFormatted(skyblockUser) + "§7/§c" +
                LevelAdvManager.instance.getExpToGetForNextLevelFormatted(skyblockUser.getLevel())));
        inv.setItem(10, playerInfo);

        ItemStack nextReward = new ItemStack(Material.GOLD_BLOCK);
        nextReward.setDisplayName("§6Récompense(s) du prochain niveau");
        LevelAdvReward levelAdvReward = LevelAdvRewardManager.instance.getReward(skyblockUser.getLevel() + 1);
        try {
            nextReward.setLore(LevelAdvRewardManager.instance.getLoreForReward(levelAdvReward));
        } catch (Exception e) {
            nextReward.setLore(Arrays.asList(""));
        }
        inv.setItem(12, nextReward);

        ItemStack rewardsGui = new ItemStack(Material.CHEST);
        rewardsGui.setDisplayName("§6Récompenses");
        inv.setItem(14, rewardsGui);

        ItemStack help = new ItemStack(Material.BOOK);
        help.setDisplayName("§6Aide");
        help.setLore(Arrays.asList("§7SOON"));
        inv.setItem(16, help);

        player.openInventory(inv);
    }

    public ArrayList<Integer> getSlotToFill() {
        return new ArrayList<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31,
                32, 33, 34, 37, 38, 39, 40, 41, 42, 43));
    }

    public void openPlayerRewardAtPage(Player player, boolean pageAtCurrentLvl, int pageAdd) {
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player);
        HashMap<Integer, Inventory> invs = new HashMap<>();
        double currentLevel = 0;
        int pageCurrentLvl = 0;
        int currentPage = 0;
        boolean stop = false;
        while (true) {
            ArrayList<Integer> slots = getSlotToFill();
            Inventory inv = Bukkit.createInventory(null, 54, "§6Récompenses des niveaux " + LevelAdvManager.instance.formatDouble(currentLevel)
                    + "-" + LevelAdvManager.instance.formatDouble(currentLevel + getSlotToFill().size() - 1));

            ItemStack itemStack = new ItemStack(Material.AIR);
            for (Integer integer : slots) {
                if (skyblockUser.getLevel() > currentLevel) {
                    itemStack.setType(Material.GREEN_STAINED_GLASS_PANE);
                    itemStack.setDisplayName("§a" + LevelAdvManager.instance.formatDouble(currentLevel));
                } else if (skyblockUser.getLevel() == currentLevel) {
                    itemStack.setType(Material.YELLOW_STAINED_GLASS_PANE);
                    itemStack.setDisplayName("§e" + LevelAdvManager.instance.formatDouble(currentLevel));
                    pageCurrentLvl = currentPage;
                    stop = true;
                } else {
                    itemStack.setType(Material.RED_STAINED_GLASS_PANE);
                    itemStack.setDisplayName("§c" + LevelAdvManager.instance.formatDouble(currentLevel));
                }
                LevelAdvReward levelAdvReward = LevelAdvRewardManager.instance.getReward(currentLevel);
                if (levelAdvReward != null) {
                    itemStack.setLore(LevelAdvRewardManager.instance.getLoreForReward(levelAdvReward));
                    String colorAdd = (levelAdvReward.isMajorReward() ? "§l" : "") + (levelAdvReward.isSubReward() ? "§n" : "");
                    itemStack.setDisplayName(itemStack.getDisplayName().replace("§a", "§a" + colorAdd)
                            .replace("§e", "§e" + colorAdd).replace("§c", "§c" + colorAdd));
                }
                inv.setItem(integer, itemStack);
                currentLevel++;
            }

            ItemStack next = new ItemStack(Material.ARROW);
            next.setDisplayName("§6Page suivante");

            ItemStack previous = new ItemStack(Material.ARROW);
            previous.setDisplayName("§6Page précédente");

            ItemStack current = new ItemStack(Material.PAPER);
            current.setDisplayName("§6Page avec votre niveau");

            inv.setItem(53, next);
            if (currentPage > 0) inv.setItem(45, previous);
            inv.setItem(49, current);

            invs.put(currentPage, inv);

            if (pageAtCurrentLvl && stop) break;
            if (!pageAtCurrentLvl && pageCurrentLvl + currentPage == pageAdd) break;

            currentPage++;
        }

        if (pageAtCurrentLvl) {
            player.openInventory(invs.get(pageCurrentLvl));
            openedRewards.put(player.getUniqueId(), pageCurrentLvl);
        } else {
            if (invs.containsKey(currentPage)) {
                player.openInventory(invs.get(currentPage));
                openedRewards.put(player.getUniqueId(), currentPage);
            } else {
                player.openInventory(invs.get(0));
                openedRewards.put(player.getUniqueId(), 0);
            }
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("§6Niveau d'aventure")) {
            e.setCancelled(true);
            ItemStack current = e.getCurrentItem();
            if (current == null) return;
            if (current.getType() == Material.CHEST) {
                openPlayerRewardAtPage((Player) e.getWhoClicked(), true, 5);
                return;
            }
            return;
        }
        if (e.getView().getTitle().contains("§6Récompenses des niveaux ")) {
            e.setCancelled(true);
            ItemStack current = e.getCurrentItem();
            if (current == null) return;
            Player player = (Player) e.getWhoClicked();
            if (current.getType() == Material.PAPER) {
                openPlayerRewardAtPage(player, true, 0);
                return;
            }
            if (current.getType() != Material.ARROW) return;
            if (e.getRawSlot() == 53) {
                if (openedRewards.containsKey(player.getUniqueId())) {
                    int page = openedRewards.get(player.getUniqueId());
                    openPlayerRewardAtPage(player, false, page + 1);
                } else {
                    openPlayerRewardAtPage(player, true, 0);
                }
                return;
            }
            if (e.getRawSlot() == 45) {
                if (openedRewards.containsKey(player.getUniqueId())) {
                    int page = openedRewards.get(player.getUniqueId());
                    openPlayerRewardAtPage(player, false, page - 1);
                } else {
                    openPlayerRewardAtPage(player, true, 0);
                }
                return;
            }
            return;
        }
    }
}
