package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.utils.PlayerUtils;
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
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!Main.instance.haverequest.contains(player)) {
            player.sendMessage("§6§lTéléportation §8» §fVous ne possédez aucune demande de téléportation.");
            return false;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Main.instance.getTarget(p.getName()) != null
            && Main.instance.getTarget(p.getName()).equalsIgnoreCase(player.getName())) {
                PlayerUtils.instance.teleportPlayerFromRequestToAnotherPlayer(p, player, PlayerUtils.instance.getPlayerTeleportingdelay(p));
                player.sendMessage("§6§lTéléportation §8» §fVous avez §aaccepté §fla demande de Téléportation de §6" + p.getName() + "§f.");
                Main.instance.pending.remove(player);
                Main.instance.haverequest.remove(player);
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
