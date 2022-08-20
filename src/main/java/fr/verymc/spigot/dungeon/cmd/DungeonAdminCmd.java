package main.java.fr.verymc.spigot.dungeon.cmd;

import main.java.fr.verymc.spigot.dungeon.mobs.DungeonMobCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class DungeonAdminCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVous devez être un joueur pour executer cette commande.");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("spawnmob")) {
                player.sendMessage("§6§lDungeon §8» §cMerci de préciser un mob.");
                return false;
            }
        }

        if (args.length == 3) {
            int level = Integer.parseInt(args[2]);
            if (args[1].equalsIgnoreCase("zombie")) {
                if (!DungeonMobCreator.instance.zombieLvlAvailable.contains(level)) {
                    player.sendMessage("§6§lDungeon §8» §cCe niveau de zombie n'est pas disponible.");
                    return false;
                }
                player.sendMessage("§6§lDungeon §8» §fLancement du processus de spawn...");
                DungeonMobCreator.instance.spawnZombie(player.getLocation(), level);
                player.sendMessage("§6§lDungeon §8» §aProcessus terminé.");
                return false;
            }
        }

        player.sendMessage("§cUsage: /dungeon <team> <join|leave|create|delete> [nom]");
        return false;
    }

    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("dungeon")) {
            if (args.length == 1) {
                subcmd.add("create");
                subcmd.add("join");
                subcmd.add("spawnmob");
            } else if (args.length >= 2) {
                subcmd.add("zombie");
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }

}
