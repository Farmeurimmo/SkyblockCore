package main.java.fr.verymc.spigot.island.blocks;

import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class Chest {

    public int type;
    public Location block;
    public UUID owner;
    private Long chunkKey;
    private ItemStack itemToBuySell;
    private double price;
    private boolean isSell;
    private boolean activeSellOrBuy;

    public Chest(int type, Location block, UUID owner, Long chunkKey, ItemStack itemToBuySell, double price, boolean isSell,
                 boolean activeSellOrBuy) {
        this.type = type;
        this.block = block;
        this.owner = owner;
        this.chunkKey = chunkKey;
        this.itemToBuySell = itemToBuySell;
        if (price == 0) {
            price++;
        }
        this.price = price;
        this.activeSellOrBuy = activeSellOrBuy;
        this.isSell = isSell;
    }

    public static String toString(Chest c) {
        return c.getType() + ObjectConverter.SEPARATOR + ObjectConverter.instance.locationToString(c.getBlock()) + ObjectConverter.SEPARATOR + c.getOwner().toString() +
                ObjectConverter.SEPARATOR + c.getChunkKey() + ObjectConverter.SEPARATOR + ObjectConverter.instance.itemStackToString(c.getItemToBuySell() == null ?
                new ItemStack(Material.AIR, 1) : c.getItemToBuySell()) + ObjectConverter.SEPARATOR + c.getPrice() + ObjectConverter.SEPARATOR
                + c.isSell() + ObjectConverter.SEPARATOR + c.isActiveSellOrBuy();
    }

    public static Chest fromString(String str) {
        String[] splited = str.split(ObjectConverter.SEPARATOR);
        int type = Integer.parseInt(splited[0]);
        Location block = ObjectConverter.instance.locationFromString(splited[1]);
        UUID owner = UUID.fromString(splited[2]);
        long chunkKey = Long.parseLong(splited[3]);
        ItemStack itemToBuySell = ObjectConverter.instance.fromString(splited[4]);
        if (itemToBuySell.getType() == Material.AIR) {
            itemToBuySell = null;
        }
        double price = Double.parseDouble(splited[5]);
        boolean isSell = Boolean.parseBoolean(splited[6]);
        boolean activeSellOrBuy = Boolean.parseBoolean(splited[7]);
        return new Chest(type, block, owner, chunkKey, itemToBuySell, price, isSell, activeSellOrBuy);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Location getBlock() {
        return block;
    }

    public void setBlock(Location block) {
        this.block = block;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getOwnerName() {
        return Bukkit.getOfflinePlayer(owner).getName();
    }

    public Long getChunkKey() {
        return chunkKey;
    }

    public ItemStack getItemToBuySell() {
        return itemToBuySell;
    }

    public void setItemToBuySell(ItemStack itemToBuySell) {
        this.itemToBuySell = itemToBuySell;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean isSell) {
        this.isSell = isSell;
        for (Map.Entry<Player, Chest> chestEntry : PlayerShopGuis.instance.opened.entrySet()) {
            if (chestEntry.getValue().equals(this)) {
                PlayerShopGuis.instance.mainShopGui(this, chestEntry.getKey());
            }
        }
    }

    public boolean isActiveSellOrBuy() {
        return activeSellOrBuy;
    }

    public void setActiveSellOrBuy(boolean activeSellOrBuy) {
        this.activeSellOrBuy = activeSellOrBuy;
    }

}
