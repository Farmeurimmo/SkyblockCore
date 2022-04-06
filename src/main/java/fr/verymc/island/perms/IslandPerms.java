package main.java.fr.verymc.island.perms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public enum IslandPerms {

    PROMOTE("§6Promouvoir un membre inférieur à lui"),
    DEMOTE("§6Rétrograder un membre inférieur à lui"),
    INVITE("§6Inviter un membre"),
    CANCEL_INVITE("§6Annuler l'invitation d'un membre"),
    KICK("§6Expulser un membre inférieur à lui"),
    BAN("§6Bannir un visiteur"),
    UNBAN("§6Débannir un visiteur"),
    SET_ISLAND_NAME("§6Définir le nom de l'île"),
    SET_ISLAND_WARP("§6Définir le warp de l'île"),
    PRIVATE("§6Définir la visibilité de l'île sur privée"),
    PUBLIC("§6Définir la visibilité de l'île sur publique"),
    CHANGE_BORDER_COLOR("§6Changer la couleur de la bordure"),
    CHANGE_ISLAND_BIOME("§6Changer le biome de l'île"),
    SET_HOME("§6Définir le home de l'île"),
    CHANGE_PERMS("§6Définir les permissions jusqu'à son niveau de grade"),
    ALL_PERMS("§6Toutes les permissions (Bypass les autres permissions)"),
    BUILD("§fConstruire"),
    BREAK("§fCasser"),
    INTERACT("§fIntéragir avec les blocs"),
    CONTAINER("§fOuvrir les conteneurs");

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
                return new ItemStack(Material.KNOWLEDGE_BOOK);
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
            case CHANGE_PERMS -> {
                return new ItemStack(Material.EMERALD_BLOCK);
            }
            case BUILD -> {
                return new ItemStack(Material.DIAMOND_PICKAXE);
            }
            case BREAK -> {
                return new ItemStack(Material.DIAMOND_AXE);
            }
            case INTERACT -> {
                return new ItemStack(Material.IRON_HOE);
            }
            case CONTAINER -> {
                return new ItemStack(Material.CHEST);
            }
            case ALL_PERMS -> {
                return new ItemStack(Material.BEACON);
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
