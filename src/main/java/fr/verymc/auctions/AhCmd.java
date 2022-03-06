package main.java.fr.verymc.auctions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AhCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                AuctionsManager.instance.OpenAuction(player, 0);
                return true;
            }
            if (args.length == 2) {
                if (player.getItemInHand() != null) {
                    if (args[0].equalsIgnoreCase("sell")) {
                        boolean digit = false;
                        try {
                            @SuppressWarnings("unused")
                            float intValue = Float.parseFloat(args[1]);
                            digit = true;
                        } catch (NumberFormatException e) {
                            digit = false;
                        }
                        if (!args[1].contains("-") && !args[1].contains(",") && digit == true) {
                            if (args[1].length() <= 9) {
                                ItemStack item = player.getItemInHand().clone();
                                Double aaa = Double.parseDouble(args[1]);
                                AuctionsManager.instance.addItemToAh(player, aaa, item);
                                player.sendMessage("§6§lMonnaie §8» §fVous venez de mettre aux auctions §ax" + item.getAmount() + " " + item.getType()
                                        + " §fpour §a" + aaa + "$ §f!");
                                player.getItemInHand().setAmount(0);
                            }
                        }
                    }
                } else {

                }
            }

        }

        return true;
    }
}
