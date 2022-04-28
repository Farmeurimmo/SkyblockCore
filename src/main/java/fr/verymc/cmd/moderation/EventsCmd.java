package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.evenement.BlocBreakerContest;
import main.java.fr.verymc.evenement.DailyBonus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EventsCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage /event <type>");
            return false;
        }
        switch (args[0]) {
            case "challenges" -> {
                if (DailyBonus.instance.active) {
                    sender.sendMessage("§cLe bonus a déjà été lancé !");
                    break;
                }
                DailyBonus.instance.startBonus();
            }
            case "blocbreakercontest" -> {
                if (BlocBreakerContest.instance.isActive) {
                    sender.sendMessage("§cLe concours a déjà été lancé !");
                    break;
                }
                BlocBreakerContest.instance.startContest();
            }
        }
        return false;
    }
}
