package main.java.fr.verymc.core.cmd.base;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.commons.enums.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlyCmd implements CommandExecutor, TabCompleter {
    public void switch_fly_player(Player player) {
        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendActionBar("§aFly activé pour " + player.getDisplayName() + " !");
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendActionBar("§aFly désactivé pour " + player.getDisplayName() + " !");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!(player.hasPermission("fly"))) {
            player.sendActionBar("§cPermissions insuffisantes.");
            return false;
        }
        if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
            switch_fly_player(Bukkit.getPlayer(args[0]));
            return false;
        }
        if (Main.instance.serverType == ServerType.ISLAND) {
            switch_fly_player(player);
            return false;
        } else {
            player.sendActionBar("§cImpossible de fly sur ce serveur !");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("fly")) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
