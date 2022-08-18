package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.core.gui.MenuGui;
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
        playerInfo.setLore(Arrays.asList("§7Niveau: §6" + LevelAdvManager.instance.getLevelFormatted(skyblockUser)));
        inv.setItem(10, playerInfo);

        ItemStack nextReward = new ItemStack(Material.GOLD_BLOCK);
        nextReward.setDisplayName("§6Prochain niveau (§e" + LevelAdvManager.instance.formatDouble(skyblockUser.getLevel() + 1) + "§6)");
        LevelAdvReward levelAdvReward = LevelAdvRewardManager.instance.getReward(skyblockUser.getLevel() + 1);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Expérience: §a" + skyblockUser.getExp() + "§7/§c" + LevelAdvManager.instance.getExpToGetForNextLevelFormatted(skyblockUser.getLevel()));
        lore.add("§7Progression: §a" + LevelAdvManager.instance.formatDouble(LevelAdvManager.instance.getLevelPercentage(skyblockUser.getExp(),
                LevelAdvManager.instance.expToGetForNextLevel(skyblockUser.getLevel()))) + "%");
        lore.add("");
        lore.add("§7Récompenses: ");
        try {
            lore.addAll(LevelAdvRewardManager.instance.getLoreForReward(levelAdvReward));
        } catch (Exception e) {
        }
        nextReward.setLore(lore);
        inv.setItem(12, nextReward);

        ItemStack rewardsGui = new ItemStack(Material.CHEST);
        rewardsGui.setDisplayName("§6Niveaux d'aventure");
        inv.setItem(14, rewardsGui);

        ItemStack help = new ItemStack(Material.BOOK);
        help.setDisplayName("§6Aide");
        help.setLore(Arrays.asList("§7SOON"));
        inv.setItem(16, help);

        ItemStack arrow = new ItemStack(Material.ARROW);
        arrow.setDisplayName("§6Retour au menu principal §7(/menu)");
        inv.setItem(26, arrow);

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

        ItemStack home = new ItemStack(Material.IRON_DOOR);
        home.setDisplayName("§6Retour au menu §7(/level)");

        ItemStack next = new ItemStack(Material.ARROW);
        next.setDisplayName("§6Page suivante");

        ItemStack previous = new ItemStack(Material.ARROW);
        previous.setDisplayName("§6Page précédente");

        ItemStack current = new ItemStack(Material.PAPER);
        current.setDisplayName("§6Page avec votre niveau");

        while (true) {
            ArrayList<Integer> slots = getSlotToFill();
            Inventory inv = Bukkit.createInventory(null, 54, "§6Niveaux d'aventure " + LevelAdvManager.instance.formatDouble(currentLevel)
                    + "-" + LevelAdvManager.instance.formatDouble(currentLevel + getSlotToFill().size() - 1));

            ItemStack itemStack = new ItemStack(Material.AIR);
            for (Integer integer : slots) {
                if (skyblockUser.getLevel() + 1 > currentLevel) {
                    itemStack.setType(Material.GREEN_STAINED_GLASS_PANE);
                    itemStack.setDisplayName("§a" + LevelAdvManager.instance.formatDouble(currentLevel));
                } else if (skyblockUser.getLevel() + 1 == currentLevel) {
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
                    ArrayList<String> lore = new ArrayList<>();
                    if (levelAdvReward.getLevel() > skyblockUser.getLevel()) {
                        lore.add("§7Expérience manquante: §c" + LevelAdvManager.instance.getExpForLevelFormatted(skyblockUser.getLevel(), currentLevel, skyblockUser.getExp()));
                    }
                    lore.add("§7Expérience du niveau: §6" + LevelAdvManager.instance.getExpToGetForNextLevelFormatted(currentLevel));
                    lore.add("");
                    lore.addAll(LevelAdvRewardManager.instance.getLoreForReward(levelAdvReward));
                    itemStack.setLore(lore);
                    String colorAdd = (levelAdvReward.isMajorReward() ? "§l" : "") + (levelAdvReward.isSubReward() ? "§n" : "");
                    itemStack.setDisplayName(itemStack.getDisplayName().replace("§a", "§a" + colorAdd)
                            .replace("§e", "§e" + colorAdd).replace("§c", "§c" + colorAdd));
                }
                inv.setItem(integer, itemStack);
                currentLevel++;
            }

            inv.setItem(53, next);
            if (currentPage > 0) inv.setItem(45, previous);
            inv.setItem(48, current);

            inv.setItem(50, home);

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
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu((Player) e.getWhoClicked());
                return;
            }
            if (current.getType() == Material.CHEST) {
                openPlayerRewardAtPage((Player) e.getWhoClicked(), true, 5);
                return;
            }
            return;
        }
        if (e.getView().getTitle().contains("§6Niveaux d'aventure ")) {
            e.setCancelled(true);
            ItemStack current = e.getCurrentItem();
            if (current == null) return;
            Player player = (Player) e.getWhoClicked();
            if (current.getType() == Material.PAPER) {
                openPlayerRewardAtPage(player, true, 0);
                return;
            }
            if (current.getType() == Material.IRON_DOOR) {
                openPlayerMainLevel(player);
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
