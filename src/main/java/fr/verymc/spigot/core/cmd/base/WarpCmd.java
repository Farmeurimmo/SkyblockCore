package main.java.fr.verymc.spigot.core.cmd.base;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarpCmd implements CommandExecutor, TabCompleter {

    public static final Location Crates = new Location(Main.instance.mainWorld, -215.5, 66.5, -95.5, 45, 0);
    public static final Location Enchantement = new Location(Main.instance.mainWorld, -187.5, 70.5, -63.5, -125, 20);
    public static final Location Bar = new Location(Main.instance.mainWorld, -225.5, 59.5, -48.5, -180, 0);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 1) {
            String str = args[0].toLowerCase();
            switch (str) {
                case "boxes":
                    PlayerUtils.instance.teleportPlayerFromRequest(player, Crates, PlayerUtils.instance.getPlayerTeleportingdelay(player), ServerType.SKYBLOCK_HUB);
                    break;
                case "enchantement":
                    PlayerUtils.instance.teleportPlayerFromRequest(player, Enchantement, PlayerUtils.instance.getPlayerTeleportingdelay(player), ServerType.SKYBLOCK_HUB);
                    break;
                case "bar":
                    PlayerUtils.instance.teleportPlayerFromRequest(player, Bar, PlayerUtils.instance.getPlayerTeleportingdelay(player), ServerType.SKYBLOCK_HUB);
                default:
                    break;
            }
            return false;
        }
        if (args.length == 2) {
            if (!player.hasPermission("*")) {
                player.sendMessage("§c/warp <warp>");
                return false;
            }
            if (Bukkit.getPlayer(args[1]) != null
                    && Bukkit.getPlayer(args[1]).isOnline()) {
                Player p = Bukkit.getPlayer(args[1]);
                String str = args[0].toLowerCase();
                switch (str) {
                    case "boxes":
                        p.teleport(Crates);
                        p.sendActionBar("§aVous avez été téléporté au warp boxes par un membre du staff !");
                        break;
                    case "enchantement":
                        p.teleport(Enchantement);
                        p.sendActionBar("§aVous avez été téléporté au warp enchantement par un membre du staff !");
                        break;
                    case "bar":
                        p.teleport(Bar);
                        p.sendActionBar("§aVous avez été téléporté au warp bar par un membre du staff !");
                    default:
                        break;
                }
            }
            return false;
        }
        if (player.hasPermission("*")) {
            player.sendMessage("§c/warp <warp> [Joueur]");
        }
        return false;
    }

    public void get_all_player_for_tab_complete(ArrayList<String> subcmd, CommandSender sender) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            subcmd.add(player.getName());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("warp")) {
            switch (args.length) {
                case 1:
                    subcmd.add("boxes");
                    subcmd.add("enchantement");
                    subcmd.add("bar");
                    Collections.sort(subcmd);
                    break;
                case 2:
                    if (sender.hasPermission("*")) {
                        get_all_player_for_tab_complete(subcmd, sender);
                    } else {
                        subcmd.add("");
                    }
                    Collections.sort(subcmd);
                    break;
                default:
                    subcmd.add("");
                    Collections.sort(subcmd);
                    break;
            }
        }
        return subcmd;
    }
}
