package main.java.fr.verymc.core.cmd.moderation;

import main.java.fr.verymc.core.cmd.utils.UtilsCmd;
import main.java.fr.verymc.core.gui.AfkMineCaptchaGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AntiAfkMineCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 1) {
            player.sendActionBar("§c/afkmine <Joueur>");
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendActionBar("§cCe joueur n'existe pas !");
            return false;
        }
        if (!Bukkit.getPlayer(args[0]).isOnline()) {
            player.sendActionBar("§cCe joueur n'est pas en ligne !");
            return false;
        }
        Player p = Bukkit.getPlayer(args[0]);
        AfkMineCaptchaGui.MakeAfkMineCaptchaGui(p);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("afkmine")) {
            UtilsCmd.generate_auto_complete(args, subcmd);
        }
        return subcmd;
    }
}