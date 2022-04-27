package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.cmd.utils.UtilsCmd;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GmLCmd implements CommandExecutor, TabCompleter {
    public void gm_command(Player player, String[] args,
                           String command_to_print, GameMode gamemode) {
        switch (args.length) {
            case 0 -> {
                player.setGameMode(gamemode);
                player.sendActionBar("§aVous venez de passer en " + gamemode);
            }
            case 1 -> {
                if (Bukkit.getPlayer(args[0]) == null) {
                    player.sendActionBar("§cCe joueur n'existe pas !");
                    return;
                }
                if (!Bukkit.getPlayer(args[0]).isOnline()) {
                    player.sendActionBar("§cCe joueur n'est pas en ligne !");
                    return;
                }
                Player p = Bukkit.getPlayer(args[0]);
                p.setGameMode(gamemode);
                p.sendActionBar("§aVous venez de passer en " + gamemode);
                player.sendActionBar("§aVous venez de passer " + p.getName() + " en " + gamemode);
            }
            default -> {
                player.sendActionBar("§c/" + command_to_print + " [Joueur]");
            }
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!player.hasPermission("*")) {
            player.sendActionBar("§cVous n'avez pas la permission !");
            return false;
        }
        switch (cmd.getName()) {
            case "gma" -> {
                gm_command(player, args, "gma", GameMode.ADVENTURE);
            }
            case "gmc" -> {
                gm_command(player, args, "gmc", GameMode.CREATIVE);
            }
            case "gms" -> {
                gm_command(player, args, "gms", GameMode.SURVIVAL);
            }
            case "gmsp" -> {
                gm_command(player, args, "gmsp", GameMode.SPECTATOR);
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("gma")
        || cmd.getName().equalsIgnoreCase("gms")
        || cmd.getName().equalsIgnoreCase("gmsp")
        || cmd.getName().equalsIgnoreCase("gmc")) {
            UtilsCmd.generate_auto_complete(args, subcmd);
        }
        return subcmd;
    }
}
