package main.java.fr.verymc.auctions;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AuctionsManager {

    public static AuctionsManager instance;

    public HashMap<Long, ItemStack> ahtype = new HashMap<>();

    public HashMap<Long, Double> ahprice = new HashMap<>();

    public HashMap<Long, String> ahseller = new HashMap<>();

    public HashMap<Long, UUID> ahsellerUUID = new HashMap<>();

    public HashMap<Integer, Inventory> ahinv = new HashMap<>();

    public ArrayList<Integer> slotstofill = new ArrayList<>();

    public HashMap<Long, ItemStack> alreadysaw = new HashMap<>();

    public HashMap<String, Integer> open = new HashMap<>();
    public ArrayList<String> auctionned = new ArrayList<>();

    public AuctionsManager() {
        instance = this;
        addSlots();
        readForAuctions();
        autoUpdateInv();
    }

    public void addSlots() {
        slotstofill.clear();
        slotstofill.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31,
                32, 33, 34, 37, 38, 39, 40, 41, 42, 43));
    }

    public int getNextSlot() {
        int slottoreturn = 0;
        if (slotstofill.size() == 0) {
            return slottoreturn;
        }
        while (slottoreturn != slotstofill.get(0)) {
            slottoreturn += 1;
        }
        return slottoreturn;
    }

    public void autoUpdateInv() {
        addSlots();
        alreadysaw.clear();
        ahinv.clear();
        int currentpage = 1;

        while (true) {
            Inventory inv = Bukkit.createInventory(null, 54, "§6Auctions #" + currentpage);
            for (Map.Entry<Long, ItemStack> aa : ahtype.entrySet()) {
                if (slotstofill.size() >= 1 && !alreadysaw.containsKey(aa.getKey())) {
                    int slot = getNextSlot();
                    slotstofill.remove(new Integer(slot));
                    ItemStack itemCloned = aa.getValue().clone();
                    inv.setItem(slot, itemCloned);
                    alreadysaw.put(aa.getKey(), itemCloned);
                }
                continue;
            }
            ahinv.put(currentpage, inv);
            if (alreadysaw.size() >= ahtype.size()) break;
            addSlots();
            currentpage += 1;
        }

        HashMap<String, Integer> open2 = new HashMap<>();
        open2 = (HashMap<String, Integer>) open.clone();
        for (Map.Entry<String, Integer> plays : open2.entrySet()) {
            if (Bukkit.getPlayer(plays.getKey()) != null) {
                openAuction(Bukkit.getPlayer(plays.getKey()), plays.getValue());
            }
        }
        for (String aa : auctionned) {
            if (Bukkit.getPlayer(aa) != null) {
                auctionnedListGui(Bukkit.getPlayer(aa));
            }
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                autoUpdateInv();
            }
        }, 30);
    }

    public void deListItemFromAh(long millis) {
        if (ahtype.containsKey(millis)) ahtype.remove(millis);
        if (ahseller.containsKey(millis)) ahseller.remove(millis);
        if (ahsellerUUID.containsKey(millis)) ahsellerUUID.remove(millis);
        if (ahprice.containsKey(millis)) ahprice.remove(millis);

        Main.instance.getDataah().set("auction." + millis, null);
        Main.instance.saveDataah();
    }

    public void buyItemFromAh(Player player, long millis) {
        double price = ahprice.get(millis);
        UUID sellerUUID = ahsellerUUID.get(millis);
        String sellerString = ahseller.get(millis);
        ItemStack itemBuyed = ahtype.get(millis).clone();

        if (EcoAccountsManager.instance.checkForFounds(player, price)) {

            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage("§6§lAuctions §8» §fVous n'avez pas asser de place dans votre inventaire.");
                return;
            }

            ArrayList<String> lore = new ArrayList<>();
            ArrayList<String> lorecloned = new ArrayList<>();
            lore.addAll(itemBuyed.getLore());
            for (String aa : lore) {
                if (aa.contains(millis + "")) {
                    lorecloned.add(millis + "");
                }
            }
            for (String ae : lorecloned) {
                lore.remove(ae + "");
            }

            itemBuyed.setLore(lore);

            EcoAccountsManager.instance.removeFounds(player, price, false);

            player.getInventory().addItem(itemBuyed);

            player.sendMessage("§6§lAuctions §8» §fVous venez d'acheter §ax" + itemBuyed.getAmount() + " " + itemBuyed.getType() + " §fpour §c" + price + "$§f.");

            UUID playeruuid = ahsellerUUID.get(millis);
            if (playeruuid == null) {
                return;
            }
            EcoAccountsManager.instance.addFoundsUUID(playeruuid, price, false);
            deListItemFromAh(millis);
            if (Bukkit.getPlayer(playeruuid) == null) {
                return;
            }
            if (Bukkit.getPlayer(playeruuid).isOnline()) {
                Bukkit.getPlayer(playeruuid).sendMessage("§6§lAuctions §8» §a" + player.getName() + "§f vient d'acheter un item que vous aviez mis aux auctions" +
                        " pour §a" + price + "$§f.");
            }
            return;
        } else {
            double loa = price - EcoAccountsManager.instance.getMoneyUUID(player.getUniqueId());
            player.sendMessage("§6§lShop §8» §fIl vous manque §6" + loa + "$§f.");
            return;
        }
    }

    public void openAuction(Player player, int page) {

        ItemStack custom4 = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom4.setItemMeta(customd);

        ItemStack custom6 = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta customc = custom6.getItemMeta();
        customc.setDisplayName("§6Items listés §8| §7(clic gauche)");
        custom6.setItemMeta(customc);

        ItemStack custom5 = new ItemStack(Material.ARROW, 1);
        ItemMeta customa = custom5.getItemMeta();
        customa.setDisplayName("§6Page suivante §8| §7(clic gauche)");
        custom5.setItemMeta(customa);

        ItemStack custom3 = new ItemStack(Material.ARROW, 1);
        ItemMeta customb = custom3.getItemMeta();
        customb.setDisplayName("§6Page précédente §8| §7(clic gauche)");
        custom3.setItemMeta(customb);


        for (Map.Entry<Integer, Inventory> invs : ahinv.entrySet()) {
            if (invs.getKey() == page) {
                for (ItemStack ae : invs.getValue().getContents()) {
                    if (ae == null) continue;
                    if (!ahtype.containsValue(ae)) continue;
                    if (ae.getLore() == null) continue;
                    int loresize = ae.getLore().size() - 1;
                    String toparse = ae.getLore().get(loresize);
                    long millis = Long.parseLong(toparse);
                    if (ae.getLore() == null) {
                        ae.setLore(Arrays.asList("§f§6§f---------------", "§f§6Prix: " + ahprice.get(millis),
                                "§f§6Vendeur: " + ahseller.get(millis), "§f§6§f---------------"));
                    } else {
                        ArrayList<String> lore = new ArrayList<>();
                        lore.addAll(ae.getLore());
                        lore.addAll(Arrays.asList("§f§6§f---------------", "§f§6§6Prix: " + ahprice.get(millis),
                                "§f§6§6Vendeur: " + ahseller.get(millis), "§f§6§f---------------"));
                        ae.setLore(lore);
                    }
                }
                if (ahinv.size() > page) invs.getValue().setItem(53, custom5);
                if (page > 1) invs.getValue().setItem(45, custom3);
                invs.getValue().setItem(49, custom4);
                invs.getValue().setItem(47, custom6);
                player.openInventory(invs.getValue());
                if (open.containsKey(player.getName())) {
                    open.remove(player.getName());
                }
                open.put(player.getName(), 1);
                return;
            }
        }
        for (Map.Entry<Integer, Inventory> invs : ahinv.entrySet()) {
            if (invs.getKey() == 1) {
                for (ItemStack ae : invs.getValue().getContents()) {
                    if (ae == null) continue;
                    if (!ahtype.containsValue(ae)) continue;
                    if (ae.getLore() == null) continue;
                    int loresize = ae.getLore().size() - 1;
                    String toparse = ae.getLore().get(loresize);
                    long millis = Long.parseLong(toparse);
                    if (ae.getLore() == null) {
                        ae.setLore(Arrays.asList("§f§6§f---------------", "§f§6Prix: " + ahprice.get(millis),
                                "§f§6Vendeur: " + ahseller.get(millis), "§f§6§f---------------"));
                    } else {
                        ArrayList<String> lore = new ArrayList<>();
                        lore.addAll(ae.getLore());
                        lore.addAll(Arrays.asList("§f§6§f---------------", "§f§6§6Prix: " + ahprice.get(millis),
                                "§f§6§6Vendeur: " + ahseller.get(millis), "§f§6§f---------------"));
                        ae.setLore(lore);
                    }
                }
                if (ahinv.size() > page) invs.getValue().setItem(53, custom5);
                if (page > 1) invs.getValue().setItem(45, custom3);
                invs.getValue().setItem(49, custom4);
                invs.getValue().setItem(47, custom6);
                player.openInventory(invs.getValue());
                if (open.containsKey(player.getName())) {
                    open.remove(player.getName());
                }
                open.put(player.getName(), 1);
                return;
            }
        }
    }

    public void auctionnedListGui(Player player) {

        Inventory inv = Bukkit.createInventory(null, 54, "§6Items listés");

        ItemStack custom4 = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom4.setItemMeta(customd);

        inv.setItem(49, custom4);

        ArrayList<Integer> slotstofill = new ArrayList<>();

        slotstofill.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31,
                32, 33, 34, 37, 38, 39, 40, 41, 42, 43));

        int slot = 0;
        for (Map.Entry<Long, ItemStack> items : ahtype.entrySet()) {
            ItemStack item = items.getValue().clone();
            int loresize = item.getLore().size() - 1;
            String toparse = item.getLore().get(loresize);
            long millis = Long.parseLong(toparse);
            if (ahseller.containsKey(millis) && ahseller.get(millis).equals(player.getName()) && ahsellerUUID.get(millis).equals(player.getUniqueId())) {
                while (!slotstofill.contains(slot)) {
                    slot += 1;
                    if (slot > 44) break;
                }
                inv.setItem(slot, items.getValue());
                slotstofill.remove(new Integer(slot));
            }
        }

        player.openInventory(inv);
        auctionned.add(player.getName());
    }

    public void readForAuctions() {
        if (Main.instance.getDataah().getConfigurationSection("auction") != null) {
            for (String aa : Main.instance.getDataah().getConfigurationSection("auction").getKeys(false)) {
                long millis = Long.parseLong(aa);
                double price = Main.instance.getDataah().getDouble("auction." + millis + ".price");
                ItemStack tosell = Main.instance.getDataah().getItemStack("auction." + millis + ".itemStack");
                String seller = Main.instance.getDataah().getString("auction." + millis + ".seller");
                UUID sellerUUID = UUID.fromString(Main.instance.getDataah().getString("auction." + millis + ".sellerUUID"));

                if (tosell.getLore() == null) {
                    tosell.setLore(Arrays.asList(millis + ""));
                } else {
                    ArrayList<String> lore = new ArrayList<>();
                    lore.addAll(tosell.getLore());
                    if (!lore.contains(millis + "")) {
                        lore.add(millis + "");
                    }
                    tosell.setLore(lore);
                }
                ahtype.put(millis, tosell);
                ahprice.put(millis, price);
                ahseller.put(millis, seller);
                ahsellerUUID.put(millis, sellerUUID);
            }
        }
    }

    public void addItemToAh(Player player, Double price, ItemStack tosell) {
        Long millis = System.currentTimeMillis();

        Main.instance.getDataah().set("auction." + millis + ".price", price);
        Main.instance.getDataah().set("auction." + millis + ".itemStack", tosell);
        Main.instance.getDataah().set("auction." + millis + ".seller", player.getName());
        Main.instance.getDataah().set("auction." + millis + ".sellerUUID", player.getUniqueId().toString());
        Main.instance.saveDataah();

        if (tosell.getLore() == null) {
            tosell.setLore(Arrays.asList(millis + ""));
        } else {
            ArrayList<String> lore = new ArrayList<>();
            lore.addAll(tosell.getLore());
            lore.add(millis + "");
            tosell.setLore(lore);
        }

        ahtype.put(millis, tosell);
        ahprice.put(millis, price);
        ahseller.put(millis, player.getName());
        ahsellerUUID.put(millis, player.getUniqueId());
    }

    public int numberOfSelledItems(Player player) {
        int toReturn = 0;
        String playername = player.getName();
        for (Map.Entry<Long, String> items : ahseller.entrySet()) {
            if (playername == items.getValue()) {
                toReturn += 1;
            }
        }
        return toReturn;
    }

}
