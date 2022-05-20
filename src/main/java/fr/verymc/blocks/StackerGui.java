package main.java.fr.verymc.blocks;

import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StackerGui {

    public static StackerGui instance;

    public HashMap<Player, Chest> opened = new HashMap<>();
    public HashMap<Player, Boolean> addOrRemove = new HashMap<>();

    public StackerGui() {
        instance = this;
    }

    public void openStackerGui(Player player, Chest chest) {
        if (chest.getType() != 3) return;
        if (!IslandManager.instance.getIslandByLoc(chest.getBlock()).getMembers().containsKey(player.getUniqueId())) {
            player.sendMessage("§6§lStacker §8» §fVous devez être un membre de l'île pour modifier le stacker.");
            return;
        }

        opened.put(player, chest);
        Inventory inv = Bukkit.createInventory(null, 27, "§6Stackeur de blocs");

        ItemStack itemAdd = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        itemAdd.setDisplayName("§aAjouter des blocs");
        itemAdd.setLore(Arrays.asList("§7Clic pour ajouter des blocs"));
        inv.setItem(10, itemAdd);

        ItemStack itemRemove = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        itemRemove.setDisplayName("§cEnlever des blocs");
        itemRemove.setLore(Arrays.asList("§7Clic pour enlever des blocs"));
        inv.setItem(16, itemRemove);

        ItemStack itemBloc = new ItemStack(chest.getStacked(), (int) (chest.getAmount() > 64 ? 64 : chest.getAmount()));
        itemBloc.setLore(Arrays.asList("§ex" + NumberFormat.getInstance().format(chest.getAmount())));
        inv.setItem(13, itemBloc);


        player.openInventory(inv);
    }

    public void openEditingAmountGui(Player player, Chest chest, boolean addOrRemov) {
        if (chest.getType() != 3) return;
        addOrRemove.put(player, addOrRemov);
        opened.put(player, chest);
        Inventory inv = Bukkit.createInventory(null, 27, "§6Edition du stacker");

        ItemStack item;
        if (addOrRemov) {
            item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        } else {
            item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        }
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

        ItemStack retour = new ItemStack(Material.ARROW);
        retour.setDisplayName("§6Retour §8| §7(clic gauche)");
        inv.setItem(26, retour);

        player.openInventory(inv);
    }
}
