package main.java.fr.verymc.blocks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class Chest {

    public int type;
    public Location block;
    public UUID owner;
    public String ownerName;
    private Long chunkKey;
    private ItemStack itemToBuySell;
    private double price;
    private boolean isSell;
    private boolean activeSellOrBuy;

    public Chest(int type, Location block, UUID owner, String ownerName, Long chunkKey, ItemStack itemToBuySell, double price, boolean isSell,
                 boolean activeSellOrBuy) {
        this.type = type;
        this.block = block;
        this.owner = owner;
        this.ownerName = ownerName;
        this.chunkKey = chunkKey;
        this.itemToBuySell = itemToBuySell;
        if (price == 0) {
            price++;
        }
        this.price = price;
        this.activeSellOrBuy = activeSellOrBuy;
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

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
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
