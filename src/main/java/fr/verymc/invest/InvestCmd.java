package main.java.fr.verymc.invest;

import main.java.fr.verymc.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("invest.other")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                SkyblockUserManager.instance.checkForAccount(p);

                InvestManager.instance.toggleInvestMode(p);
            }
        } else {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;

                    SkyblockUserManager.instance.checkForAccount(p);

                    InvestManager.instance.toggleInvestMode(p);
                } else {
                    sender.sendMessage("§cVous devez être un joueur pour utiliser cette commande.");
                }
            } else if (args.length == 1) {
                Player p = Bukkit.getServer().getPlayer(args[0]);
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
