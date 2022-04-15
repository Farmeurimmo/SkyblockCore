package main.java.fr.verymc.island.challenges;

import main.java.fr.verymc.crates.CratesKeyManager;
import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class IslandChallengesGuis implements Listener {

    public static int boost = 1;

    public static void CompleteChallenge(Player player, IslandChallenge challenge) {
        IslandManager.instance.getPlayerIsland(player).getBank().addCrystaux(5.0 * boost);
        IslandManager.instance.getPlayerIsland(player).getBank().addMoney(5000.0 * boost);
        CratesKeyManager.GiveCrateKey(player, 1 * boost, "Challenge");

        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
        player.sendMessage("§6§lChallenges §8» §fVous venez de compléter le challenge journalier n°§6" + challenge.getId() +
                " §fau palier " + challenge.getPalier() + "/5.");
        player.sendMessage("§6§lChallenges §8» §fVous avez reçu " + 2 * boost + "crystaux, " + 5000 * boost + "$ et x" + 1 * boost + " Clée Challenge.");
    }

    public static void MakeMainGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Challenges");

        ItemStack custom1 = new ItemStack(Material.CLOCK, 1);
        ItemMeta customa = custom1.getItemMeta();
        customa.setDisplayName("§6Challenges journaliers");
        custom1.setItemMeta(customa);
        inv.setItem(11, custom1);

        ItemStack custom2 = new ItemStack(Material.BUCKET, 1);
        ItemMeta customb = custom2.getItemMeta();
        customb.setDisplayName("§6Challenges normaux");
        custom2.setItemMeta(customb);
        inv.setItem(15, custom2);


        ItemStack custom9 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom9.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom9.setItemMeta(customh);
        inv.setItem(26, custom9);

        player.openInventory(inv);
    }

    public static void MakeDailyGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Challenges journaliers");

        Island playerIsland = IslandManager.instance.getPlayerIsland(player);

        int currentSlot = 10;
        for (IslandChallenge challenge : playerIsland.getChallenges()) {
            ItemStack custom = new ItemStack(challenge.getMaterial(), 1);
            ItemMeta customa = custom.getItemMeta();
            customa.setDisplayName("§6" + challenge.getName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Progression: " + challenge.getProgress() + "/" + challenge.getMaxProgress() * (challenge.getPalier() + 1) * playerIsland.getMembers().size());
            lore.add("§7Palier: " + challenge.getPalier() + "/5");
            lore.add("§7Actif: " + (challenge.isActive() ? "§aOui" : "§cNon"));
            customa.setLore(lore);
            custom.setItemMeta(customa);
            inv.setItem(currentSlot, custom);
            currentSlot++;
        }


        ItemStack custom9 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom9.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom9.setItemMeta(customh);
        inv.setItem(35, custom9);


        player.openInventory(inv);
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Challenges")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu((Player) e.getWhoClicked());
                return;
            }
            if (current.getType() == Material.CLOCK) {
                IslandChallengesGuis.MakeDailyGui((Player) e.getWhoClicked());
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Challenges journaliers")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                IslandChallengesGuis.MakeMainGui((Player) e.getWhoClicked());
                return;
            }
        }
    }

}
