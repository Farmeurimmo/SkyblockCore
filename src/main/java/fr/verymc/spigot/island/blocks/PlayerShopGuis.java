package main.java.fr.verymc.spigot.island.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PlayerShopGuis {

    public static PlayerShopGuis instance;
    public HashMap<Player, Chest> opened = new HashMap<>();
    public HashMap<Player, Chest> priceEditing = new HashMap<>();
    public HashMap<Player, Chest> itemEditing = new HashMap<>();

    public PlayerShopGuis() {
        instance = this;
    }

    public void mainShopGui(Chest chest, Player player) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Player shop de " + chest.getOwnerName());

        opened.put(player, chest);

        if (chest.getOwner().equals(player.getUniqueId())) {

            if (chest.getItemToBuySell() == null) {
                ItemStack item = new ItemStack(Material.BARRIER);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§cAucun item");
                meta.setLore(Arrays.asList("§7Vous n'avez pas encore défini", "§7l'item à acheter ou à vendre", "§7Clic ici pour le changer"));
                item.setItemMeta(meta);

                inv.setItem(13, item);
            } else {
                ItemStack cloned = new ItemStack(Material.BARRIER);
                if (chest.getItemToBuySell().getType() != Material.AIR) {
                    cloned = chest.getItemToBuySell().clone();
                }
                ArrayList<String> lore = new ArrayList<>();
                if (cloned.getLore() != null) {
                    lore.addAll(cloned.getItemMeta().getLore());
                }
                lore.add("§7Clic pour changer l'item");
                cloned.setLore(lore);
                inv.setItem(13, cloned);
            }

            ItemStack item;
            if (chest.isSell()) {
                item = new ItemStack(Material.RED_WOOL);
                item.setDisplayName("§cMode achat (coffre achète au joueur)");
                item.setLore(Arrays.asList("§7Clic pour changer le mode"));
            } else {
                item = new ItemStack(Material.GREEN_WOOL);
                item.setDisplayName("§aMode vente (coffre vend au joueur)");
                item.setLore(Arrays.asList("§7Clic pour changer le mode"));
            }

            inv.setItem(10, item);

            boolean priceEdit = false;

            if (priceEditing.containsKey(player)) {
                if (priceEditing.get(player).equals(chest)) {
                    priceEdit = true;
                }
            }

            ItemStack custom1 = new ItemStack(Material.SUNFLOWER);
            custom1.setDisplayName("§6Prix: " + NumberFormat.getInstance().format(chest.getPrice()) + "$");
            custom1.setLore(Arrays.asList("§7Clic pour changer le prix", "§7Mode édition: " + (priceEdit ? "§aOui" : "§cNon")));
            inv.setItem(16, custom1);

            ItemStack custom2;
            if (chest.isActiveSellOrBuy()) {
                custom2 = new ItemStack(Material.GREEN_CONCRETE);
                custom2.setDisplayName("§aActif");
                custom2.setLore(Arrays.asList("§7Clic pour changer l'état"));
            } else {
                custom2 = new ItemStack(Material.RED_CONCRETE);
                custom2.setDisplayName("§cInactif");
                custom2.setLore(Arrays.asList("§7Clic pour changer l'état"));
            }
            inv.setItem(22, custom2);

            ItemStack help = new ItemStack(Material.KNOWLEDGE_BOOK);
            help.setDisplayName("§6Aide");
            help.setLore(Arrays.asList("§7Pour ouvrir le menu du player shop -> clic droit ou gauche",
                    "§7Pour ouvrir le menu du coffre -> shift + clic gauche.",
                    "§7Pour le casser -> shift + clic droit.",
                    "§7Les menus possèdent des inventaires indépendants.",
                    "§7Les player shop en mode édition sont accessibles",
                    "  §7uniquement par le propriétaire.",
                    "§7Le player shop doit être sur actif pour pouvoir vendre/acheter.",
                    "§7Si il est sur inactif, aucune transaction n'est possible.",
                    "§7Le prix ne peut pas être négatif et égal à 0."));
            inv.setItem(18, help);

        } else {

            if (chest.getItemToBuySell() == null) {
                ItemStack item = new ItemStack(Material.BARRIER);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§cAucun item défini");
                item.setItemMeta(meta);

                inv.setItem(10, item);
            } else {
                ItemStack cloned = chest.getItemToBuySell().clone();
                inv.setItem(10, cloned);
            }


            ItemStack item;
            if (!chest.isSell()) {
                item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            } else {
                item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            }
            for (int i = 0; i <= 6; i++) {
                item.setAmount((int) Math.pow(2, i));
                if (item.getAmount() == 2) {
                    continue;
                }
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName((chest.isSell() ? "§aVendre" : "§cAcheter") + " x" + item.getAmount());
                ArrayList<String> lore = new ArrayList<>();
                if (item.getLore() != null) {
                    lore.addAll(item.getItemMeta().getLore());
                }
                lore.add("§7Clic pour " + (chest.isSell() ? "vendre" : "acheter") + " l'item");
                lore.add("§6Prix /u: " + NumberFormat.getInstance().format(chest.getPrice()) + "$");
                lore.add("§6Prix total: " + NumberFormat.getInstance().format(chest.getPrice() * item.getAmount()) + "$");
                meta.setLore(lore);
                item.setItemMeta(meta);
                if (i >= 1) {
                    inv.setItem(11 + i - 1, item);
                } else {
                    inv.setItem(11 + i, item);
                }
                item.setLore(null);
            }
        }

        player.openInventory(inv);
    }

}
