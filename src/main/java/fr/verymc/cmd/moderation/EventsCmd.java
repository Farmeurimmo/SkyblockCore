package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.evenement.BlocBreakerContest;
import main.java.fr.verymc.evenement.DailyBonus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EventsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("challenges")) {
                if (!DailyBonus.instance.active) {
                    DailyBonus.instance.startBonus();
                } else {
                    sender.sendMessage("§cLe bonus a déjà été lancé !");
                }
                return true;
            } else if (args[0].equalsIgnoreCase("blockbreakercontest")) {
                if (!BlocBreakerContest.instance.isActive) {
                    BlocBreakerContest.instance.startContest();
                } else {
                    sender.sendMessage("§cLe concours a déjà été lancé !");
                }
                return true;
            }
        }
        sender.sendMessage("Usage /event <type>");

        return true;
    }


}
