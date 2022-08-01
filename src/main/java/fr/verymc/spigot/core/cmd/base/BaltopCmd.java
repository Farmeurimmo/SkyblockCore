package main.java.fr.verymc.spigot.core.cmd.base;

import main.java.fr.verymc.spigot.core.eco.BaltopManager;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaltopCmd implements CommandExecutor {
    private boolean get_if_args_is_int(String args) {
        boolean digit;
        try {
            @SuppressWarnings("unused")
            int intValue = Integer.parseInt(args);
            digit = true;
        } catch (NumberFormatException e) {
            digit = false;
        }
        return digit;
    }

    private void send_player_baltop_page(String args, Player player) {
        int intValue = Integer.parseInt(args);
        int TotalPlayers = SkyblockUserManager.instance.users.size();
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
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {
            BaltopManager.GetBaltop(player, 1, 10);
        } else if (args.length == 1) {
            boolean digit = get_if_args_is_int(args[0]);
            if (digit && !args[0].contains("-")) {
                send_player_baltop_page(args[0], player);
            } else {
                player.sendMessage("§6§lMonnaie §8» §fVeuillez saisir un numéro de page valide.");
            }
        } else {
            player.sendMessage("§6§lMonnaie §8» §fUsage, /baltop <page>");
        }
        return false;
    }
}
