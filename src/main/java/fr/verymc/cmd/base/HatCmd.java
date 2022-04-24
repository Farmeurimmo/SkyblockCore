package main.java.fr.verymc.cmd.base;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HatCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!player.hasPermission("hat")) {
            player.sendActionBar("§cPermissions insuffisantes !");
            return false;
        }
        ItemStack item_in_hand = player.getItemInHand();
        item_in_hand.getType();
        if (item_in_hand.getType() != Material.AIR) {
            if (player.getInventory().getHelmet() != null) {
                ItemStack helmet = player.getInventory().getHelmet();
                player.getInventory().setHelmet(item_in_hand);
                player.getItemInHand().setAmount(0);
                player.setItemInHand(helmet);
            } else {
                player.getInventory().setHelmet(item_in_hand);
                player.getItemInHand().setAmount(0);
            }
            player.sendActionBar("§aItem en main équipé !");
        } else {
            player.getInventory().addItem(player.getInventory().getHelmet());
            player.getInventory().setHelmet(null);
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("hat")) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
