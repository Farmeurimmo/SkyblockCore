package main.java.fr.verymc.island.perms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public enum IslandPerms {

    PROMOTE("Promouvoir un membre inférieur à lui"),
    DEMOTE("Rétrograder un membre inférieur à lui"),
    INVITE("Inviter un membre"),
    CANCEL_INVITE("Annuler l'invitation d'un membre"),
    KICK("Expulser un membre inférieur à lui"),
    BAN("Bannir un visiteur"),
    UNBAN("Débannir un visiteur"),
    SET_ISLAND_NAME("Définir le nom de l'île"),
    SET_ISLAND_WARP("Définir le warp de l'île"),
    PRIVATE("Définir la visibilité de l'île sur privée"),
    PUBLIC("Définir la visibilité de l'île sur publique"),
    CHANGE_BORDER_COLOR("Changer la couleur de la bordure"),
    CHANGE_ISLAND_BIOME("Changer le biome de l'île"),
    SET_HOME("Définir le home de l'île"),
    CHANGE_PERMS("Définir les permissions jusqu'à son niveau de grade"),
    BUILD("Définir les permissions de construction"),
    BREAK("Définir les permissions de destruction"),
    ALL_PERMS("Toutes les permissions");

    private String description;

    IslandPerms(String description) {
        this.description = description;
    }

    public static ArrayList<IslandPerms> getAllPerms() {
        ArrayList<IslandPerms> perms = new ArrayList<>();
        for (IslandPerms perm : IslandPerms.values()) {
            perms.add(perm);
        }
        return perms;
    }

    public static ItemStack getItemStackForPerm(IslandPerms perm) {
        switch (perm) {
            case BAN -> {
                return new ItemStack(Material.ANVIL);
            }
            case CANCEL_INVITE -> {
                return new ItemStack(Material.LEGACY_BOOK_AND_QUILL);
            }
            case CHANGE_BORDER_COLOR -> {
                return new ItemStack(Material.BARRIER);
            }
            case CHANGE_ISLAND_BIOME -> {
                return new ItemStack(Material.CLAY);
            }
            case DEMOTE -> {
                return new ItemStack(Material.DIAMOND_SWORD);
            }
            case UNBAN -> {
                return new ItemStack(Material.DAMAGED_ANVIL);
            }
            case PRIVATE -> {
                return new ItemStack(Material.RED_BED);
            }
            case PUBLIC -> {
                return new ItemStack(Material.BLUE_BED);
            }
            case PROMOTE -> {
                return new ItemStack(Material.NETHERITE_SWORD);
            }
            case INVITE -> {
                return new ItemStack(Material.BOOK);
            }
            case KICK -> {
                return new ItemStack(Material.LEATHER_BOOTS);
            }
            case SET_HOME -> {
                return new ItemStack(Material.GRASS_BLOCK);
            }
            case SET_ISLAND_NAME -> {
                return new ItemStack(Material.NAME_TAG);
            }
            case SET_ISLAND_WARP -> {
                return new ItemStack(Material.COMPASS);
            }
            default -> {
                return new ItemStack(Material.AIR);
            }
        }
    }

    public String getDescription() {
        return description;
    }
}
