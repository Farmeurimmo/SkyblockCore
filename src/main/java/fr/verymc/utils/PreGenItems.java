package main.java.fr.verymc.utils;

import main.java.fr.verymc.eco.EcoAccountsManager;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PreGenItems {

    public static PreGenItems instance;

    public PreGenItems() {
        instance = this;
    }

    public ItemStack getOwnerHead(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");

        ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta customi = (SkullMeta) custom10.getItemMeta();
        customi.setOwner(player.getName());
        customi.setDisplayName("§7" + player.getName());
        customi.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " +
                EcoAccountsManager.instance.moneyGetarrondiNDecimales(player)));
        custom10.setItemMeta(customi);
        return custom10;
    }

    @NotNull
    public ItemStack getHead(Player player) {
        ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta customi = (SkullMeta) custom10.getItemMeta();
        customi.setOwner(player.getName());
        custom10.setItemMeta(customi);
        return custom10;
    }

}
