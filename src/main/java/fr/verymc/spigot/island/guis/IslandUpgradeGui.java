package main.java.fr.verymc.spigot.island.guis;

import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeSize;
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
        int sizeIsland = IslandUpgradeSize.getSizeFromLevel(playerIsland.getSizeUpgrade().getLevel());

        ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta custom1Meta = custom1.getItemMeta();
        custom1Meta.setDisplayName("§6Taille de l'île");
        ArrayList<String> custom1Lore = new ArrayList<>();
        custom1Lore.add("§7Taille actuelle : §6" + sizeIsland + "§7x§6" + sizeIsland);
        custom1Lore.add("");
        custom1Lore.add("§7Niveaux:");
        for (int i = 0; i <= 4; i++) {
            custom1Lore.add("§7" + i + ": §6" + IslandUpgradeSize.getSizeFromLevel(i) + "§7x§6" + IslandUpgradeSize.getSizeFromLevel(i));
            custom1Lore.add("  §7Prix : §e" + IslandUpgradeSize.getPriceMoneyFromLevel(i) + "$§7, §e" + IslandUpgradeSize.getPriceCrytauxFromLevel(i) + " crystaux");
        }
        custom1Lore.add("");
        custom1Lore.add("§7Clic pour améliorer");
        custom1Meta.setLore(custom1Lore);
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
            IslandUpgradeGenerator generator = new IslandUpgradeGenerator(i);
            generator.setLevel(i);
            String str = "§7" + i + ": ";
            String str2 = "";
            String str3 = "  §7Prix: §e" + IslandUpgradeGenerator.getMoneyCostFromLevel(i) + "$§7, §e" + IslandUpgradeGenerator.getCrystalCostFromLevel(i) + " crystaux";
            for (Map.Entry<Material, Integer> entry : generator.getMaterials().entrySet()) {
                if (str.length() <= 80) {
                    str += "§6" + entry.getKey().name() + " §7" + entry.getValue() + "% §6";
                } else {
                    if (str2.isEmpty()) {
                        str2 += "  ";
                    }
                    str2 += "§6" + entry.getKey().name() + " §7" + entry.getValue() + "% §6";
                }
            }
            custom2Lore.add(str.toLowerCase());
            if (str2 != null && !str2.isEmpty()) {
                custom2Lore.add(str2.toLowerCase());
            }
            custom2Lore.add(str3.toLowerCase());
        }
        custom2Lore.replaceAll(s -> s.replace("_ore", ""));
        custom2Lore.replaceAll(s -> s.replace("cobblestone", "cobb"));
        custom2Lore.replaceAll(s -> s.replace("diamond", "diams"));
        custom2Lore.replaceAll(s -> s.replace("emerald", "emer"));
        custom2Lore.replaceAll(s -> s.replace("ancient_", ""));
        custom2Lore.add("");
        custom2Lore.add("§7Clic pour améliorer");
        custom2Meta.setLore(custom2Lore);
        custom2.setItemMeta(custom2Meta);
        inv.setItem(14, custom2);

        ItemStack custom3 = new ItemStack(Material.PAPER);
        ItemMeta custom3Meta = custom3.getItemMeta();
        custom3Meta.setDisplayName("§6Nombre de membres");
        ArrayList<String> custom3Lore = new ArrayList<>();
        custom3Lore.add("§7Nombre de membre actuel: §6" + playerIsland.getMembers().size() + "/" + playerIsland.getMaxMembers());
        custom3Lore.add("");
        custom3Lore.add("§7Niveaux: ");
        for (int i = 0; i <= 5; i++) {
            custom3Lore.add("§7" + i + ": §6" + IslandUpgradeMember.getMaxMembers(i) + " §7membres");
            custom3Lore.add("  §7Prix : §e" + IslandUpgradeMember.getPriceMoneyFromLevel(i) + "$§7, §e" +
                    IslandUpgradeMember.getPriceCrytauxFromLevel(i) + " crystaux");
        }
        custom3Lore.add("");
        custom3Lore.add("§7Clic pour améliorer");
        custom3Meta.setLore(custom3Lore);
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
