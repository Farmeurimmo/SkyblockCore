package main.java.fr.verymc.shopgui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AmountGuiManager implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        Material currentType = current.getType();

        if (e.getView().getTitle().equalsIgnoreCase("§6Choix de la quantité à acheter")) {
            e.setCancelled(true);
            if (currentType == Material.LIME_STAINED_GLASS_PANE) {
                int amount = e.getInventory().getItem(22).getAmount();
                int toadd = current.getAmount();
                int now = amount + toadd;
                if (now > 64) {
                    e.getInventory().getItem(22).setAmount(64);
                } else {
                    e.getInventory().getItem(22).setAmount(now);
                }
                ItemMeta temp = e.getInventory().getItem(22).getItemMeta();
                Double price = 0.0;
                if (e.getInventory().getItem(22).getType() != Material.SPAWNER) {
                    price = BuyShopItem.pricesbuy.get(new ItemStack(Material.valueOf(e.getInventory().getItem(22).getType().toString())));
                } else {
                    ItemStack ddddd = new ItemStack(e.getInventory().getItem(22).getType());
                    ddddd.setDisplayName(temp.getDisplayName());
                    price = BuyShopItem.pricesbuy.get(ddddd);
                }
                double totalcost = price * e.getInventory().getItem(22).getAmount();
                temp.setLore(Arrays.asList("§6Prix d'achat: §c" + price + "$/u", "§6Total: §c" + totalcost + "$"));
                e.getInventory().getItem(22).setItemMeta(temp);
                ItemMeta temp2 = e.getInventory().getItem(40).getItemMeta();
                temp2.setLore(Arrays.asList("§aTotal: §c" + totalcost + "$"));
                e.getInventory().getItem(40).setItemMeta(temp2);
                return;
            }
            if (currentType == Material.RED_STAINED_GLASS_PANE) {
                int amount = e.getInventory().getItem(22).getAmount();
                int toremove = current.getAmount();
                int now = amount - toremove;
                if (now < 1) {
                    e.getInventory().getItem(22).setAmount(1);
                } else {
                    e.getInventory().getItem(22).setAmount(now);
                }
                ItemMeta temp = e.getInventory().getItem(22).getItemMeta();
                Double price = 0.0;
                if (e.getInventory().getItem(22).getType() != Material.SPAWNER) {
                    price = BuyShopItem.pricesbuy.get(new ItemStack(Material.valueOf(e.getInventory().getItem(22).getType().toString())));
                } else {
                    ItemStack ddddd = new ItemStack(e.getInventory().getItem(22).getType());
                    ddddd.setDisplayName(temp.getDisplayName());
                    price = BuyShopItem.pricesbuy.get(ddddd);
                }
                double totalcost = price * e.getInventory().getItem(22).getAmount();
                temp.setLore(Arrays.asList("§6Prix d'achat: §c" + price + "$/u", "§6Total: §c" + totalcost + "$"));
                e.getInventory().getItem(22).setItemMeta(temp);
                ItemMeta temp2 = e.getInventory().getItem(40).getItemMeta();
                temp2.setLore(Arrays.asList("§aTotal: §c" + totalcost + "$"));
                e.getInventory().getItem(40).setItemMeta(temp2);
                return;
            }
            if (currentType == Material.LIME_WOOL) {
                if (current.getAmount() == 1) {
                    Double price = 0.0;
                    if (e.getInventory().getItem(22).getType() != Material.SPAWNER) {
                        price = BuyShopItem.pricesbuy.get(new ItemStack(Material.valueOf(e.getInventory().getItem(22).getType().toString())));
                    } else {
                        ItemStack ddddd = new ItemStack(e.getInventory().getItem(22).getType());
                        ddddd.setDisplayName(e.getInventory().getItem(22).getDisplayName());
                        price = BuyShopItem.pricesbuy.get(ddddd);
                    }
                    Double amount = price;
                    ItemStack od = e.getInventory().getItem(22);
                    od.setLore(null);
                    int total = od.getAmount();
                    BuyShopItem.BuyOSellItemNonStack(od, (Player) e.getWhoClicked(), true, amount, total);
                }
                return;

            }
            if (currentType == Material.GREEN_STAINED_GLASS) {
                ItemStack od = e.getInventory().getItem(22);
                GenMultiStacksBuyGui.OpenStacksAmoutShop((Player) e.getWhoClicked(), od);
                return;
            }
            if (currentType == Material.ARROW) {
                Player player = (Player) e.getWhoClicked();
                String lastpage = GenShopPage.lastpage.get(player);
                int pagecurrent = GenShopPage.lastnumpage.get(player);
                GenShopPage.OpenPreGenPage(player, lastpage, pagecurrent);
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Choix de la quantité à vendre")) {
            e.setCancelled(true);
            if (currentType == Material.LIME_STAINED_GLASS_PANE) {
                int amount = e.getInventory().getItem(22).getAmount();
                int toadd = current.getAmount();
                int now = amount + toadd;
                if (now > 64) {
                    e.getInventory().getItem(22).setAmount(64);
                } else {
                    e.getInventory().getItem(22).setAmount(now);
                }
                ItemMeta temp = e.getInventory().getItem(22).getItemMeta();
                Double price = BuyShopItem.pricessell.get(new ItemStack(Material.valueOf(e.getInventory().getItem(22).getType().toString())));
                double totalcost = price * e.getInventory().getItem(22).getAmount();
                temp.setLore(Arrays.asList("§6Prix de vente: §a" + price + "$/u", "§6Total: §a" + totalcost + "$"));
                e.getInventory().getItem(22).setItemMeta(temp);
                ItemMeta temp2 = e.getInventory().getItem(40).getItemMeta();
                temp2.setLore(Arrays.asList("§aTotal: §a" + totalcost + "$"));
                e.getInventory().getItem(40).setItemMeta(temp2);
                return;
            }
            if (currentType == Material.RED_STAINED_GLASS_PANE) {
                int amount = e.getInventory().getItem(22).getAmount();
                int toremove = current.getAmount();
                int now = amount - toremove;
                if (now < 1) {
                    e.getInventory().getItem(22).setAmount(1);
                } else {
                    e.getInventory().getItem(22).setAmount(now);
                }
                ItemMeta temp = e.getInventory().getItem(22).getItemMeta();
                Double price = BuyShopItem.pricessell.get(new ItemStack(Material.valueOf(e.getInventory().getItem(22).getType().toString())));
                double totalcost = price * e.getInventory().getItem(22).getAmount();
                temp.setLore(Arrays.asList("§6Prix de vente: §a" + price + "$/u", "§6Total: §a" + totalcost + "$"));
                e.getInventory().getItem(22).setItemMeta(temp);
                ItemMeta temp2 = e.getInventory().getItem(40).getItemMeta();
                temp2.setLore(Arrays.asList("§aTotal: §a" + totalcost + "$"));
                e.getInventory().getItem(40).setItemMeta(temp2);
                return;
            }
            if (currentType == Material.LIME_WOOL) {
                if (current.getAmount() == 1) {
                    Double price = BuyShopItem.pricesbuy.get(new ItemStack(Material.valueOf(e.getInventory().getItem(22).getType().toString())));
                    Double amount = price;
                    ItemStack od = e.getInventory().getItem(22);
                    int total = od.getAmount();
                    BuyShopItem.BuyOSellItemNonStack(od, (Player) e.getWhoClicked(), false, amount, total);
                }
                return;
            }
            if (currentType == Material.GREEN_STAINED_GLASS) {
                ItemStack od = e.getInventory().getItem(22);
                int amountinvinv = BuyShopItem.GetAmountInInv(od, (Player) e.getWhoClicked());
                Double price = BuyShopItem.pricesbuy.get(new ItemStack(Material.valueOf(e.getInventory().getItem(22).getType().toString())));
                if (amountinvinv >= 1) {
                    BuyShopItem.BuyOSellItemNonStack(od, (Player) e.getWhoClicked(), false, price, amountinvinv);
                } else {
                    BuyShopItem.BuyOSellItemNonStack(od, (Player) e.getWhoClicked(), false, price, amountinvinv);
                }
                return;
            }
            if (currentType == Material.ARROW) {
                Player player = (Player) e.getWhoClicked();
                String lastpage = GenShopPage.lastpage.get(player);
                int pagecurrent = GenShopPage.lastnumpage.get(player);
                GenShopPage.OpenPreGenPage(player, lastpage, pagecurrent);
                return;
            }
        }
    }
}
