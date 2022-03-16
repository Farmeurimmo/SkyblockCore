package main.java.fr.verymc.minions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinionsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 2) {
            sender.sendMessage("§6§lMinions §8» §fErreur, utilisation: /minion <pseudo> <minion>");
            return true;
        }

        if (args[0] == null) return true;
        if (Bukkit.getPlayer(args[0]) == null) return true;

        Player ptarget = Bukkit.getPlayer(args[0]);

        if (ptarget.getInventory().firstEmpty() == -1) {
            sender.sendMessage("§6§lMinions §8» §fErreur, la cible ne possède pas la place dans son inventaire.");
            return true;
        }

        if (args[1].equalsIgnoreCase("piocheur")) {
            MinionManager.instance.giveMinionItem(ptarget, MinionType.PIOCHEUR.name());
            ptarget.sendMessage("§6§lMinions §8» §fVous avez reçu §ax1 Minion Piocheur §fdans votre inventaire.");
            return true;
        }

        sender.sendMessage("Minions disponibles: Piocheur");

        return true;
    }
}
