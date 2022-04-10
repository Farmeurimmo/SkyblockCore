package main.java.fr.verymc.island.cmds;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.IslandValueCalcManager;
import main.java.fr.verymc.island.guis.*;
import main.java.fr.verymc.island.perms.IslandPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IslandCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (IslandManager.instance.mainWorld != null) {
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
                            p.sendMessage("§6§lIles §8» §fTu n'as pas la permission de faire ceci !");
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
                        if (playerIsland.getPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId())).contains(IslandPerms.SET_HOME)) {
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
                    } else if (args[0].equalsIgnoreCase("border") || args[0].equalsIgnoreCase("bordure")) {
                        IslandBorderGui.instance.openBorderIslandMenu(p);
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
                            IslandManager.instance.deleteIsland(p);
                        } else {
                            p.sendMessage("§6§lIles §8» §fTu ne peux pas détruire cette île, tu n'es pas le propriétaire.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("top")) {
                        IslandTopGui.instance.openTopIslandMenu(p);
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
                            playerIsland.sendMessageToEveryMember("§6§lIles §8» §fL'île est maintenant publique ! (Action de " + p.getName() + ")");
                        } else {
                            p.sendMessage("§6§lIles §8» §fL'île est déjà publique !");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("private")) {
                        Island playerIsland = IslandManager.instance.getPlayerIsland(p);
                        if (playerIsland.isPublic()) {
                            playerIsland.setPublic(false);
                            playerIsland.sendMessageToEveryMember("§6§lIles §8» §fL'île est maintenant privée ! (Action de " + p.getName() + ")");
                        } else {
                            p.sendMessage("§6§lIles §8» §fL'île est déjà privée !");
                        }
                        return true;
                    }


                } else if (args.length == 2) {
                    if (Bukkit.getPlayer(args[1]) == null) {
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
                                    p.sendMessage("§6§lIles §8» §fVous avez envoyé une invitation à §6" + target.getName()
                                            + " §favec succès.");
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
                        if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.ADD_COOP, p.getUniqueId())) {
                            playerIsland.addCoop(target.getUniqueId());
                            playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                                    " vient de rejoindre l'île en tant que membre temporaire par " + p.getName() + ", son status expirera quand" +
                                    " il se déconnectera ou que tous les membres de l'îles seront déconnectés.");
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
                        if (playerIsland.hasPerms(playerIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.REMOVE_COOP, p.getUniqueId())) {
                            playerIsland.removeCoop(target.getUniqueId());
                            playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                                    " vient de perdre le status de membre temporaire par  " + p.getName() + ".");
                            target.sendMessage("§6§lIles §8» §fVous avez été retiré du statut de coop par "
                                    + p.getName() + ".");
                        } else {
                            p.sendMessage("§6§lIles §8» §fTu n'as pas la permission de faire cette action.");
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
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("is")) {
            if (args.length == 1) {
                subcmd.addAll(Arrays.asList("go", "home", "invite", "accept", "kick", "promote", "demote", "sethome", "upgrade", "bank",
                        "border", "bordure", "leave", "delete", "top", "coop", "uncoop", "chat", "public", "private", "bypass", "spy",
                        "transfer"));
            } else {
                subcmd.add("");
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }
}
