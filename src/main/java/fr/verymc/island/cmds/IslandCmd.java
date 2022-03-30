package main.java.fr.verymc.island.cmds;

import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.guis.IslandMainGui;
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
                    if (args[0].equalsIgnoreCase("go") || args[0].equalsIgnoreCase("home")) {
                        IslandManager.instance.teleportPlayerToIslandSafe(p);
                        return true;
                    }


                } else if (args.length == 2) {
                    if (Bukkit.getPlayer(args[1]) == null) {
                        p.sendMessage("§6§6§lIles §8» §fCe joueur n'existe pas ou n'est pas connecté.");
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == p) {
                        p.sendMessage("§6§6§lIles §8» §fTu ne peux pas effectuer d'action à toi même.");
                        return true;
                    }
                    if (!IslandManager.instance.asAnIsland(p)) {
                        p.sendMessage("§6§6§lIles §8» §fTu n'es pas dans une ile.");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("invite")) {
                        if (!IslandManager.instance.asAnIsland(target)) {
                            if (IslandManager.instance.invitePlayer(p, target)) {
                                p.sendMessage("§6§6§lIles §8» §fVous avez envoyé une invitation à §e" + target.getName()
                                        + " §favec succès.");
                            } else {
                                p.sendMessage("§6§6§lIles §8» §fVous avez déjà envoyé une invitation à §e" + target.getName());
                            }
                        } else {
                            p.sendMessage("§6§6§lIles §8» §f" + target.getName() + " est déjà dans une île.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("accept")) {
                        if (!IslandManager.instance.asAnIsland(p)) {
                            if (IslandManager.instance.acceptInvite(target, p)) {
                                p.sendMessage("§6§6§lIles §8» §fVous avez accepté l'invitation de §e" + target.getName());
                            } else {
                                p.sendMessage("§6§6§lIles §8» §fVous n'avez pas reçu d'invitation de §e" + target.getName());
                            }
                        } else {
                            p.sendMessage("§6§6§lIles §8» §fTu es déjà dans une île.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("kick")) {
                        if (IslandManager.instance.promoteAndDemoteAction(p, target.getUniqueId(), target.getName(),
                                ClickType.MIDDLE, IslandManager.instance.getPlayerIsland(p))) {
                        } else {
                            p.sendMessage("§6§6§lIles §8» §fTu ne peux pas faire cette action.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("promote")) {
                        if (IslandManager.instance.promoteAndDemoteAction(p, target.getUniqueId(), target.getName(),
                                ClickType.MIDDLE, IslandManager.instance.getPlayerIsland(p))) {

                        } else {
                            p.sendMessage("§6§6§lIles §8» §fTu ne peux pas faire cette action.");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("demote")) {
                        if (IslandManager.instance.promoteAndDemoteAction(p, target.getUniqueId(), target.getName(),
                                ClickType.MIDDLE, IslandManager.instance.getPlayerIsland(p))) {

                        } else {
                            p.sendMessage("§6§6§lIles §8» §fTu ne peux pas faire cette action.");
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
                subcmd.addAll(Arrays.asList("go", "home", "invite", "accept", "kick", "promote", "demote"));
            } else if (args.length == 2) {
                subcmd.add("");
            } else {
                subcmd.add("");
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }
}
