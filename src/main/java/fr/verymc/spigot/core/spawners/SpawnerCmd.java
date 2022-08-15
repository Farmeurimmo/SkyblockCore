package main.java.fr.verymc.spigot.core.spawners;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class SpawnerCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVous devez être un joueur pour utiliser cette commande.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("spawner")) {
            player.sendMessage("§cVous n'avez pas la permission pour utiliser cette commande.");
            return true;
        }

        if (args.length != 3) {
            player.sendMessage("§cUsage: /spawner <add> <joueur> <entitée>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§cLe joueur n'est pas connecté.");
            return true;
        }

        EntityType entityType;
        try {
            entityType = EntityType.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("§cL'entité n'existe pas.");
            return true;
        }

        SpawnersManager.instance.giveSpawner(target, entityType, 1);
        target.sendMessage("§aVous avez reçu un générateur de " + entityType);
        player.sendMessage("§aVous avez donné un générateur de " + entityType + " à " + target.getName());
        return false;
    }

    @Override
    public java.util.List<String> onTabComplete(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        ArrayList<String> toReturn = new ArrayList<>();
        if (args.length == 3) {
            EntityType[] entityTypes = EntityType.values();
            Arrays.stream(entityTypes).toList().forEach(entityType -> toReturn.add(entityType.toString()));
        }
        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                toReturn.add(player.getName());
            }
        }
        if (args.length == 1) {
            toReturn.add("add");
        }
        return toReturn;
    }
}
