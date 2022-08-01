package main.java.fr.verymc.spigot.utils;

import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
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
        String Grade = user.getCachedData().getMetaData().getPrefix();
        if (Grade != null) {
            Grade.replace("&", "§");
        } else {
            Grade = "[]";
        }
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

    @NotNull
    public ItemStack getHeadMinion() {
        ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta customi = (SkullMeta) custom10.getItemMeta();
        customi.setOwner("Farmeurimmo");
        custom10.setItemMeta(customi);
        return custom10;
    }

    @NotNull
    public ItemStack getEvoPickaxe() {
        ItemStack evoPickaxe = (new ItemStackBuilder(Material.NETHERITE_PICKAXE, 1).setName("§6Gros Cailloux")
                .setLore("§70", "§7", "§6Clic droit pour ouvrir le menu d'amélioration")).getItemStack();
        evoPickaxe.setUnbreakable(true);
        return evoPickaxe;
    }

}
