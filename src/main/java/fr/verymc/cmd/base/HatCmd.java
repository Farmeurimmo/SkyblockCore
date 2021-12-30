package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.utils.SendActionBar;
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

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("hat")) {
                ItemStack aaa = player.getItemInHand();
                if (aaa.getType() != null && aaa.getType() != Material.AIR) {
                    if (player.getInventory().getHelmet() != null) {
                        ItemStack bbb = player.getInventory().getHelmet();
                        player.getInventory().setHelmet(aaa);
                        player.getItemInHand().setAmount(0);
                        player.setItemInHand(bbb);
                    } else {
                        player.getInventory().setHelmet(aaa);
                        player.getItemInHand().setAmount(0);
                    }
                    SendActionBar.SendActionBarMsg(player, "§aItem en main §quip§ !");
                } else {
                    player.getInventory().addItem(player.getInventory().getHelmet());
                    player.getInventory().setHelmet(null);
                }
            } else {
                SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes !");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("hat")) {
            if (args.length >= 0) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
