package main.java.fr.verymc.shopgui;

import main.java.fr.verymc.eco.EcoAccountsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.IntStream;

public class SellAllCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        double total = 0;
        ItemStack[] ddd = IntStream.range(0, 31).boxed().map(player.getInventory()::getItem).toArray(ItemStack[]::new);
        for (ItemStack a : ddd) {
            if (a == null) continue;
            if (a.getType() == null) continue;
            ItemStack searched = new ItemStack(a.getType());
            searched.setAmount(a.getAmount());
            if (BuyShopItem.pricessell.get(new ItemStack(a.getType())) != null && BuyShopItem.pricessell.get(new ItemStack(a.getType())) > 0) {
                int amount = BuyShopItem.GetAmountInInv(searched, player);
                Double price = BuyShopItem.pricessell.get(new ItemStack(a.getType()));
                price = amount * price;
                BuyShopItem.removeItems(player.getInventory(), searched.getType(), amount);
                total += price;
            }
        }
        if (total > 0) EcoAccountsManager.instance.AddFounds(player, total, false);
        player.sendMessage("§6§lShop §8» §fVous avez vendu tout les items vendables de votre inventaire pour §a" + total + "$.");

        return false;
    }

}
