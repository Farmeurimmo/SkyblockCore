package main.java.fr.verymc.spigot.island.guis;

import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.protections.IslandSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IslandSettingsGui {

    public static IslandSettingsGui instance;

    public IslandSettingsGui() {
        instance = this;
    }

    public void openIslandSettingsGui(Player player) {
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        if (playerIsland == null) {
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 36, "§6Paramètres de l'île");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(35, custom8);

        ItemStack custom9 = new ItemStack(Material.KNOWLEDGE_BOOK, 1);
        ItemMeta customi = custom9.getItemMeta();
        customi.setDisplayName("§6Informations complémentaires");
        customi.setLore(Arrays.asList("§aActivé §7signifie que les actions peuvent s'effectuer",
                "§cDésactivé §7signifie que les actions ne peuvent pas s'effectuer"));
        custom9.setItemMeta(customi);
        inv.setItem(27, custom9);

        int currentSlot = 10;

        for (IslandSettings setting : IslandSettings.values()) {
            ItemStack custom = IslandSettings.getItemForSetting(setting);
            if (custom.getType() == Material.DAYLIGHT_DETECTOR && !playerIsland.hasSettingActivated(setting))
                continue;
            if (custom.getType() == Material.CLOCK && !playerIsland.hasSettingActivated(setting))
                continue;
            if (custom.getType() == Material.DAYLIGHT_DETECTOR && playerIsland.hasSettingActivated(setting)) {
                custom.setDisplayName("§6" + setting.getDesc());
                custom.setLore(Arrays.asList("", "§7Clic pour changer"));
            } else if (custom.getType() == Material.CLOCK && playerIsland.hasSettingActivated(setting)) {
                custom.setDisplayName("§6" + setting.getDesc());
                custom.setLore(Arrays.asList("", "§7Clic pour changer"));
            } else {
                custom.setDisplayName("§6" + setting.getDesc());
                custom.setLore(Arrays.asList("§7Statut: " + (playerIsland.hasSettingActivated(setting) ? "§aActivé" :
                        "§cDésactivé"), "", "§7Clic pour changer"));
            }
            inv.setItem(currentSlot, custom);
            currentSlot++;
            if (currentSlot == 17) {
                currentSlot += 2;
            }
        }

        player.openInventory(inv);
    }


}
