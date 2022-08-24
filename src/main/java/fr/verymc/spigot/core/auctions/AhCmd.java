package main.java.fr.verymc.spigot.core.auctions;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

public class AhCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cVous devez être un joueur pour utiliser cette commande.");
            return true;
        }

        if (args.length == 0) {
            AuctionGuis.instance.openAuction(player, 0);
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("§6§lAuctions §8» §fCommande inconnue, sous commandes disponible: sell");
            return true;
        }

        if (args[0].equalsIgnoreCase("sell")) {
            if (player.getItemInHand().getType() == Material.AIR) {
                player.sendMessage("§6§lAuctions §8» §fVous n'avez pas d'item en main.");
                return true;
            }
            double value;
            try {
                value = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("§6§lAuctions §8» §fVous devez entrer un nombre valide.");
                return true;
            }
            if (value <= 5) {
                player.sendMessage("§6§lAuctions §8» §fVous devez entrer un nombre strictement supérieur à 5.");
                return true;
            }
            if (AuctionsManager.instance.numberOfSelledItems(player) > 10) {
                player.sendMessage("§6§lAuctions §8» §fVous ne pouvez pas vendre plus de 10 items à la fois.");
            }
            ItemStack item = player.getItemInHand().clone();
            AuctionsManager.instance.addItemToAh(player, value, item);
            player.sendMessage("§6§lAuctions §8» §fVous venez de mettre aux auctions §ax" + item.getAmount() + " " + item.getType()
                    + " §fpour §a" + NumberFormat.getInstance().format(value) + "$ §f!");
            player.getItemInHand().setAmount(0);
        }

        return true;
    }
}
