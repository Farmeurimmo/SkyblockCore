package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
            if (current.getType() == Material.PLAYER_HEAD) {
                IslandMemberGui.instance.openMemberIslandMenu(player);
                return;
            }
            if (current.getType() == Material.GRASS_BLOCK) {
                IslandManager.instance.teleportPlayerToIslandSafe(player);
                return;
            }
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu(player);
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Membres de l'île")) {
            e.setCancelled(true);
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
                    targetUUID = Bukkit.getPlayer(playerName).getUniqueId();
                }
                if (IslandManager.instance.isOwner(player)) {
                    if (!currentIsland.getOwner().equals(targetUUID)) {
                        if (e.isLeftClick()) {
                            if (currentIsland.promote(targetUUID)) {
                                player.sendMessage("§6§6§lIles §8» §fVous avez promu §6" + playerName + " §fau grade §6" +
                                        currentIsland.getIslandRankFromUUID(targetUUID).getName());
                            }
                            return;
                        }
                        if (e.isRightClick()) {
                            if (currentIsland.demote(targetUUID)) {
                                player.sendMessage("§6§6§lIles §8» §fVous avez rétrogradé §6" + playerName + " §fau grade §6" +
                                        currentIsland.getIslandRankFromUUID(targetUUID).getName());
                            }
                            return;
                        }
                        if (e.getClick() == ClickType.MIDDLE) {
                            if (currentIsland.kickFromIsland(targetUUID)) {
                                player.sendMessage("§6§6§lIles §8» §fVous avez exclu §6" + playerName + " §fde l'île");
                            }
                            return;
                        }
                        return;
                    }
                }
                if (currentIsland.getPerms(currentIsland.getIslandRankFromUUID(player.getUniqueId())).contains(IslandPerms.PROMOTE)) {
                    if (e.isLeftClick()) {
                        if (IslandRank.isUp(currentIsland.getIslandRankFromUUID(player.getUniqueId()), currentIsland.getIslandRankFromUUID(targetUUID))) {
                            if (currentIsland.promote(targetUUID)) {
                                player.sendMessage("§6§6§lIles §8» §fVous avez promu §6" + playerName + " §fau grade §6" +
                                        currentIsland.getIslandRankFromUUID(targetUUID).getName());
                            }
                        }
                        return;
                    }
                }
                if (currentIsland.getPerms(currentIsland.getIslandRankFromUUID(player.getUniqueId())).contains(IslandPerms.DEMOTE)) {
                    if (e.isRightClick()) {
                        if (IslandRank.isUp(currentIsland.getIslandRankFromUUID(player.getUniqueId()), currentIsland.getIslandRankFromUUID(targetUUID))) {
                            if (currentIsland.demote(targetUUID)) {
                                player.sendMessage("§6§6§lIles §8» §fVous avez rétrogradé §6" + playerName + " §fau grade §6" +
                                        currentIsland.getIslandRankFromUUID(targetUUID).getName());
                            }
                        }
                        return;
                    }
                }
                if (currentIsland.getPerms(currentIsland.getIslandRankFromUUID(player.getUniqueId())).contains(IslandPerms.KICK)) {
                    if (e.getClick() == ClickType.MIDDLE) {
                        if (IslandRank.isUp(currentIsland.getIslandRankFromUUID(player.getUniqueId()), currentIsland.getIslandRankFromUUID(targetUUID))) {
                            if (currentIsland.demote(targetUUID)) {
                                player.sendMessage("§6§6§lIles §8» §fVous avez exclu §6" + playerName + " §fde l'île");
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
