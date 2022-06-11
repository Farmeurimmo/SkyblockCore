package main.java.fr.verymc.core.auctions;

import org.bukkit.Material;
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
                AuctionsManager.instance.openAuction(player, 1);
                return true;
            }
            if (args.length == 2) {
                if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
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
                                if (AuctionsManager.instance.numberOfSelledItems(player) <= 10) {
                                    ItemStack item = player.getItemInHand().clone();
                                    Double aaa = Double.parseDouble(args[1]);
                                    if (aaa < 5) {
                                        player.sendMessage("§6§lAuctions §8» §fVous devez entrer un prix supérieur ou égal à 5.");
                                        return true;
                                    }
                                    AuctionsManager.instance.addItemToAh(player, aaa, item);
                                    player.sendMessage("§6§lAuctions §8» §fVous venez de mettre aux auctions §ax" + item.getAmount() + " " + item.getType()
                                            + " §fpour §a" + aaa + "$ §f!");
                                    player.getItemInHand().setAmount(0);
                                    return true;
                                } else {
                                    player.sendMessage("§6§lAuctions §8» §fVous avez atteint la limite d'items que vous pouvez vendre.");
                                    return true;
                                }
                            } else {
                                player.sendMessage("§6§lAuctions §8» §fPrix trop grand.");
                                return true;
                            }
                        } else {
                            player.sendMessage("§6§lAuctions §8» §fCe n'est pas un nombre valide.");
                            return true;
                        }
                    } else {
                        player.sendMessage("§6§lAuctions §8» §fErreur dans la commande, commandes disponibles: sell");
                        return true;
                    }
                } else {
                    player.sendMessage("§6§lAuctions §8» §fVous n'avez pas d'item en main.");
                    return true;
                }
            }

            player.sendMessage("§6§lAuctions §8» §fCommande inconnue, sous commandes disponible: sell");

        }

        return true;
    }
}
