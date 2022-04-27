package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TpNoCmd implements CommandExecutor, TabCompleter {

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!Main.instance.haverequest.contains(player)) {
            player.sendMessage("§6§lTéléportation §8» §fVous ne possèdez aucune demande de téléportation.");
            return false;
        }
        Main.instance.haverequest.remove(player);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Main.instance.getTarget(p.getName()) != null
                    && Main.instance.getTarget(p.getName()).equalsIgnoreCase(player.getName())) {
                Main.instance.pending.remove(player.getName());
                Main.instance.tpatarget.remove(player.getName());
                p.sendMessage("§6§lTéléportation §8» §a" + player.getName() + " §fa refusé votre demande de Téléportation.");
                player.sendMessage("§6§lTéléportation §8» §fLa demande de téléportation de " + p.getName() + " §fa été refusé avec succès.");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("tpno")) {
            if (args.length == 1) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}