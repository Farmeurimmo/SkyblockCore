package main.java.fr.verymc.core.cmd.moderation;

import main.java.fr.verymc.core.cmd.utils.UtilsCmd;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GmCmd implements CommandExecutor, TabCompleter {
    public void set_player_gamemode(String gamemode, Player player) {
        switch (gamemode) {
            case "0" -> {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendActionBar("§aVous venez de passer en survie");
            }
            case "1" -> {
                player.setGameMode(GameMode.CREATIVE);
                player.sendActionBar("§aVous venez de passer en créatif");
            }
            case "2" -> {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendActionBar("§aVous venez de passer en adventure");
            }
            case "3" -> {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendActionBar("§aVous venez de passer en spectateur");
            }
            default -> player.sendActionBar("§cGamemodes disponibles: 0,1,2,3");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!player.hasPermission("gm")) {
            player.sendActionBar("§cVous n'avez pas la permission !");
            return false;
        }
        switch (args.length) {
            case 0 -> {
                player.sendActionBar("§c/gm <0,1,2,3> [Joueur]");
            }
            case 1 -> {
                set_player_gamemode(args[0], player);
                player.sendActionBar("§aVous venez de passer en " + player.getGameMode());
            }
            case 2 -> {
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendActionBar("§cCe joueur n'existe pas !");
                    return false;
                }
                if (!Bukkit.getPlayer(args[1]).isOnline()) {
                    player.sendActionBar("§cCe joueur n'est pas en ligne !");
                    return false;
                }
                Player p = Bukkit.getPlayer(args[1]);
                set_player_gamemode(args[0], p);
                p.sendActionBar("§aVous venez de passer en " + p.getGameMode());
                player.sendActionBar("§aVous venez de passer " + p.getName() + " en " + p.getGameMode());
            }
            default -> player.sendActionBar("§c/gm <0,1,2,3> [Joueur]");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("gm")) {
            switch (args.length) {
                case 1 -> {
                    subcmd.add("0");
                    subcmd.add("1");
                    subcmd.add("2");
                    subcmd.add("3");
                    Collections.sort(subcmd);
                }
                case 2 -> {
                    UtilsCmd.set_all_player_in_subcmd(subcmd);
                    Collections.sort(subcmd);
                }
                default -> {
                    subcmd.add("");
                    Collections.sort(subcmd);
                }
            }
        }
        return subcmd;
    }

}
