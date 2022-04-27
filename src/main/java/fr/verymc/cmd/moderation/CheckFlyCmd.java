package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.cmd.utils.UtilsCmd;
import main.java.fr.verymc.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CheckFlyCmd implements CommandExecutor, TabCompleter {
    public String get_color(boolean param) {
        if (param) {
            return ("§c§l");
        } else {
            return ("§a§l");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 1) {
            player.sendActionBar("§c/checkfly <Joueur>");
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
        player.sendMessage("§6Gestion du fly de " + p.getName() + ": \n§6Permission de voler "
                + get_color(!p.getAllowFlight()) + p.getAllowFlight() + "\n§6En vol: " + get_color(!p.isFlying())
                + p.isFlying() + "\nTemp fly: " + SkyblockUserManager.instance.getUser(p.getUniqueId()).getFlyLeft());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("checkfly")) {
            UtilsCmd.generate_auto_complete(args, subcmd);
        }
        return subcmd;
    }
}
