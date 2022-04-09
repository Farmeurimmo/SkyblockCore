package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.island.upgrade.IslandUpgradesType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class IslandUpgradeGui {

    public static IslandUpgradeGui instance;

    public IslandUpgradeGui() {
        instance = this;
    }

    public void openUpgradeIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 27, "§6Améliorations de l'île");

        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        int sizeIsland = playerIsland.getSizeUpgrade().getSize();

        ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta custom1Meta = custom1.getItemMeta();
        custom1Meta.setDisplayName("§6Taille de l'île");
        custom1Meta.setLore(Arrays.asList("§7Taille actuelle : §6" + sizeIsland + "§7x§6" + sizeIsland, "", "§7Niveaux:", "§70: §650§7x§650"
                , "§71: §6100§7x§6100", "§72: §6150§7x§6150", "§73: §6200§7x§6200", "§74: §6250§7x§6250", "", "§7Clic pour améliorer"));
        custom1.setItemMeta(custom1Meta);
        inv.setItem(10, custom1);

        ItemStack custom2 = new ItemStack(Material.COBBLESTONE);
        ItemMeta custom2Meta = custom2.getItemMeta();
        custom2Meta.setDisplayName("§6Générateur de l'île");
        ArrayList<String> custom2Lore = new ArrayList<>();
        custom2Lore.add("§7Niveau actuel : §6" + playerIsland.getGeneratorUpgrade().getLevel());
        custom2Lore.add("");
        custom2Lore.add("§7Niveaux: ");
        for (int i = 0; i <= 5; i++) {
            IslandUpgradeGenerator generator = new IslandUpgradeGenerator(i, IslandUpgradesType.GENERATOR, true);
            generator.setLevel(i);
            String str = "§7" + i + ": ";
            String str2 = "";
            for (Map.Entry<Material, Integer> entry : generator.getMaterials().entrySet()) {
                if (str.length() <= 70) {
                    str += "§6" + entry.getKey().name() + " §7" + entry.getValue() + "% §6";
                } else {
                    if (str2.isEmpty()) {
                        str2 += "  ";
                    }
                    str2 += "§6" + entry.getKey().name() + " §7" + entry.getValue() + "% §6";
                }
            }
            custom2Lore.add(str);
            if (str2 != null && !str2.isEmpty()) {
                custom2Lore.add(str2);
            }
        }
        custom2Lore.replaceAll(s -> s.replace("_ORE", ""));
        custom2Lore.replaceAll(s -> s.replace("COBBLESTONE", "COBB"));
        custom2Lore.replaceAll(s -> s.replace("DIAMOND", "DIAMS"));
        custom2Lore.replaceAll(s -> s.replace("EMERALD", "EMER"));
        custom2Lore.replaceAll(s -> s.replace("ANCIENT_", ""));
        custom2Meta.setLore(custom2Lore);
        custom2.setItemMeta(custom2Meta);
        inv.setItem(14, custom2);

        ItemStack custom3 = new ItemStack(Material.PAPER);
        ItemMeta custom3Meta = custom3.getItemMeta();
        custom3Meta.setDisplayName("§6Nombre de membres");
        custom3Meta.setLore(Arrays.asList("§7Nombre de membre actuel: §6" + playerIsland.getMembers().size() + "/" + playerIsland.getMaxMembers(),
                "", "§7Niveau", "§70: §66 §7Membres", "§71: §68 §7Membres", "§72: §610 §7Membres", "§73: §612 §7Membres", "§74: §614 §7Membres",
                "§75: §616 §7Membres", "", "§7Clic pour améliorer"));
        custom3.setItemMeta(custom3Meta);
        inv.setItem(12, custom3);

        ItemStack custom4 = new ItemStack(Material.SPAWNER);
        ItemMeta custom4Meta = custom4.getItemMeta();
        custom4Meta.setDisplayName("§6Limite de blocs");
        custom4Meta.setLore(Arrays.asList("§4§l-> ACTUELLEMENT OFF <-", "", "§4§lEN REFLEXION..."));
        custom4.setItemMeta(custom4Meta);
        inv.setItem(16, custom4);


        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(26, custom8);


        player.openInventory(inv);
    }
}
