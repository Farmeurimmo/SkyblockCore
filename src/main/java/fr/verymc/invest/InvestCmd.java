package main.java.fr.verymc.invest;

import main.java.fr.verymc.storage.SkyblockUserManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            SkyblockUserManager.instance.checkForAccount(p);

            InvestManager.instance.toggleInvestMode(p);
        }


        return true;
    }
}
