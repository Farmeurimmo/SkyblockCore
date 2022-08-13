package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import main.java.fr.verymc.spigot.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class LevelAdvGui implements Listener {

    public static LevelAdvGui instance;

    public LevelAdvGui() {
        instance = this;
    }

    public void openPlayerMainLevel(Player player) {
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player);

        Inventory inv = Bukkit.createInventory(null, 27, "§6Niveau d'aventure");

        ItemStack nextReward = new ItemStack(Material.GOLD_BLOCK);
        nextReward.setDisplayName("§6Récompense(s) du prochain niveau");
        nextReward.setLore(LevelAdvManager.instance.getRewardForNextLevel(skyblockUser.getLevel()));
        inv.setItem(11, nextReward);

        ItemStack playerInfo = PreGenItems.instance.getHead(player);
        playerInfo.setDisplayName("§6" + player.getName());
        playerInfo.setLore(Arrays.asList("§7Niveau: §6" + LevelAdvManager.instance.getLevelFormatted(skyblockUser), "§7Expérience: §a" +
                LevelAdvManager.instance.getExpFormatted(skyblockUser) + "§7/§c" +
                LevelAdvManager.instance.getExpToGetForNextLevelFormatted(skyblockUser.getLevel())));
        inv.setItem(13, playerInfo);

        ItemStack help = new ItemStack(Material.BOOK);
        help.setDisplayName("§6Aide");
        help.setLore(Arrays.asList("§7bla bla"));
        inv.setItem(15, help);

        player.openInventory(inv);
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("§6Niveau d'aventure")) {
            e.setCancelled(true);
        }
    }
}
