package main.java.fr.verymc.spigot.core.cmd.base;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.ServersManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.IslandValueCalcManager;
import main.java.fr.verymc.spigot.island.guis.*;
import main.java.fr.verymc.spigot.island.perms.IslandPerms;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.json.simple.JSONArray;

import java.util.*;

public class IslandCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (Main.instance.serverType != ServerType.SKYBLOCK_ISLAND) {
                final ByteArrayDataOutput out = ByteStreams.newDataOutput();
                Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.instance, "BungeeCord");
                for (Map.Entry<String, JSONArray> entry : ServersManager.instance.stringJSONArrayHashMap.entrySet()) {
                    if (entry.getValue().contains(p.getUniqueId())) {
                        out.writeUTF("Connect");
                        out.writeUTF(entry.getKey());
                        p.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
                        return true;
                    }
                }
                for (Map.Entry<String, JSONArray> entry : ServersManager.instance.stringJSONArrayHashMap.entrySet()) {
                    if (entry.getKey().contains(ServerType.SKYBLOCK_ISLAND.getDisplayName())) {
                        out.writeUTF("Connect");
                        out.writeUTF(entry.getKey());
                        p.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
                        return true;
                    }
                }

                return true;
            }
            if (args.length == 0) {
                if (!IslandManager.instance.asAnIsland(p)) {
                    IslandManager.instance.genIsland(p);
                    return true;
                }
                IslandMainGui.instance.openMainIslandMenu(p);
                return true;


            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("bypass")) {
                    if (p.hasPermission("bypass")) {
                        if (IslandManager.instance.isBypassing(p.getUniqueId())) {
                            IslandManager.instance.removeBypassing(p.getUniqueId());
                            p.sendMessage("§6§lIles §8» §fTu ne peux plus bypasser les îles !");
                        } else {
                            IslandManager.instance.addBypassing(p.getUniqueId());
                            p.sendMessage("§6§lIles §8» §fTu peux maintenant bypasser les îles !");
                        }
                    } else {
                        p.sendMessage("§6§lIles §8» §cVous n'avez pas les permissions de faire ceci !");
                        if (IslandManager.instance.isBypassing(p.getUniqueId())) {
                            IslandManager.instance.removeBypassing(p.getUniqueId());
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("spy")) {
                    if (p.hasPermission("spy")) {
                        if (IslandManager.instance.isSpying(p.getUniqueId())) {
                            IslandManager.instance.removeSpying(p.getUniqueId());
                            p.sendMessage("§6§lIles §8» §fTu ne peux plus spy les îles !");
                        } else {
                            IslandManager.instance.addSpying(p.getUniqueId());
                            p.sendMessage("§6§lIles §8» §fTu peux maintenant spy les îles !");
                        }
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu n'as pas la permission de faire ceci !");
                        if (IslandManager.instance.isSpying(p.getUniqueId())) {
                            IslandManager.instance.removeSpying(p.getUniqueId());
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("top")) {
                    IslandTopGui.instance.openTopIslandMenu(p);
                    return true;
                } else if (args[0].equalsIgnoreCase("blocvalues")) {
                    IslandBlocsValueGui.instance.openBlocsValueGui(p);
                    return true;
                } else if (args[0].equalsIgnoreCase("settings")) {
                    IslandSettingsGui.instance.openIslandSettingsGui(p);
                    return true;
                }
                if (IslandManager.instance.getPlayerIsland(p) == null) {
                    p.sendMessage("§6§lIles §8» §fTu n'es pas dans une île !");
                    return true;
                }

                if (args[0].equalsIgnoreCase("go") || args[0].equalsIgnoreCase("home")) {
                    IslandManager.instance.teleportPlayerToIslandSafe(p);
                    return true;
                } else if (args[0].equalsIgnoreCase("sethome")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (IslandManager.instance.getIslandByLoc(p.getLocation()) != playerIsland) {
                        p.sendMessage("§6§lIles §8» §fTu dois être sur ton île pour définir le home de ton île.");
                        return true;
                    }
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.SET_HOME, p)) {
                        playerIsland.setHome(p.getLocation());
                        p.sendMessage("§6§lIles §8» §fNouveau home d'île définit !");
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu n'as pas la permission de faire ceci !");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("upgrade")) {
                    IslandUpgradeGui.instance.openUpgradeIslandMenu(p);
                    return true;
                } else if (args[0].equalsIgnoreCase("bank")) {
                    IslandBankGui.instance.openBankIslandMenu(p);
                    return true;
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (IslandManager.instance.getPlayerIsland(p).getOwnerUUID().equals(p.getUniqueId())) {
                        p.sendMessage("§6§lIles §8» §fTu ne peux pas quitter ton île, pour la quitter il faut d'abord la détruire," +
                                " ou la transférer à un autre joueur (/is transfer ou /is delete).");
                        return true;
                    }
                    IslandManager.instance.leaveIsland(p);
                    return true;
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (IslandManager.instance.getPlayerIsland(p).getOwnerUUID().equals(p.getUniqueId())) {
                        IslandConfirmationGui.instance.openConfirmationGui(p, "la suppression de l'île");
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu ne peux pas détruire cette île, tu n'es pas le propriétaire.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("recalc")) {
                    if (p.hasPermission("*")) {
                        IslandValueCalcManager.instance.makeCountForAllIsland();
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu n'as pas la permission de faire ceci !");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("chat")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.isIslandChatToggled(p.getUniqueId())) {
                        playerIsland.removeIslandChatToggled(p.getUniqueId());
                    } else {
                        playerIsland.addIslandChatToggled(p.getUniqueId());
                    }
                    p.sendMessage("§6§lIles §8» §fChat d'île " + (playerIsland.isIslandChatToggled(p.getUniqueId()) ? "§aactivé" : "§cdésactivé") +
                            " §f!");
                    return true;
                } else if (args[0].equalsIgnoreCase("public")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (!playerIsland.isPublic()) {
                        playerIsland.setPublic(true);
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §fL'île est maintenant publique ! (Action de " + p.getName() + ")", p);
                    } else {
                        p.sendMessage("§6§lIles §8» §fL'île est déjà publique !");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("private")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.isPublic()) {
                        playerIsland.setPublic(false);
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §fL'île est maintenant privée ! (Action de " + p.getName() + ")", p);
                    } else {
                        p.sendMessage("§6§lIles §8» §fL'île est déjà privée !");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("cancelinvites")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.CANCEL_INVITE, p)) {
                        if (IslandManager.instance.pendingInvites.containsKey(p)) {
                            IslandManager.instance.cancelInvite(p, p);
                        } else {
                            p.sendMessage("§6§lIles §8» §fVous n'avez pas d'invitations en cours.");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("permissions")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.CHANGE_PERMS, p)) {
                        IslandRankEditGui.instance.openEditRankIslandMenu(p);
                        return true;
                    }
                }


            } else if (args.length == 2) {
                if (!args[0].equalsIgnoreCase("rename") && Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage("§6§lIles §8» §fCe joueur n'existe pas ou n'est pas connecté.");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == p) {
                    p.sendMessage("§6§lIles §8» §fTu ne peux pas effectuer d'action à toi même.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("accept")) {
                    if (!IslandManager.instance.asAnIsland(p)) {
                        if (IslandManager.instance.getPlayerIsland(target).getMaxMembers() > IslandManager.instance.getPlayerIsland(target).getMembers().size()) {
                            if (IslandManager.instance.acceptInvite(target, p)) {
                                p.sendMessage("§6§lIles §8» §fVous avez accepté l'invitation de §6" + target.getName());
                            } else {
                                p.sendMessage("§6§lIles §8» §fVous n'avez pas reçu d'invitation de §6" + target.getName());
                            }
                        } else {
                            p.sendMessage("§6§lIles §8» §fL'île de §6" + target.getName() + " §fest pleine.");
                        }
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu es déjà dans une île.");
                    }
                    return true;
                }
                if (!IslandManager.instance.asAnIsland(p)) {
                    p.sendMessage("§6§lIles §8» §fTu n'es pas dans une ile.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("invite")) {
                    if (!IslandManager.instance.asAnIsland(target)) {
                        if (IslandManager.instance.getPlayerIsland(p).getMaxMembers() > IslandManager.instance.getPlayerIsland(p).getMembers().size()) {
                            if (IslandManager.instance.invitePlayer(p, target)) {

                            } else {
                                p.sendMessage("§6§lIles §8» §fVous avez déjà envoyé une invitation à §6" + target.getName());
                            }
                        } else {
                            p.sendMessage("§6§lIles §8» §fTu ne peux pas inviter plus de §6" + IslandManager.instance.getPlayerIsland(p).getMaxMembers() + "§f joueurs.");
                        }
                    } else {
                        p.sendMessage("§6§lIles §8» §f" + target.getName() + " est déjà dans une île.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("kick")) {
                    if (IslandManager.instance.promoteAndDemoteAction(p, target.getUniqueId(), target.getName(),
                            ClickType.MIDDLE, IslandManager.instance.getPlayerIsland(p))) {
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu ne peux pas faire cette action.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("promote")) {
                    if (IslandManager.instance.promoteAndDemoteAction(p, target.getUniqueId(), target.getName(),
                            ClickType.LEFT, IslandManager.instance.getPlayerIsland(p))) {

                    } else {
                        p.sendMessage("§6§lIles §8» §fTu ne peux pas faire cette action.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("demote")) {
                    if (IslandManager.instance.promoteAndDemoteAction(p, target.getUniqueId(), target.getName(),
                            ClickType.RIGHT, IslandManager.instance.getPlayerIsland(p))) {

                    } else {
                        p.sendMessage("§6§lIles §8» §fTu ne peux pas faire cette action.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("transfer")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.getOwnerUUID().equals(p.getUniqueId())) {
                        IslandManager.instance.setIslandNewOwner(target);
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu n'es pas le propriétaire de cette île.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("coop")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.isCoop(target.getUniqueId())) {
                        p.sendMessage("§6§lIles §8» §f" + target.getName() + " est déjà en coop.");
                        return true;
                    }
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.ADD_COOP, p)) {
                        playerIsland.addCoop(target.getUniqueId());
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                                " vient de rejoindre l'île en tant que membre temporaire par " + p.getName() + ", son status expirera quand" +
                                " il se déconnectera ou que tous les membres de l'îles seront déconnectés.", p);
                        target.sendMessage("§6§lIles §8» §fTu viens d'être ajouté à l'île de " + p.getName() + " en tant que membre temporaire " +
                                ", ton statut expirera quand tu déconnecteras ou quand tous les membres de l'îles seront déconnectés.");
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu n'as pas la permission de faire cette action.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("uncoop")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (!playerIsland.isCoop(target.getUniqueId())) {
                        p.sendMessage("§6§lIles §8» §f" + target.getName() + " n'est pas en coop.");
                        return true;
                    }
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.REMOVE_COOP, p)) {
                        playerIsland.removeCoop(target.getUniqueId());
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                                " vient de perdre le status de membre temporaire par  " + p.getName() + ".", p);
                        target.sendMessage("§6§lIles §8» §fVous avez été retiré du statut de coop par "
                                + p.getName() + ".");
                    } else {
                        p.sendMessage("§6§lIles §8» §fTu n'as pas la permission de faire cette action.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("ban")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.BAN, p)) {
                        if (playerIsland.getMembers().containsKey(target.getUniqueId())) {
                            p.sendMessage("§6§lIles §8» §f" + target.getName() + " est un membre de l'île.");
                            return true;
                        }
                        if (!playerIsland.isBanned(target.getUniqueId())) {
                            if (playerIsland.addBanned(target.getUniqueId())) {
                                if (IslandManager.instance.getIslandByLoc(target.getLocation()) == playerIsland) {
                                    PlayerUtils.instance.teleportPlayerFromRequest(target, SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
                                }
                                playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                                        " vient d'être banni de l'île par " + p.getName() + ".", p);
                                target.sendMessage("§6§lIles §8» §fVous avez été banni de l'île par " + p.getName() + ".");
                            } else {
                                p.sendMessage("§6§lIles §8» §fImpossible de bannir " + target.getName() + ".");
                            }
                        } else {
                            p.sendMessage("§6§lIles §8» §f" + target.getName() + " est déjà banni de l'île.");
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("unban")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.UNBAN, p)) {
                        if (playerIsland.isBanned(target.getUniqueId())) {
                            if (playerIsland.removeBanned(target.getUniqueId())) {
                                playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                                        " vient de être débanni de l'île par " + p.getName() + ".", p);
                            } else {
                                p.sendMessage("§6§lIles §8» §fImpossible de débannir " + target.getName() + ".");
                            }
                        } else {
                            p.sendMessage("§6§lIles §8» §f" + target.getName() + " n'est pas banni de l'île.");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("expel")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.EXPEL, p)) {
                        if (playerIsland.getMembers().containsKey(target.getUniqueId())) {
                            p.sendMessage("§6§lIles §8» §f" + target.getName() + " est un membre de l'île.");
                            return true;
                        }
                        if (IslandManager.instance.getIslandByLoc(target.getLocation()) == playerIsland) {
                            PlayerUtils.instance.teleportPlayerFromRequest(target, SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
                            target.sendMessage("§6§lIles §8» §fVous avez été expulsé de l'île par " + p.getName() + ".");
                            playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                                    " vient d'être expulsé de l'île par " + p.getName() + ".", p);
                        } else {
                            p.sendMessage("§6§lIles §8» §f" + target.getName() + " n'est pas sur l'île.");
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("cancelinvites")) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.CANCEL_INVITE, p)) {
                        if (IslandManager.instance.pendingInvites.containsKey(target)) {
                            IslandManager.instance.cancelInvite(p, target);
                        } else {
                            p.sendMessage("§6§lIles §8» §f" + target.getName() + " n'a pas d'invitation en cours.");
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("rename")) {
                    if (!p.hasPermission("is.rename")) {
                        p.sendMessage("§6§lIles §8» §fVous n'avez pas la permission.");
                        return true;
                    }
                    Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                    if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.SET_ISLAND_NAME, p)) {
                        if (args[1].length() >= 4 && args[1].length() <= 32) {
                            playerIsland.setName(args[1]);
                            playerIsland.sendMessageToEveryMember("§6§lIles §8» §fL'île a été renommée par " + p.getName() + " en " +
                                    playerIsland.getName() + ".", p);
                        } else {
                            p.sendMessage("§6§lIles §8» §fLe nom de l'île doit contenir entre 4 et 32 caractères.");
                        }
                    }
                    return true;
                }
            } else if (args.length == 3) {

            } else if (args.length == 4) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage("§6§lIles §8» §fCe joueur n'existe pas ou n'est pas connecté.");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (!IslandManager.instance.asAnIsland(target)) {
                    p.sendMessage("§6§lIles §8» §fTu n'es pas dans une ile.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("bank") && p.hasPermission("is.bank.give")) {
                    if (args[2].equalsIgnoreCase("moneyadd")) {
                        try {
                            double amount = Double.parseDouble(args[3]);
                            IslandManager.instance.getPlayerIsland(target).getBank().addMoney(amount);
                            p.sendMessage("§6§lIles §8» §fTu as give §6" + amount + "$§f à §6" + target.getName() + "§f.");
                            target.sendMessage("§6§lIles §8» §fTu as reçu §6" + amount + "$§f de la part de §6CONSOLE§f.");
                            return true;
                        } catch (NumberFormatException e) {
                            p.sendMessage("§6§lIles §8» §fTu dois mettre un nombre valide.");
                            return true;
                        }
                    }
                    if (args[2].equalsIgnoreCase("crystauxadd")) {
                        try {
                            double amount = Double.parseDouble(args[3]);
                            IslandManager.instance.getPlayerIsland(target).getBank().addCrystaux(amount);
                            p.sendMessage("§6§lIles §8» §fTu as give §6" + amount + "§f à §6" + target.getName() + "§f.");
                            target.sendMessage("§6§lIles §8» §fTu as reçu §6" + amount + "§f de la part de §6CONSOLE§f.");
                            return true;
                        } catch (NumberFormatException e) {
                            p.sendMessage("§6§lIles §8» §fTu dois mettre un nombre valide.");
                            return true;
                        }
                    }
                    if (args[2].equalsIgnoreCase("xpadd")) {
                        try {
                            Integer amount = Integer.parseInt(args[3]);
                            IslandManager.instance.getPlayerIsland(target).getBank().addXp(amount);
                            p.sendMessage("§6§lIles §8» §fTu as give §6" + amount + "§f à §6" + target.getName() + "§f.");
                            target.sendMessage("§6§lIles §8» §fTu as reçu §6" + amount + "§f de la part de §6CONSOLE§f.");
                            return true;
                        } catch (NumberFormatException e) {
                            p.sendMessage("§6§lIles §8» §fTu dois mettre un nombre valide.");
                            return true;
                        }
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("is")) {
            if (args.length == 1) {
                subcmd.addAll(Arrays.asList("go", "home", "invite", "accept", "kick", "promote", "demote", "sethome", "upgrade", "bank",
                        "leave", "delete", "top", "coop", "uncoop", "chat", "public", "private", "bypass", "spy", "transfer", "ban",
                        "unban", "expel", "cancelinvites", "rename", "settings", "blocvalues", "permissions"));
            } else {
                if (IslandManager.instance.asAnIsland((Player) sender)) {
                    Island playerIsland = IslandManager.instance.getPlayerIsland((Player) sender);
                    if (args[0].equalsIgnoreCase("ban")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (!playerIsland.getMembers().containsKey(p.getUniqueId())) {
                                subcmd.add(p.getName());
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("unban")) {
                        for (UUID uuid : playerIsland.getBanneds()) {
                            subcmd.add(Bukkit.getOfflinePlayer(uuid).getName());
                        }
                    }
                    if (args[0].equalsIgnoreCase("expel")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            subcmd.add(p.getName());
                        }
                    }
                    if (args[0].equalsIgnoreCase("invite")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (!playerIsland.getMembers().containsKey(p.getUniqueId())) {
                                subcmd.add(p.getName());
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("accept")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            subcmd.add(p.getName());
                        }
                    }
                    if (args[0].equalsIgnoreCase("kick")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (playerIsland.getMembers().containsKey(p.getUniqueId())) {
                                subcmd.add(p.getName());
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("cancelinvites")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (playerIsland.getMembers().containsKey(p.getUniqueId())) {
                                subcmd.add(p.getName());
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("promote")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (playerIsland.getMembers().containsKey(p.getUniqueId())) {
                                subcmd.add(p.getName());
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("demote")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (playerIsland.getMembers().containsKey(p.getUniqueId())) {
                                subcmd.add(p.getName());
                            }
                        }
                    }
                }
                if (subcmd.size() == 0) {
                    subcmd.add("");
                }
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }
}
