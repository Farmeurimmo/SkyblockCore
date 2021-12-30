package main.java.fr.verymc.featherfly;

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
                if (player.hasPermission("dailyfly.1heur")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "featherfly give " + player.getName() + " 1heur");
                    player.sendMessage("§aVous avez r§cup§r§ votre pl§me de fly journalier.");
                    AlreadyRedeem.add(player.getUniqueId());
                }
            } else {
                player.sendMessage("§cVous avez déjà r§cup§r§ votre fly journalier.");
            }
        }
        return false;
    }

}
