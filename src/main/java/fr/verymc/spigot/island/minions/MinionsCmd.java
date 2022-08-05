package main.java.fr.verymc.spigot.island.minions;

import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MinionsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 2) {
            sender.sendMessage("§6§lMinions §8» §fErreur, utilisation: /minion <pseudo> <minion>");
            return true;
        }

        if (args[0] == null) return true;
        if (Bukkit.getPlayer(args[0]) == null) return true;

        Player ptarget = Bukkit.getPlayer(args[0]);

        if (ptarget.getInventory().firstEmpty() == -1) {
            sender.sendMessage("§6§lMinions §8» §fErreur, la cible ne possède pas la place dans son inventaire.");
            return true;
        }

        if (args[1].equalsIgnoreCase("piocheur")) {
            MinionManager.instance.giveMinionItem(ptarget, MinionType.PIOCHEUR.name());
            ptarget.sendMessage("§6§lMinions §8» §fVous avez reçu §ax1 Minion Piocheur §fdans votre inventaire.");
            sender.sendMessage("§6§lMinions §8» §fLa cible vient de recevoir §ax1 Minion Piocheur§f.");
            return true;
        }
        if (args[1].equalsIgnoreCase("repop")) {
            ArrayList<Minion> minions = new ArrayList<>();
            for (Island island : IslandManager.instance.islands) {
                minions.addAll(island.getMinions());
            }
            for (Minion minion : minions) {
                if (!minion.getBlocLocation().isChunkLoaded()) {
                    minion.getBlocLocation().getChunk().load();
                }
                MinionManager.instance.despawnMinion(minion);
                MinionManager.instance.spawnMinion(minion);
            }

        }
        if (args[1].equalsIgnoreCase("repopnearest")) {
            ArrayList<Minion> minions = new ArrayList<>();
            for (Island island : IslandManager.instance.islands) {
                minions.addAll(island.getMinions());
            }
            for (Minion minion : minions) {
                if (minion.getBlocLocation().getBlock().equals(ptarget.getLocation().getBlock())) {
                    MinionManager.instance.despawnMinion(minion);
                    MinionManager.instance.spawnMinion(minion);

                    ptarget.sendMessage("§6§lMinions §8» §aRepoped nearest minion, loc: " + minion.getBlocLocation().getX() + " " + minion.getBlocLocation().getY() + " " + minion.getBlocLocation().getZ());
                    break;
                }
            }
            return true;
        }

        sender.sendMessage("Minions disponibles: Piocheur");

        return true;
    }
}
