package main.java.fr.verymc.items;

import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PickaxeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || args.length > 1) {
            sender.sendMessage("§cErreur, utilisation: /pickaxe <joueur>");
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§cErreur, joueur inconnu");
            return false;
        }
        Player p = Bukkit.getPlayer(args[0]);
        p.getInventory().addItem(PreGenItems.instance.getEvoPickaxe());
        p.sendMessage("§eVous avez reçu une pioche évolutive !");
        return false;
    }
}
