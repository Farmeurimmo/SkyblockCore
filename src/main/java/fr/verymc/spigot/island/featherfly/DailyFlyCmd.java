package main.java.fr.verymc.spigot.island.featherfly;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class DailyFlyCmd implements CommandExecutor {

    public static ArrayList<UUID> AlreadyRedeem = new ArrayList<UUID>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!AlreadyRedeem.contains(player.getUniqueId())) {
                if (player.hasPermission("group.zeus")) {
                    player.sendMessage("§aVous n'avez plus accès au fly journalier.");
                    return true;
                }
                if (player.hasPermission("dailyfly.dieu")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "featherfly give " + player.getName() + " 3heur");
                    player.sendMessage("§aVous avez récupéré votre plûme de fly journalier.");
                    AlreadyRedeem.add(player.getUniqueId());
                    return true;
                } else if (player.hasPermission("dailyfly.legende")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "featherfly give " + player.getName() + " 90min");
                    player.sendMessage("§aVous avez récupéré votre plûme de fly journalier.");
                    AlreadyRedeem.add(player.getUniqueId());
                    return true;
                }
            } else {
                player.sendMessage("§cVous avez déjà récupéré votre fly journalier.");
            }
        }
        return false;
    }

}
