package fr.farmeurimmo.verymc.cmd.base;

import fr.farmeurimmo.verymc.core.Main;
import fr.farmeurimmo.verymc.utils.GetTeleportDelay;
import fr.farmeurimmo.verymc.utils.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TpYesCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Main.haverequest.contains(player)) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (Main.instance1.getTarget(p.getName()) != null) {
                        if (Main.instance1.getTarget(p.getName()).equalsIgnoreCase(player.getName())) {
                            TeleportPlayer.TeleportPlayerFromRequestToAnotherPlayer(p, player, GetTeleportDelay.GetPlayerTeleportingdelay(p));
                            player.sendMessage("§6§lTéléportation §8» §fVous avez §aaccepté §fla demande de Téléportation de §6" + p.getName() + "§f.");
                            Main.pending.remove(player);
                            if (Main.haverequest.contains(player)) {
                                Main.haverequest.remove(player);
                            }
                        }
                    }
                }
            } else {
                player.sendMessage("§6§lTéléportation §8» §fVous ne possédez aucune demande de téléportation.");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("tpyes")) {
            if (args.length == 1) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
