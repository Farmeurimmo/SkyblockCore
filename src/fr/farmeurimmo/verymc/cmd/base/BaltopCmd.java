package fr.farmeurimmo.verymc.cmd.base;

import fr.farmeurimmo.verymc.eco.BaltopManager;
import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaltopCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                BaltopManager.GetBaltop(player, 1, 10);
            } else if (args.length == 1) {
                boolean digit = false;
                try {
                    @SuppressWarnings("unused")
                    int intValue = Integer.parseInt(args[0]);
                    digit = true;
                } catch (NumberFormatException e) {
                    digit = false;
                }
                if (!args[0].contains("-") && digit == true) {
                    int intValue = Integer.parseInt(args[0]);
                    int TotalPlayers = EcoAccountsManager.Moneys.size();
                    int TotalPage = TotalPlayers / 10 + 1;
                    if (intValue >= 1) {
                        if (intValue <= TotalPage) {
                            BaltopManager.GetBaltop(player, intValue, 10);
                        } else {
                            player.sendMessage("§6§lMonnaie §8» §fCette page n'existe pas.");
                        }
                    } else {
                        player.sendMessage("§6§lMonnaie §8» §fLe numéro de la page doit être égal ou supérieur à 1.");
                    }
                } else {
                    player.sendMessage("§6§lMonnaie §8» §fVeuillez saisir un numéro de page valide.");
                }
            } else {
                player.sendMessage("§6§lMonnaie §8» §fUsage, /baltop <page>");
            }
        }

        return false;
    }

}
