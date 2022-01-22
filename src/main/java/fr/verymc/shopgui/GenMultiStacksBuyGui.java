package main.java.fr.verymc.shopgui;

import main.java.fr.verymc.utils.PreGenItems;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class GenMultiStacksBuyGui {

    public static HashMap<ItemStack, Integer> multistacks = new HashMap<>();

    public static void OpenStacksAmoutShop(Player player, ItemStack aa) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6Choix des stacks à acheter");

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");

        inv.setItem(49, PreGenItems.getOwnerHead(player));

        ItemStack custom7 = new ItemStack(Material.ARROW, 1);
        ItemMeta customg = custom7.getItemMeta();
        customg.setDisplayName("§6Retour en arrière");
        custom7.setItemMeta(customg);
        inv.setItem(53, custom7);

        Double price = (double) 0.0;
        if (aa.getType() != Material.SPAWNER) {
            price = BuyShopItem.pricesbuy.get(new ItemStack(Material.valueOf(aa.getType().toString())));
        } else {
            ItemStack ddddd = new ItemStack(aa.getType());
            ddddd.setDisplayName(aa.getDisplayName());
            price = BuyShopItem.pricesbuy.get(ddddd);
        }
        ItemMeta tempameta = aa.getItemMeta();
        tempameta.setLore(null);
        tempameta.setLore(Arrays.asList("§aPrix par stack: §c" + price * 64 + "$"));
        ;
        aa.setItemMeta(tempameta);
        inv.setItem(45, aa);

        for (Entry<ItemStack, Integer> cc : multistacks.entrySet()) {
            ItemMeta temp = cc.getKey().getItemMeta();
            if (cc.getKey().getAmount() < 64) {
                temp.setLore(Arrays.asList("§aTotal: §c" + price * cc.getKey().getAmount() * 64 + "$"));
            } else {
                ItemStack ddddd = new ItemStack(aa.getType());
                if (cc.getKey().getType() == Material.SPAWNER) {
                    ddddd.setDisplayName(aa.getDisplayName());
                }
                temp.setLore(Arrays.asList("§aTotal: §c" + price * BuyShopItem.GetAmountToFillInInv(ddddd, player) + "$"));
            }
            cc.getKey().setItemMeta(temp);
            inv.setItem(cc.getValue(), cc.getKey());
        }

        player.openInventory(inv);

    }

    public static void GenMultiShopGuiStartup() {
        ItemStack custom4 = new ItemStack(Material.GREEN_STAINED_GLASS, 64);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§aRemplir l'inventaire");
        custom4.setItemMeta(customd);

        ItemStack custom5 = new ItemStack(Material.GREEN_STAINED_GLASS, 36);
        ItemMeta custome = custom5.getItemMeta();
        custome.setDisplayName("§a36 stacks");
        custom5.setItemMeta(custome);

        ItemStack custom6 = new ItemStack(Material.GREEN_STAINED_GLASS, 27);
        ItemMeta customf = custom6.getItemMeta();
        customf.setDisplayName("§a27 stacks");
        custom6.setItemMeta(customf);

        ItemStack custom8 = new ItemStack(Material.GREEN_STAINED_GLASS, 18);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§a18 stacks");
        custom8.setItemMeta(customh);

        ItemStack custom9 = new ItemStack(Material.GREEN_STAINED_GLASS, 12);
        ItemMeta customi = custom9.getItemMeta();
        customi.setDisplayName("§a12 stacks");
        custom9.setItemMeta(customi);

        ItemStack custom10 = new ItemStack(Material.GREEN_STAINED_GLASS, 9);
        ItemMeta customj = custom10.getItemMeta();
        customj.setDisplayName("§a9 stacks");
        custom10.setItemMeta(customj);

        ItemStack custom11 = new ItemStack(Material.GREEN_STAINED_GLASS, 8);
        ItemMeta customk = custom11.getItemMeta();
        customk.setDisplayName("§a8 stacks");
        custom11.setItemMeta(customk);

        ItemStack custom12 = new ItemStack(Material.GREEN_STAINED_GLASS, 7);
        ItemMeta customl = custom12.getItemMeta();
        customl.setDisplayName("§a7 stacks");
        custom12.setItemMeta(customl);

        ItemStack custom13 = new ItemStack(Material.GREEN_STAINED_GLASS, 6);
        ItemMeta customm = custom13.getItemMeta();
        customm.setDisplayName("§a6 stacks");
        custom13.setItemMeta(customm);

        ItemStack custom14 = new ItemStack(Material.GREEN_STAINED_GLASS, 5);
        ItemMeta customn = custom14.getItemMeta();
        customn.setDisplayName("§a5 stacks");
        custom14.setItemMeta(customn);

        ItemStack custom15 = new ItemStack(Material.GREEN_STAINED_GLASS, 4);
        ItemMeta customo = custom15.getItemMeta();
        customo.setDisplayName("§a4 stacks");
        custom15.setItemMeta(customo);

        ItemStack custom16 = new ItemStack(Material.GREEN_STAINED_GLASS, 3);
        ItemMeta customp = custom16.getItemMeta();
        customp.setDisplayName("§a3 stacks");
        custom16.setItemMeta(customp);

        ItemStack custom17 = new ItemStack(Material.GREEN_STAINED_GLASS, 2);
        ItemMeta customq = custom17.getItemMeta();
        customq.setDisplayName("§a2 stacks");
        custom17.setItemMeta(customq);

        ItemStack custom18 = new ItemStack(Material.GREEN_STAINED_GLASS, 1);
        ItemMeta customr = custom18.getItemMeta();
        customr.setDisplayName("§a1 stacks");
        custom18.setItemMeta(customr);

        multistacks.put(custom4, 42);
        multistacks.put(custom5, 40);
        multistacks.put(custom6, 38);

        multistacks.put(custom8, 34);
        multistacks.put(custom9, 32);
        multistacks.put(custom10, 30);
        multistacks.put(custom11, 28);

        multistacks.put(custom12, 24);
        multistacks.put(custom13, 22);
        multistacks.put(custom14, 20);

        multistacks.put(custom15, 16);
        multistacks.put(custom16, 14);
        multistacks.put(custom17, 12);
        multistacks.put(custom18, 10);
    }
}
