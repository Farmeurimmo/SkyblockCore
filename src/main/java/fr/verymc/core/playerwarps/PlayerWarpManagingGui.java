package main.java.fr.verymc.core.playerwarps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;

public class PlayerWarpManagingGui {

    public static PlayerWarpManagingGui instance;
    public HashMap<Player, PlayerWarp> creationMode = new HashMap<>();
    public HashMap<Player, Location> oldLocation = new HashMap<>();
    public HashMap<Player, String> oldName = new HashMap<>();

    public PlayerWarpManagingGui() {
        instance = this;
    }

    public void openManagingMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu de gestion du warp joueur");

        PlayerWarp playerWarp = PlayerWarpManager.instance.getPlayerWarpFromUUID(player.getUniqueId());

        if (playerWarp != null) {

            ItemStack item = new ItemStack(Material.COMPASS);
            item.setDisplayName("§6Changer la localisation du warp");
            item.setLore(Arrays.asList("§7Clic pour changer la localisation du warp"));

            ItemStack item2 = new ItemStack(Material.NAME_TAG);
            item2.setDisplayName("§6Nom du warp");
            item2.setLore(Arrays.asList("§7" + playerWarp.getName(), "", "§7Clic pour changer le nom"));

            ItemStack item3 = new ItemStack(Material.ENDER_PEARL);
            item3.setDisplayName("§6Téléportation au warp");
            item3.setLore(Arrays.asList("§7Clic pour se téléporter au warp"));

            ItemStack item4 = new ItemStack(Material.NETHERITE_BLOCK);
            item4.setDisplayName("§6Promouvoir le warp");
            item4.setLore(Arrays.asList("§7Promu: " + (playerWarp.getTimeLeftPromoted() > 0 ? "§aOui" : "§cNon \n§7Clic pour changer le statut")));

            ItemStack item5 = new ItemStack(Material.PAPER);
            item5.setDisplayName("§6Informations complémentaires");
            item5.setLore(Arrays.asList("§7Vues totales: " + NumberFormat.getInstance().format(playerWarp.getVues()), "§7Note moyenne: " +
                    (playerWarp.getNote() < 0 ? "§cAucune note" : NumberFormat.getInstance().format(playerWarp.getNote()))));


            ItemStack item6 = new ItemStack(Material.BARRIER);
            item6.setDisplayName("§6Supprimer le warp");
            item6.setLore(Arrays.asList("§7Clic pour supprimer le warp"));

            inv.setItem(12, item);
            inv.setItem(13, item5);
            inv.setItem(14, item2);
            inv.setItem(10, item3);
            inv.setItem(16, item4);
            inv.setItem(26, item6);

        } else {
            ItemStack item = new ItemStack(Material.BARRIER);
            item.setDisplayName("§cVous n'avez pas de warp");
            item.setLore(Arrays.asList("§7Clic pour en créer un warp joueur"));

            inv.setItem(13, item);
        }

        ItemStack item7 = new ItemStack(Material.BOOK);
        item7.setDisplayName("§6Liste de warps");
        item7.setLore(Arrays.asList("§7Clic pour ouvrir la liste de tous les warps"));
        inv.setItem(22, item7);

        player.openInventory(inv);

    }

}
