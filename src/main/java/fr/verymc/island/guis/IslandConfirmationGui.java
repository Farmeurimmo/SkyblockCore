package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class IslandConfirmationGui {

    public static IslandConfirmationGui instance;

    HashMap<UUID, String> confirmations = new HashMap<>();

    public IslandConfirmationGui() {
        instance = this;
    }

    public void addConfirmation(Player player, String confirmation) {
        confirmations.put(player.getUniqueId(), confirmation);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                if (confirmations.containsKey(player.getUniqueId())) {
                    confirmations.remove(player.getUniqueId());
                    player.closeInventory();
                    player.sendMessage("§cDélai de réponse dépassé pour la confirmation.");
                }
            }
        }, 20 * 30);
    }

    public void removeConfirmation(Player player) {
        if (confirmations.containsKey(player.getUniqueId())) {
            confirmations.remove(player.getUniqueId());
            player.closeInventory();
            player.sendMessage("§cVous avez annulé la confirmation.");
        }
    }

    public void openConfirmationGui(Player player, String confirmation) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Confirmation de " + confirmation);

        ItemStack yes = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        yes.setDisplayName("§aOui");
        inv.setItem(12, yes);

        ItemStack no = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        no.setDisplayName("§cNon");
        inv.setItem(14, no);

        player.openInventory(inv);

        addConfirmation(player, confirmation);
    }
}
