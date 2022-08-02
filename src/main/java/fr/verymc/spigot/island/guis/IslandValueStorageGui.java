package main.java.fr.verymc.spigot.island.guis;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandBlocsValues;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IslandValueStorageGui {

    public static IslandValueStorageGui instance;

    public ArrayList<Player> openedMain = new ArrayList<>();
    public HashMap<Player, Boolean> deposOrWithdraw = new HashMap<>();
    public HashMap<Player, Material> actual = new HashMap<>();

    public IslandValueStorageGui() {
        instance = this;
        updateInvs();
    }

    public void updateInvs() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                ArrayList<Player> toRemoveMain = new ArrayList<>();
                for (Player player : openedMain) {
                    Island island = IslandManager.instance.getPlayerIsland(player);
                    if (island == null) {
                        toRemoveMain.add(player);
                        continue;
                    }
                    if (player.getOpenInventory().getTitle().contains("§6Stacker bank")) {
                        openMainGui(player, island);
                    } else {
                        toRemoveMain.add(player);
                    }
                }
                for (Player player : toRemoveMain) {
                    openedMain.remove(player);
                }
                ArrayList<Player> toRemoveDW = new ArrayList<>();
                for (Map.Entry<Player, Boolean> entry : deposOrWithdraw.entrySet()) {
                    Player player = entry.getKey();
                    boolean value = entry.getValue();
                    Island island = IslandManager.instance.getPlayerIsland(player);
                    if (island == null) {
                        toRemoveDW.add(player);
                        continue;
                    }
                    if (player.getOpenInventory().getTitle().contains("6Edition du stacker bank")) {
                        ItemStack itemStack = new ItemStack(actual.get(player));
                        if (itemStack == null) toRemoveDW.add(player);
                        openEditingAmountGui(player, island, value, itemStack.getType());
                    } else {
                        toRemoveDW.add(player);
                    }
                }
                for (Player player : toRemoveDW) {
                    deposOrWithdraw.remove(player);
                    actual.remove(player);
                }
            }
        }, 20 * 3);
    }

    public void openMainGui(Player player, Island island) {
        openedMain.add(player);
        Inventory inv = Bukkit.createInventory(null, 27, "§6Stacker bank");

        HashMap<Material, Double> stacked = island.getStackedBlocs();
        for (Map.Entry<Material, Double> entry : IslandBlocsValues.instance.getBlockValues().entrySet()) {
            double valuePerBloc = IslandBlocsValues.instance.getBlockValue(entry.getKey());
            ItemStack item = new ItemStack(entry.getKey());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6x" + (stacked.containsKey(entry.getKey()) ? stacked.get(entry.getKey()) : "0") + " " + entry.getKey().name());
            meta.setLore(Arrays.asList("§7Valeur par bloc: " + valuePerBloc, "§7Valeur possédée: " +
                            (valuePerBloc * (stacked.containsKey(entry.getKey()) ? stacked.get(entry.getKey()) : 0)),
                    "§7", "§aClic droit pour ajouter des blocs", "§cClic gauche pour enlever des blocs"));
            item.setItemMeta(meta);
            inv.setItem(inv.firstEmpty(), item);
        }

        ItemStack retour = new ItemStack(Material.ARROW);
        retour.setDisplayName("§6Retour §8| §7(clic gauche)");
        inv.setItem(26, retour);

        player.openInventory(inv);
    }

    public void openEditingAmountGui(Player player, Island island, boolean addOrRemov, Material material) {
        if (openedMain.contains(player)) openedMain.remove(player);
        if (deposOrWithdraw.containsKey(player)) deposOrWithdraw.remove(player);
        if (actual.containsKey(player)) actual.remove(player);
        deposOrWithdraw.put(player, addOrRemov);
        actual.put(player, material);
        Inventory inv = Bukkit.createInventory(null, 36, "§6Edition du stacker bank");

        HashMap<Material, Double> stacked = island.getStackedBlocs();

        ItemStack item = new ItemStack((addOrRemov ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE));
        for (int i = 0; i <= 6; i++) {
            item.setAmount((int) Math.pow(2, i));
            if (item.getAmount() == 2) {
                continue;
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName((addOrRemov ? "§aAjouter" : "§cEnlever") + " x" + item.getAmount());
            ArrayList<String> lore = new ArrayList<>();
            if (item.getLore() != null) {
                lore.addAll(item.getItemMeta().getLore());
            }
            lore.add("§7Clic pour " + (addOrRemov ? "ajouter" : "enlever") + " x" + item.getAmount());
            meta.setLore(lore);
            item.setItemMeta(meta);
            if (i >= 1) {
                inv.setItem(10 + i - 1, item);
            } else {
                inv.setItem(10 + i, item);
            }
            item.setLore(null);
        }

        ItemStack itemStack = new ItemStack((addOrRemov ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE));
        itemStack.setDisplayName((addOrRemov ? "§aTout ajouter" : "§cTout enlever"));
        inv.setItem(16, itemStack);

        ItemStack itemStack1 = new ItemStack(material);
        ItemMeta meta = itemStack1.getItemMeta();
        meta.setDisplayName("§6x" + (stacked.containsKey(material) ? stacked.get(material) : "0") + " " + material.name());
        itemStack1.setItemMeta(meta);
        inv.setItem(22, itemStack1);

        ItemStack retour = new ItemStack(Material.ARROW);
        retour.setDisplayName("§6Retour §8| §7(clic gauche)");
        inv.setItem(35, retour);

        player.openInventory(inv);
    }
}
