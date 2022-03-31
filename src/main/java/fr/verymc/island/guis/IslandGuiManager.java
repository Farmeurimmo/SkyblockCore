package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class IslandGuiManager implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if (current == null) {
            return;
        }
        if (current.getType() == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Menu d'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                player.closeInventory();
                return;
            }
            if (current.getType() == Material.PLAYER_HEAD) {
                IslandMemberGui.instance.openMemberIslandMenu(player);
                return;
            }
            if (current.getType() == Material.ENDER_EYE) {
                IslandManager.instance.teleportPlayerToIslandSafe(player);
                return;
            }
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu(player);
                return;
            }
            if (current.getType() == Material.BLAST_FURNACE) {
                IslandUpgradeGui.instance.openUpgradeIslandMenu(player);
                return;
            }
            if (current.getType() == Material.CHEST) {
                IslandBankGui.instance.openBankIslandMenu(player);
                return;
            }
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Membres de l'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                player.closeInventory();
                return;
            }
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
            if (current.getType() == Material.PLAYER_HEAD) {
                Island currentIsland = IslandManager.instance.getPlayerIsland(player);
                String playerName = current.getItemMeta().getDisplayName().replace("§6", "");
                UUID targetUUID;
                if (Bukkit.getPlayer(playerName) != null) {
                    targetUUID = Bukkit.getPlayer(playerName).getUniqueId();
                } else {
                    targetUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
                }
                if (IslandManager.instance.promoteAndDemoteAction(player, targetUUID, playerName, e.getClick(),
                        currentIsland)) {
                    IslandMemberGui.instance.openMemberIslandMenu(player);
                    return;
                } else {
                    player.sendMessage("§6§6§lIles §8» §fTu ne peux pas faire cette action.");
                }
            }
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Améliorations de l'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                return;
            }
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
            if (current.getType() == Material.GRASS_BLOCK) {
                if (IslandManager.instance.getPlayerIsland(player).getSizeUpgrade().upOfOneLevel(player)) {
                    IslandManager.instance.setWorldBorder(player);
                    IslandUpgradeGui.instance.openUpgradeIslandMenu(player);
                    return;
                }
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Banque de l'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                return;
            }
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
        }
    }
}
