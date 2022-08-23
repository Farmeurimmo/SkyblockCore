package main.java.fr.verymc.spigot.core.auctions;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AuctionsManager {

    public static final String BASE_STR_NAME = "§6Auctions #";
    public static final String BASE_STR_LISTED = "§6Items listés";
    public static final String BASE_STR_UUID = "uuidProduct";
    public static AuctionsManager instance;
    public LinkedList<Inventory> invs = new LinkedList<>();
    public LinkedList<Auction> auctions = new LinkedList<>();
    public HashMap<UUID, Integer> open = new HashMap<>();
    public ArrayList<UUID> openListed = new ArrayList<>();

    public AuctionsManager() {
        instance = this;
        genAuctionsPage(false);
    }

    //readFromStringOfAh(); -> read data from a string
    //getStringOfAh() -> return a string of the ah

    public String getStringOfAh() {
        String toReturn = "";
        for (Auction auction : auctions) {
            toReturn += Auction.toString(auction) + ObjectConverter.SEPARATOR_ELEMENT;
        }
        if (toReturn.length() > 0) toReturn = toReturn.replace(toReturn.substring(toReturn.length() - 1), "");
        return toReturn;
    }

    public void readFromStringOfAh(String str) {
        auctions.clear();
        String[] split = str.split(ObjectConverter.SEPARATOR_ELEMENT);
        for (String s : split) {
            Auction auction = Auction.fromString(s);
            if (auction != null) {
                auctions.add(auction);
            }
        }
    }

    public LinkedList<Integer> getSlots() {
        return new LinkedList<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31,
                32, 33, 34, 37, 38, 39, 40, 41, 42, 43));
    }

    public void genAuctionsPage(boolean refresh) {
        ArrayList<Auction> auctionsDone = new ArrayList<>();
        int currentPage = 1;
        invs.clear();
        while (auctionsDone.size() < auctions.size()) {
            LinkedList<Integer> slots = getSlots();
            Inventory inv = Bukkit.createInventory(null, 54, BASE_STR_NAME + currentPage);
            for (Auction auction : auctions) {

                if (auctionsDone.contains(auction)) continue;
                if (slots.size() == 0) break;
                int slot = slots.removeFirst();

                ItemStack item = auction.getItem().clone();
                applyUUIDAuction(item, auction);

                ArrayList<String> lore = (item.getLore() == null) ? new ArrayList<>() : new ArrayList<>(item.getLore());
                lore.add("");
                lore.add("§7Vendeur: §6" + auction.getSellerName());
                lore.add("§7Prix: §6" + NumberFormat.getInstance().format(auction.getPrice()));
                lore.add("§7Ajouté il y a: §6" + TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - auction.getCreated_at()) + "h" +
                        TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - auction.getCreated_at()) + "m" +
                        TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - auction.getCreated_at()) + "s");
                item.setLore(lore);

                inv.setItem(slot, item);
                auctionsDone.add(auction);
            }
            invs.add(inv);
        }
        if (invs.size() == 0) {
            invs.add(Bukkit.createInventory(null, 54, BASE_STR_NAME + currentPage));
        }
        updateInv();
        if (refresh) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            genAuctionsPage(false);
        }, 20L * 5);
    }

    public void updateInv() {
        HashMap<Player, Integer> opened = new HashMap<>();
        for (Map.Entry<UUID, Integer> entry : open.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player == null) continue;
            opened.put(player, entry.getValue());
        }
        ArrayList<Player> openedListed = new ArrayList<>();
        for (UUID uuid : openListed) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            openedListed.add(player);
        }
        for (Map.Entry<Player, Integer> entry : opened.entrySet()) {
            AuctionGuis.instance.openAuction(entry.getKey(), entry.getValue());
        }
        for (Player player : openedListed) {
            AuctionGuis.instance.auctionnedListGui(player);
        }
    }

    public ItemStack applyUUIDAuction(ItemStack itemStack, Auction auction) {
        itemStack.addAttributeModifier(Attribute.GENERIC_FOLLOW_RANGE,
                new AttributeModifier(auction.getUuid(), BASE_STR_UUID, 0, AttributeModifier.Operation.ADD_NUMBER));
        itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return itemStack;
    }

    public Inventory getInvFromInt(Integer page) {
        if (AuctionsManager.instance.invs.get(page) == null) {
            return AuctionsManager.instance.invs.getLast();
        } else {
            return AuctionsManager.instance.invs.get(page);
        }
    }

    public LinkedList<Auction> listed(Player player) {
        LinkedList<Auction> items = new LinkedList<>();
        for (Auction auction : auctions) {
            if (auction.getSeller().equals(player.getUniqueId())) {
                items.add(auction);
            }
        }
        return items;
    }

    public int numOfPage() {
        return invs.size();
    }

    public Auction getFromUUIDProduct(UUID uuid) {
        for (Auction auction : auctions) {
            if (auction.getUuid().equals(uuid)) {
                return auction;
            }
        }
        return null;
    }

    public void deListItemFromAh(Auction auction) {
        auctions.remove(auction);
        genAuctionsPage(true);
    }

    public void buyItemFromAh(Player player, Auction auction) {
        if (auction == null) {
            player.sendMessage("§6§lAuctions §8» §cCet item n'est pas en vente ou n'est plus en vente.");
            return;
        }
        if (auction.getSeller().equals(player.getUniqueId())) {
            player.sendMessage("§6§lAuctions §8» §cVous ne pouvez pas achetez vos propres items !");
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage("§6§lAuctions §8» §cVous n'avez pas assez de place dans votre inventaire.");
            return;
        }
        if (!EcoAccountsManager.instance.checkForFounds(player, auction.getPrice())) {
            player.sendMessage("§6§lAuctions §8» §cVous n'avez pas assez d'argent sur votre compte.");
            return;
        }
        EcoAccountsManager.instance.removeFounds(player, auction.getPrice(), true);
        EcoAccountsManager.instance.addFoundsUUID(auction.getSeller(), auction.getPrice(), true);
        player.getInventory().addItem(auction.getItem());
        deListItemFromAh(auction);
    }

    public void addItemToAh(Player player, Double price, ItemStack tosell) {
        Auction auction = new Auction(UUID.randomUUID(), player.getUniqueId(), player.getName(), tosell, price, System.currentTimeMillis());
        auctions.addLast(auction);
    }

    public int numberOfSelledItems(Player player) {
        int toReturn = 0;
        for (Auction auction : auctions) {
            if (auction.getSeller().equals(player.getUniqueId())) {
                toReturn++;
            }
        }
        return toReturn;
    }

}
