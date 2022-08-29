package main.java.fr.verymc.spigot.hub.invest;

import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVous ne pouvez pas utiliser cette commande dans la console.");
            return false;
        }

        Player p = (Player) sender;
        if (!sender.hasPermission("invest.other")) {
            SkyblockUserManager.instance.checkForAccount(p);
            InvestManager.instance.toggleInvestMode(p);
        } else {
            if (args.length == 0) {
                SkyblockUserManager.instance.checkForAccount(p);
                InvestManager.instance.toggleInvestMode(p);
            } else if (args.length == 1) {
                p = Bukkit.getServer().getPlayer(args[0]);
                if (p != null) {
                    SkyblockUserManager.instance.checkForAccount(p);
                    InvestManager.instance.toggleInvestMode(p);
                } else {
                    sender.sendMessage("§cCe joueur n'est pas connecté sur le serveur.");
                }
            } else {
                sender.sendMessage("§cUsage: /invest <player>");
            }
        }


        return true;
    }
}
