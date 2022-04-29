package main.java.fr.verymc.events;

import main.java.fr.verymc.blocks.PlayerShopGuis;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.guis.IslandBankGui;
import main.java.fr.verymc.island.guis.IslandGuiManager;
import main.java.fr.verymc.island.guis.IslandTopGui;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.playerwarps.PlayerWarp;
import main.java.fr.verymc.playerwarps.PlayerWarpManager;
import main.java.fr.verymc.playerwarps.PlayerWarpManagingGui;
import main.java.fr.verymc.storage.SkyblockUserManager;
import main.java.fr.verymc.utils.PlayerUtils;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.text.NumberFormat;

public class TchatManager implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncChat(PlayerChatEvent e) {
        Player player = e.getPlayer();
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        String Prefix = "§7N/A";
        String Suffix = "";
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);


        if (IslandGuiManager.instance.bankAmountWaiting.containsKey(player.getUniqueId()) &&
                IslandGuiManager.instance.bankAmountWaitingBoolean.get(player.getUniqueId())) {
            if (!playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_ADD, player)) {
                return;
            }
            double price;
            try {
                price = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException ex) {
                player.sendMessage("§6§lIles §8» §cVeuillez entrer un nombre valide.");
                return;
            }
            if (IslandGuiManager.instance.bankAmountWaiting.get(player.getUniqueId()).equalsIgnoreCase("money")) {
                if (!EcoAccountsManager.instance.checkForFounds(player, price)) {
                    player.sendMessage("§6§lIles §8» §cVous n'avez pas assez d'argent sur votre compte.");
                    return;
                }
                EcoAccountsManager.instance.removeFoundsUUID(player.getUniqueId(), price, false);
                playerIsland.getBank().addMoney(price);
                IslandGuiManager.instance.bankAmountWaiting.remove(player.getUniqueId());
                IslandGuiManager.instance.bankAmountWaitingBoolean.remove(player.getUniqueId());
                player.sendMessage("§6§lIles §8» §fVous avez §aajouté §6" + NumberFormat.getInstance().format(price) + "$§f à votre banque.");
                player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §aajouter§f de l'argent à la banque.");
            } else if (IslandGuiManager.instance.bankAmountWaiting.get(player.getUniqueId()).equalsIgnoreCase("xp")) {
                if (PlayerUtils.instance.getTotalExperience(player) < price) {
                    player.sendMessage("§6§lIles §8» §cVous n'avez pas assez d'xp sur vous.");
                    return;
                }
                playerIsland.getBank().addXp((int) price);
                PlayerUtils.instance.setTotalExperience(player, PlayerUtils.instance.getTotalExperience(player) - (int) price);
                IslandGuiManager.instance.bankAmountWaiting.remove(player.getUniqueId());
                IslandGuiManager.instance.bankAmountWaitingBoolean.remove(player.getUniqueId());
                player.sendMessage("§6§lIles §8» §fVous avez §aajouté §6" + NumberFormat.getInstance().format(price) + "xp§f à votre banque.");
                player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §aajouter§f de l'xp à la banque.");
            }
            IslandBankGui.instance.openBankIslandMenu(player);
            return;
        } else if (IslandGuiManager.instance.bankAmountWaiting.containsKey(player.getUniqueId()) &&
                !IslandGuiManager.instance.bankAmountWaitingBoolean.get(player.getUniqueId())) {
            if (!playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_REMOVE, player)) {
                return;
            }
            double price;
            try {
                price = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException ex) {
                player.sendMessage("§6§lIles §8» §cVeuillez entrer un nombre valide.");
                return;
            }
            if (IslandGuiManager.instance.bankAmountWaiting.get(player.getUniqueId()).equalsIgnoreCase("money")) {
                if (playerIsland.getBank().getMoney() < price) {
                    player.sendMessage("§6§lIles §8» §cVous n'avez pas assez d'argent dans votre banque.");
                    return;
                }
                EcoAccountsManager.instance.addFoundsUUID(player.getUniqueId(), price, false);
                playerIsland.getBank().removeMoney(price);
                IslandGuiManager.instance.bankAmountWaiting.remove(player.getUniqueId());
                IslandGuiManager.instance.bankAmountWaitingBoolean.remove(player.getUniqueId());
                player.sendMessage("§6§lIles §8» §fVous avez §cretiré §6" + NumberFormat.getInstance().format(price) + "$§f de votre banque.");
                player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §cretirer§f de l'argent de la banque.");
            } else if (IslandGuiManager.instance.bankAmountWaiting.get(player.getUniqueId()).equalsIgnoreCase("xp")) {
                if (playerIsland.getBank().getXp() < price) {
                    player.sendMessage("§6§lIles §8» §cVous n'avez pas assez d'xp dans votre banque.");
                    return;
                }
                playerIsland.getBank().removeXp((int) price);
                PlayerUtils.instance.setTotalExperience(player, PlayerUtils.instance.getTotalExperience(player) + (int) price);
                IslandGuiManager.instance.bankAmountWaiting.remove(player.getUniqueId());
                IslandGuiManager.instance.bankAmountWaitingBoolean.remove(player.getUniqueId());
                player.sendMessage("§6§lIles §8» §fVous avez §cretiré §6" + NumberFormat.getInstance().format(price) + "xp§f de votre banque.");
                player.sendMessage("§6§lIles §8» §cDésactivation §fdu mode séléction du montant pour §cretirer§f de l'xp de la banque.");
            }
            IslandBankGui.instance.openBankIslandMenu(player);
            return;
        }


        if (PlayerShopGuis.instance.priceEditing.containsKey(player)) {
            double price;
            try {
                price = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException ex) {
                player.sendMessage("§6§lPlayerShop §8» §cVeuillez entrer un nombre valide.");
                return;
            }

            if (price > 0) {
                PlayerShopGuis.instance.priceEditing.get(player).setPrice(price);
                PlayerShopGuis.instance.mainShopGui(PlayerShopGuis.instance.priceEditing.get(player), player);
                player.sendMessage("§6§lPlayerShop §8» §fLe prix a été mis à jour.");
                PlayerShopGuis.instance.priceEditing.remove(player);
            } else {
                player.sendMessage("§6§lPlayerShop §8» §cVeuillez entrer un nombre positif et supérieur à 0.");
            }
            return;
        }

        if (PlayerWarpManagingGui.instance.creationMode.containsKey(player)) {
            PlayerWarp warp = PlayerWarpManagingGui.instance.creationMode.get(player);
            if (e.getMessage().equalsIgnoreCase("cancel")) {
                PlayerWarpManagingGui.instance.creationMode.remove(player);
                if (PlayerWarpManagingGui.instance.oldLocation.containsKey(player)) {
                    if (warp.getLocation() == null) {
                        warp.setLocation(PlayerWarpManagingGui.instance.oldLocation.get(player));
                        player.sendMessage("§6§lPlayerWarp §8» §fVotre ancienne position a été restaurée.");
                        PlayerWarpManagingGui.instance.oldLocation.remove(player);
                        PlayerWarpManagingGui.instance.openManagingMenu(player);
                    }
                } else if (PlayerWarpManagingGui.instance.oldName.containsKey(player)) {
                    if (warp.getName() == null) {
                        warp.setName(PlayerWarpManagingGui.instance.oldName.get(player));
                        player.sendMessage("§6§lPlayerWarp §8» §fVotre ancien nom a été restauré.");
                        PlayerWarpManagingGui.instance.oldName.remove(player);
                        PlayerWarpManagingGui.instance.openManagingMenu(player);
                    }
                } else {
                    player.sendMessage("§6§lPlayerWarp §8» §fVous avez annulé la création de ce warp.");
                }
                return;
            }
            if (warp.getName() == null) {
                if (e.getMessage().length() >= 4 && e.getMessage().length() <= 32) {
                    warp.setName(e.getMessage().replace("&", "§"));
                    String toAdd = "";
                    if (warp.getLocation() == null) {
                        toAdd = "Dites OUI à l'endroit de la localisation souhaité pour le warp.";
                    }
                    player.sendMessage("§6§lPlayerShop §8» §fLe nom du warp a été mis à jour."
                            + " Nouveau nom : §7" + warp.getName() + " §f. " + toAdd);
                    if (toAdd.equals("")) {
                        PlayerWarpManagingGui.instance.openManagingMenu(player);
                        PlayerWarpManagingGui.instance.creationMode.remove(player);
                    }
                    if (PlayerWarpManagingGui.instance.oldName.containsKey(player)) {
                        PlayerWarpManagingGui.instance.oldName.remove(player);
                    }
                } else {
                    player.sendMessage("§6§lPlayerWarp §8» §cVeuillez entrer un nom de warp possédant entre 4 et 32 caractères. " +
                            "Pour annuler la création du warp dites CANCEL.");
                }
                return;
            } else if (warp.getLocation() == null) {
                if (e.getMessage().equalsIgnoreCase("OUI")) {
                    if (player.getLocation().getWorld() != IslandManager.instance.getMainWorld()) {
                        player.sendMessage("§6§lPlayerWarp §8» §cVous devez être dans le monde des îles pour y mettre votre warp.");
                        return;
                    }
                    Island island = IslandManager.instance.getIslandByLoc(player.getLocation());
                    if (island == null) {
                        player.sendMessage("§6§lPlayerWarp §8» §cVous devez être sur une île pour y mettre votre warp.");
                        return;
                    } else if (!island.getMembers().containsKey(player.getUniqueId())) {
                        player.sendMessage("§6§lPlayerWarp §8» §cVous devez être membre de cette île pour y mettre votre warp.");
                        return;
                    }
                    warp.setLocation(player.getLocation());
                    player.sendMessage("§6§lPlayerWarp §8» §fLa localisation du warp a été enregistré.");
                    PlayerWarpManagingGui.instance.creationMode.remove(player);
                    if (PlayerWarpManager.instance.getPlayerWarpFromUUID(player.getUniqueId()) == null) {
                        player.sendMessage("§6§lPlayerWarp §8» §fWarp créé avec succès, pour le modifier retourner dans le menu" +
                                " de gestion (/playerwarp).");
                    }
                    PlayerWarpManager.instance.addWarp(SkyblockUserManager.instance.getUser(player), warp);
                    PlayerWarpManagingGui.instance.openManagingMenu(player);
                    if (PlayerWarpManagingGui.instance.oldLocation.containsKey(player)) {
                        PlayerWarpManagingGui.instance.oldLocation.remove(player);
                    }
                } else {
                    player.sendMessage("§6§lPlayerWarp §8» §cVeuillez entrer OUI pour valider la localisation du warp. " +
                            "Pour annuler la création du warp dites CANCEL.");
                }
                return;
            }
        }

        boolean isIslandChat = false;
        if (playerIsland != null) {
            isIslandChat = playerIsland.isIslandChatToggled(player.getUniqueId());
        }

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Prefix = user.getCachedData().getMetaData().getPrefix();
        }
        if (user.getCachedData().getMetaData().getSuffix() != null) {
            Suffix = " " + user.getCachedData().getMetaData().getSuffix();
        }
        if (!isIslandChat) {
            TextComponent message = new TextComponent();
            TextComponent symbole = new TextComponent();
            if (playerIsland == null) {
                message.setText(Prefix + " " + player.getName() + Suffix + "§7: " + e.getMessage());
            } else {
                String classement = "#N/A";
                if (IslandTopGui.instance.getTopIsland().containsKey(playerIsland)) {
                    classement = "#" + IslandTopGui.instance.getTopIsland().get(playerIsland);
                }
                message.setText("§7[" + classement + "] " + Prefix + " " + player.getName() + Suffix + "§7: " + e.getMessage());
            }
            symbole.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez ici pour report le message de " +
                    player.getName()).create()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("").create()));
            symbole.setText("§cx ");
            symbole.addExtra(message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(symbole);
            }
        } else {
            TextComponent message = new TextComponent();
            TextComponent symbole = new TextComponent();
            message.setText("§6§lTchat d'île §8» §f[§a" + playerIsland.getMembers().get(player.getUniqueId()) + "§f] " + Prefix + " " + player.getName() +
                    Suffix + "§7: " + e.getMessage());
            symbole.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez ici pour report le message de " +
                    player.getName()).create()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("").create()));
            symbole.setText("§cx ");
            symbole.addExtra(message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (IslandManager.instance.isSpying(player.getUniqueId())) {
                    p.sendMessage(symbole);
                    continue;
                }
                if (playerIsland.isInIsland(p.getUniqueId())) {
                    p.sendMessage(symbole);
                    continue;
                }
            }
        }
    }
}
