package main.java.fr.verymc.spigot.core.auctions;

import main.java.fr.verymc.spigot.utils.InventorySyncUtils;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Auction {

    private UUID uuid;
    private UUID seller;
    private String sellerName;
    private ItemStack item;
    private double price;
    private long created_at;

    public Auction(UUID uuid, UUID seller, String sellerName, ItemStack item, double price, long created_at) {
        this.uuid = uuid;
        this.seller = seller;
        this.sellerName = sellerName;
        this.item = item;
        this.price = price;
        this.created_at = created_at;
    }

    public static String toString(Auction auction) {
        ItemStack[] items = new ItemStack[1];
        items[0] = auction.getItem();
        return auction.getUuid().toString() + ObjectConverter.SEPARATOR + auction.getSeller().toString() + ObjectConverter.SEPARATOR + auction.getSellerName() +
                ObjectConverter.SEPARATOR + InventorySyncUtils.instance.itemStackArrayToBase64(items) + ObjectConverter.SEPARATOR + auction.getPrice() +
                ObjectConverter.SEPARATOR + auction.getCreated_at();
    }

    public static Auction fromString(String string) {
        String[] split = string.split(ObjectConverter.SEPARATOR);
        ItemStack[] items;
        try {
            items = InventorySyncUtils.instance.itemStackArrayFromBase64(split[3]);
        } catch (Exception e) {
            return null;
        }
        return new Auction(UUID.fromString(split[0]), UUID.fromString(split[1]), split[2], items[0], Double.parseDouble(split[4]), Long.parseLong(split[5]));
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getSeller() {
        return seller;
    }

    public String getSellerName() {
        return sellerName;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public long getCreated_at() {
        return created_at;
    }
}
