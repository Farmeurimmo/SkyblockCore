package main.java.fr.verymc.shopgui;

import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class GenShopPage {

    public static HashMap<ItemStack, Integer> blocspage1 = new HashMap<>();
    public static HashMap<ItemStack, Integer> blocspage2 = new HashMap<>();
    public static HashMap<ItemStack, Integer> agripage = new HashMap<>();
    public static HashMap<ItemStack, Integer> foodpage = new HashMap<>();
    public static HashMap<ItemStack, Integer> colopage = new HashMap<>();
    public static HashMap<ItemStack, Integer> mineraipage = new HashMap<>();
    public static HashMap<ItemStack, Integer> autrepage = new HashMap<>();
    public static HashMap<ItemStack, Integer> lootmpage = new HashMap<>();
    public static HashMap<ItemStack, Integer> redstonepage = new HashMap<>();
    public static HashMap<ItemStack, Integer> spawneurpage = new HashMap<>();
    public static HashMap<String, EntityType> spawneurtype = new HashMap<>();

    public static HashMap<String, Integer> maxpage = new HashMap<>();

    public static HashMap<Player, String> lastpage = new HashMap<>();
    public static HashMap<Player, Integer> lastnumpage = new HashMap<>();

    public static HashMap<String, Integer> numpages = new HashMap<>();
    public static ArrayList<Integer> slotstofill = new ArrayList<Integer>();
    public static HashMap<ItemStack, Integer> toshowtemp = new HashMap<>();

    public static void GenenerateShopPageStartup(String page) {
        slotstofill.clear();
        toshowtemp.clear();
        slotstofill.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43));

        int currentpage = 1;
        int numberofitems = 0;
        for (String aa : Main.instance1.getConfig().getConfigurationSection("Shops." + page).getKeys(false)) {
            ItemStack custom1 = new ItemStack(Material.valueOf(Main.instance1.getConfig().getString("Shops." + page + "." + aa + ".material")), 1);

            numberofitems += 1;

            custom1.setItemMeta(null);
            ItemMeta meta1 = custom1.getItemMeta();

            if (custom1.getType() == Material.SPAWNER) {
                meta1.setDisplayName(Main.instance1.getConfig().getString("Shops." + page + "." + aa + ".name"));
                custom1.setItemMeta(meta1);
                spawneurtype.put(custom1.getDisplayName(), EntityType.valueOf(Main.instance1.getConfig().getString("Shops." + page + "." + aa + ".spawnertype")));
            }

            String achat = "";
            Double prixachat = BuyShopItem.pricesbuy.get(custom1);
            if (prixachat == null || prixachat == -1) {
                achat = "§cNon achetable";
            } else {
                achat = "§c" + prixachat + "$";
            }

            String vente = "";
            Double prixvente = BuyShopItem.pricessell.get(custom1);
            if (prixvente == null || prixvente == -1) {
                vente = "§cNon vendable";
            } else {
                vente = "§c" + prixvente + "$";
            }
            meta1.setLore(Arrays.asList("§6Achat: " + achat, "§6Vente: " + vente));
            custom1.setItemMeta(meta1);

            int slot = GetNextSlot();
            slotstofill.removeAll(Arrays.asList(slot));
            if (slot != 0) {
                toshowtemp.put(custom1, slot);
            }
            if (numberofitems > 28) {
                if (page.equals("Blocs")) {
                    if (currentpage == 1) {
                        for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                            blocspage1.put(cc.getKey(), cc.getValue());
                        }
                    }
                }
                toshowtemp.clear();
                if (slot == 0) {
                    slotstofill.clear();
                    slotstofill.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43));
                    slot = GetNextSlot();
                    toshowtemp.put(custom1, slot);
                }
                currentpage += 1;
                numberofitems = 0;
            }
        }
        switch (page) {
            case "Blocs":
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    blocspage2.put(cc.getKey(), cc.getValue());
                }break;
            case "Agriculture":
                agripage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    agripage.put(cc.getKey(), cc.getValue());
                }break;
            case "Nourritures":
                foodpage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    foodpage.put(cc.getKey(), cc.getValue());
                }break;
            case "Colorants":
                colopage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    colopage.put(cc.getKey(), cc.getValue());
                }break;
            case "Minerais":
                mineraipage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    mineraipage.put(cc.getKey(), cc.getValue());
                }break;
            case "Autres":
                autrepage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    autrepage.put(cc.getKey(), cc.getValue());
                }break;
            case "Drops":
                lootmpage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    lootmpage.put(cc.getKey(), cc.getValue());
                }break;
            case "Redstone":
                redstonepage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    redstonepage.put(cc.getKey(), cc.getValue());
                }break;
            case "Spawneurs":
                spawneurpage.clear();
                for (Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
                    spawneurpage.put(cc.getKey(), cc.getValue());
                }break;
        }
        slotstofill.clear();
        toshowtemp.clear();
    }

    public static void OpenPreGenPage(Player player, String page, int pagenum) {


        int numberofpage = maxpage.get(page);

        Inventory inv = Bukkit.createInventory(null, 54, "§6" + page + " " + pagenum + "/" + numberofpage);

        if (page.equals("Blocs")) {
            if (pagenum == 1) {
                for (Entry<ItemStack, Integer> cc : blocspage1.entrySet()) {
                    inv.setItem(cc.getValue(), cc.getKey());
                }
            } else {
                for (Entry<ItemStack, Integer> cc : blocspage2.entrySet()) {
                    inv.setItem(cc.getValue(), cc.getKey());
                }
            }
        } else if (page.equals("Agriculture")) {
            for (Entry<ItemStack, Integer> cc : agripage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        } else if (page.equals("Colorants")) {
            for (Entry<ItemStack, Integer> cc : colopage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        } else if (page.equals("Nourritures")) {
            for (Entry<ItemStack, Integer> cc : foodpage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        } else if (page.equals("Minerais")) {
            for (Entry<ItemStack, Integer> cc : mineraipage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        } else if (page.equals("Autres")) {
            for (Entry<ItemStack, Integer> cc : autrepage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        } else if (page.equals("Drops")) {
            for (Entry<ItemStack, Integer> cc : lootmpage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        } else if (page.equals("Redstone")) {
            for (Entry<ItemStack, Integer> cc : redstonepage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        } else if (page.equals("Spawneurs")) {
            for (Entry<ItemStack, Integer> cc : spawneurpage.entrySet()) {
                inv.setItem(cc.getValue(), cc.getKey());
            }
        }

        if (numberofpage >= 2 && pagenum < numberofpage) {
            ItemStack custom7 = new ItemStack(Material.ARROW, 1);
            ItemMeta customg = custom7.getItemMeta();
            customg.setDisplayName("§6Page suivante");
            custom7.setItemMeta(customg);
            inv.setItem(53, custom7);
        }

        if (pagenum >= 2) {
            ItemStack custom6 = new ItemStack(Material.ARROW, 1);
            ItemMeta customh = custom6.getItemMeta();
            customh.setDisplayName("§6Page pr§c§dente");
            custom6.setItemMeta(customh);
            inv.setItem(45, custom6);
        }

        ItemStack custom7 = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta customg = custom7.getItemMeta();
        customg.setDisplayName("§6Retour aux Cat§gories");
        custom7.setItemMeta(customg);
        inv.setItem(0, custom7);

        inv.setItem(49, PreGenItems.getOwnerHead(player));

        player.openInventory(inv);
        numpages.put(player.getName(), pagenum);
        lastpage.put(player, page);
        lastnumpage.put(player, pagenum);
    }

    public static int GetNextSlot() {
        int slottoreturn = 0;
        if (slotstofill.size() == 0) {
            return slottoreturn;
        }
        while (slottoreturn != slotstofill.get(0)) {
            slottoreturn += 1;
        }
        if (slotstofill.contains(slottoreturn)) {
            slotstofill.removeAll(Arrays.asList(slottoreturn));
        }
        return slottoreturn;
    }

    public static void GetNumberOfPage() {
        int items = 0;
        for (@SuppressWarnings("unused") String aa : Main.instance1.getConfig().getConfigurationSection("Shops.Blocs").getKeys(false)) {
            items += 1;
        }
        int max = items / 28;
        max += 1;
        maxpage.put("Blocs", max);
        maxpage.put("Agriculture", 1);
        maxpage.put("Nourritures", 1);
        maxpage.put("Colorants", 1);
        maxpage.put("Minerais", 1);
        maxpage.put("Autres", 1);
        maxpage.put("Drops", 1);
        maxpage.put("Redstone", 1);
        maxpage.put("Spawneurs", 1);
    }
}
