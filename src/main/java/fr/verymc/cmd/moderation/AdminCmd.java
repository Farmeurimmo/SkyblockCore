package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.island.challenges.IslandChallengesReset;
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
        sender.sendMessage("Usage /admin <action> <truc>");
        return false;
    }
}
