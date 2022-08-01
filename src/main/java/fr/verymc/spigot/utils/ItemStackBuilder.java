package main.java.fr.verymc.spigot.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {
    private final ItemStack currentItemStack;
    private final ItemMeta currentItemMeta;

    public ItemStackBuilder(final Material materialType) {
        this.currentItemStack = new ItemStack(materialType);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }

    public ItemStackBuilder(final Material materialType, final byte data) {
        this.currentItemStack = new ItemStack(materialType, 1, data);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }

    public ItemStackBuilder(final Material materialType, final int amount) {
        this.currentItemStack = new ItemStack(materialType, amount);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }

    public ItemStackBuilder(final Material materialType, final int amount, final byte data) {
        this.currentItemStack = new ItemStack(materialType, amount, data);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }

    public ItemStackBuilder(final Material materialType, final int amount, final short damage) {
        this.currentItemStack = new ItemStack(materialType, amount, damage);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }

    public ItemStackBuilder setDurability(final short durability) {
        this.currentItemStack.setDurability(durability);
        return this;
    }

    public ItemStackBuilder setName(final String name) {
        this.currentItemMeta.setDisplayName(name);
        return this;
    }

    public ItemStack getItemStack() {
        this.currentItemStack.setItemMeta(this.currentItemMeta);
        return this.currentItemStack;
    }

    public List<String> getLore() {
        return this.currentItemMeta.getLore();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ItemStackBuilder setLore(final String... lore) {
        this.currentItemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public int getAmount() {
        return this.currentItemStack.getAmount();
    }
}
