package main.java.fr.verymc.spigot.core.cmd.moderation;

import main.java.fr.verymc.spigot.island.IslandValueCalcManager;
import main.java.fr.verymc.spigot.island.challenges.IslandChallengesReset;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reset")) {
                if (args[1].equalsIgnoreCase("challenges")) {
                    IslandChallengesReset.instance.resetAllChallenges();
                    sender.sendMessage("Challenges reset !");
                    return false;
                }
            }
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("classement")) {
                IslandValueCalcManager.instance.sendWebHookTop();
                sender.sendMessage("Top envoyé !");
                return false;
            }
        }
        sender.sendMessage("Usage /admin <action> <truc>");
        return false;
    }
}
