package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.challenges.ChallengesGuis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BoostCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("challenges")) {
                int boost = Integer.parseInt(args[1]);
                sender.sendMessage("Boost challenges = x" + boost);
                ChallengesGuis.boost = boost;
                return true;
            }
        }
        sender.sendMessage("Usage /boost <type> <nombre>");

        return true;
    }


}
