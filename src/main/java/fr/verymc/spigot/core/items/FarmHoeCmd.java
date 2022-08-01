package main.java.fr.verymc.spigot.core.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FarmHoeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1 || args.length > 1) {
            sender.sendMessage("§cErreur, utilisation: /farmhoe <joueur>");
            return true;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§cErreur, joueur inconnu");
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);

        ItemStack custom6 = new ItemStack(Material.NETHERITE_HOE, 1);
        ItemMeta customf = custom6.getItemMeta();
        customf.setDisplayName("§6FarmHoe Tier §cI (1X1)");
        customf.setLore(Arrays.asList("§70", "§7", "§6Clic droit pour ouvrir le menu d'amélioration"));
        customf.setUnbreakable(true);
        custom6.setItemMeta(customf);
        p.getInventory().addItem(custom6);
        p.sendMessage("§eVous avez reçu une farm hoe !");

        return false;
    }

}
