package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.challenges.IslandChallengesGuis;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRank;
import main.java.fr.verymc.island.protections.IslandSettings;
import main.java.fr.verymc.utils.WorldBorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class IslandGuiManager implements Listener {

    public static IslandGuiManager instance;
    public HashMap<UUID, String> bankAmountWaiting = new HashMap<>();
    public HashMap<UUID, Boolean> bankAmountWaitingBoolean = new HashMap<>();

    public IslandGuiManager() {
        instance = this;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if (current == null) {
            return;
        }
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
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
            if (current.getType() == Material.BARRIER) {
                IslandBorderGui.instance.openBorderIslandMenu(player);
                return;
            }
            if (current.getType() == Material.BEACON) {
                IslandTopGui.instance.openTopIslandMenu(player);
                return;
            }
            if (current.getType() == Material.BOOKSHELF) {
                IslandRankEditGui.instance.openEditRankIslandMenu(player);
                return;
            }
            if (current.getType() == Material.WOODEN_HOE) {
                IslandCoopGui.instance.openCoopIslandMenu(player);
                return;
            }
            if (current.getType() == Material.PAPER) {
                IslandChallengesGuis.MakeMainGui(player);
                return;
            }
            if (current.getType() == Material.COMPARATOR) {
                IslandSettingsGui.instance.openIslandSettingsGui(player);
                return;
            }
            if (current.getType() == Material.EMERALD_BLOCK) {
                IslandBlocsValueGui.instance.openBlocsValueGui(player);
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
                Island currentIsland = playerIsland;
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
                    player.sendMessage("§6§lIles §8» §fTu ne peux pas faire cette action.");
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
                if (playerIsland.getSizeUpgrade().upOfOneLevel(player)) {
                    IslandUpgradeGui.instance.openUpgradeIslandMenu(player);
                    playerIsland.sendMessageToEveryMember(
                            "§6§lIles §8» §f" + player.getName() + " a amélioré la taille de l'île au niveau §6" + playerIsland.getSizeUpgrade().getLevel() +
                                    "§f, la taille de l'île est maintenant de §6" + playerIsland.getSizeUpgrade().getSize() + "x" +
                                    playerIsland.getSizeUpgrade().getSize() + "§f.");
                    return;
                } else {
                    player.sendMessage("§6§lIles §8» §fFonds insuffisants ou niveau maximum atteint.");
                }
            }
            if (current.getType() == Material.PAPER) {
                if (playerIsland.getMemberUpgrade().upOfOneLevel(player)) {
                    IslandUpgradeGui.instance.openUpgradeIslandMenu(player);
                    playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() +
                            " a amélioré le nombre de membres max de l'île au niveau §6" +
                            playerIsland.getMemberUpgrade().getLevel() +
                            "§f, le nombre de membres max de l'île est maintenant de §6" +
                            playerIsland.getMemberUpgrade().getMaxMembers() + "§f.");
                } else {
                    player.sendMessage("§6§lIles §8» §fFonds insuffisants ou niveau maximum atteint.");
                }
                return;
            }
            if (current.getType() == Material.COBBLESTONE) {
                if (playerIsland.getGeneratorUpgrade().upOfOneLevel(player)) {
                    playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() +
                            " a amélioré le générateur de l'île au niveau §6" +
                            playerIsland.getGeneratorUpgrade().getLevel() + ". ");
                    IslandUpgradeGui.instance.openUpgradeIslandMenu(player);
                } else {
                    player.sendMessage("§6§lIles §8» §fFonds insuffisants ou niveau maximum atteint.");
                }
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Banque de l'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                return;
            }
            if (current.getType() == Material.SUNFLOWER) {
                if (e.getClick() == ClickType.RIGHT &&
                        playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_ADD, player)) {
                    if (bankAmountWaiting.containsKey(player.getUniqueId()) && bankAmountWaiting.get(player.getUniqueId()).equals("money")
                            && bankAmountWaitingBoolean.get(player.getUniqueId())) {
                        bankAmountWaitingBoolean.remove(player.getUniqueId());
                        bankAmountWaiting.remove(player.getUniqueId());
                        player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §aajouter§f de l'argent à la banque.");
                        IslandBankGui.instance.openBankIslandMenu(player);
                    } else {
                        player.closeInventory();
                        bankAmountWaiting.put(player.getUniqueId(), "money");
                        bankAmountWaitingBoolean.put(player.getUniqueId(), true);
                        player.sendMessage("§6§lIles §8» §aActivation §fdu mode séléction du montant pour §aajouter§f de l'argent à la banque.");
                    }
                    return;
                } else if (e.getClick() == ClickType.LEFT &&
                        playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_REMOVE, player)) {
                    if (bankAmountWaiting.containsKey(player.getUniqueId()) && bankAmountWaiting.get(player.getUniqueId()).equalsIgnoreCase("money")
                            && !bankAmountWaitingBoolean.get(player.getUniqueId())) {
                        bankAmountWaitingBoolean.remove(player.getUniqueId());
                        bankAmountWaiting.remove(player.getUniqueId());
                        player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §cretirer §fde l'argent de la banque.");
                        IslandBankGui.instance.openBankIslandMenu(player);
                    } else {
                        player.closeInventory();
                        bankAmountWaiting.put(player.getUniqueId(), "money");
                        bankAmountWaitingBoolean.put(player.getUniqueId(), false);
                        player.sendMessage("§6§lIles §8» §aActivation §fdu mode séléction du montant pour §cretirer §fde l'argent de la banque.");
                    }
                    return;
                }
                return;
            }
            if (current.getType() == Material.NETHER_STAR) {
                if (playerIsland.getBank().getCrystaux() < 5.0) {
                    player.sendMessage("§6§lIles §8» §fTu n'as pas assez de crystaux en banque.");
                    return;
                }
                if (e.getClick() == ClickType.LEFT &&
                        playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_REMOVE, player)) {
                    ItemStack item = new ItemStack(Material.NETHER_STAR);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§65 crystaux");
                    meta.setLore(Arrays.asList("§7Clic pour récupérer 5 crystaux", "§7Shit clic pour tout récupérer",
                            "§7Récupérés par §6" + player.getName()));
                    meta.setUnbreakable(true);
                    item.setItemMeta(meta);
                    player.getInventory().addItem(item);
                    playerIsland.getBank().removeCrystaux(5.0);
                    IslandBankGui.instance.openBankIslandMenu(player);
                    return;
                }
            }
            if (current.getType() == Material.EXPERIENCE_BOTTLE) {
                if (e.getClick() == ClickType.RIGHT &&
                        playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_ADD, player)) {
                    if (bankAmountWaiting.containsKey(player.getUniqueId()) && bankAmountWaiting.get(player.getUniqueId()).equalsIgnoreCase("xp") &&
                            bankAmountWaitingBoolean.get(player.getUniqueId())) {
                        bankAmountWaitingBoolean.remove(player.getUniqueId());
                        bankAmountWaiting.remove(player.getUniqueId());
                        player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §aajouter §fde l'expérience à la banque.");
                        IslandBankGui.instance.openBankIslandMenu(player);
                    } else {
                        player.closeInventory();
                        bankAmountWaiting.put(player.getUniqueId(), "xp");
                        bankAmountWaitingBoolean.put(player.getUniqueId(), true);
                        player.sendMessage("§6§lIles §8» §aActivation §fdu mode séléction du montant pour §aajouter §fde l'expérience à la banque.");
                    }
                    return;
                }
                if (e.getClick() == ClickType.LEFT &&
                        playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_REMOVE, player)) {
                    if (bankAmountWaiting.containsKey(player.getUniqueId()) && bankAmountWaiting.get(player.getUniqueId()).equalsIgnoreCase("xp") &&
                            !bankAmountWaitingBoolean.get(player.getUniqueId())) {
                        bankAmountWaitingBoolean.remove(player.getUniqueId());
                        bankAmountWaiting.remove(player.getUniqueId());
                        player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §cretirer §fde l'expérience à la banque.");
                        IslandBankGui.instance.openBankIslandMenu(player);
                    } else {
                        player.closeInventory();
                        bankAmountWaiting.put(player.getUniqueId(), "xp");
                        bankAmountWaitingBoolean.put(player.getUniqueId(), false);
                        player.sendMessage("§6§lIles §8» §aActivation §fdu mode séléction du montant pour §cretirer §fde l'expérience à la banque.");
                    }
                    return;
                }
            }
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Bordure de l'île")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
            if (!playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.CHANGE_BORDER_COLOR, player)) {
                return;
            }
            if (current.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                playerIsland.setBorderColor(WorldBorderUtil.Color.GREEN);
                IslandManager.instance.setWorldBorderForAllPlayerOnIsland(playerIsland);
                IslandBorderGui.instance.openBorderIslandMenu(player);
                return;
            }
            if (current.getType() == Material.RED_STAINED_GLASS_PANE) {
                playerIsland.setBorderColor(WorldBorderUtil.Color.RED);
                IslandManager.instance.setWorldBorderForAllPlayerOnIsland(playerIsland);
                IslandBorderGui.instance.openBorderIslandMenu(player);
                return;
            }
            if (current.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                playerIsland.setBorderColor(WorldBorderUtil.Color.BLUE);
                IslandManager.instance.setWorldBorderForAllPlayerOnIsland(playerIsland);
                IslandBorderGui.instance.openBorderIslandMenu(player);
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Classement des îles")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Permissions des grades de l'île")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
            if (e.getClick() != ClickType.LEFT && e.getClick() != ClickType.RIGHT) {
                return;
            }
            if (!playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.CHANGE_PERMS, player)) {
                return;
            }
            for (IslandPerms perm : IslandPerms.values()) {
                if (current.getType() == IslandPerms.getItemStackForPerm(perm).getType()) {
                    if (e.getClick() == ClickType.LEFT) {
                        if (playerIsland.downPerms(player, playerIsland, perm, IslandRank.instance.getPreviousRankForPerm(
                                perm, playerIsland))) {
                            IslandRankEditGui.instance.openEditRankIslandMenu(player);
                            return;
                        }
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        if (playerIsland.upPerms(player, playerIsland, perm, IslandRank.instance.getNextRankForPerm(
                                perm, playerIsland))) {
                            IslandRankEditGui.instance.openEditRankIslandMenu(player);
                            return;
                        }
                    }
                    break;
                }
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Membres temporaires (coops)")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }

            if (current.getType() == Material.PLAYER_HEAD) {
                for (UUID uuid : playerIsland.getCoops()) {
                    Player target = Bukkit.getPlayer(uuid);
                    if (target == null) {
                        target = Bukkit.getOfflinePlayer(uuid).getPlayer();
                    }
                    if (target == null) {
                        continue;
                    }
                    if (current.getDisplayName().equalsIgnoreCase("§6" + target.getName())) {
                        playerIsland.removeCoop(target.getUniqueId());
                        IslandCoopGui.instance.openCoopIslandMenu(player);
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() + " a été retiré du statut de coop par "
                                + player.getName() + ".");
                        target.sendMessage("§6§lIles §8» §fVous avez été retiré du statut de coop par "
                                + player.getName() + ".");
                        return;
                    }
                }
            }

        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Valeur des blocs")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Paramètres de l'île")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }

            boolean clod = current.getType() == Material.DAYLIGHT_DETECTOR;
            boolean clock = current.getType() == Material.CLOCK;
            for (IslandSettings settings : IslandSettings.values()) {
                if (IslandSettings.getItemForSetting(settings).getType() != current.getType()) {
                    continue;
                }
                if (!clod && !clock) {
                    if (playerIsland.hasSettingActivated(settings)) {
                        playerIsland.removeSettingActived(settings);
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §fLe paramètre '" + settings.getDesc() + "'§f a été §cdésactivé§f par "
                                + player.getName() + ".");
                    } else {
                        playerIsland.addSettingActivated(settings);
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §fLe paramètre '" + settings.getDesc() + "'§f a été §aactivé§f par "
                                + player.getName() + ".");
                    }
                    IslandSettingsGui.instance.openIslandSettingsGui(player);
                    return;
                }
                if (playerIsland.hasSettingActivated(settings)) {
                    playerIsland.removeSettingActived(settings);
                    IslandSettings islandSettings = IslandSettings.getNext(settings);
                    playerIsland.addSettingActivated(islandSettings);
                    playerIsland.sendMessageToEveryMember("§6§lIles §8» §fLe paramètre '" + settings.getDesc() + "'§f a été §6changé§f en "
                            + islandSettings.getDesc() + "§f par " + player.getName() + ".");
                    playerIsland.toggleTimeAndWeather();
                    IslandSettingsGui.instance.openIslandSettingsGui(player);
                    return;
                }
            }
        }
    }
}
