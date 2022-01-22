package main.java.fr.verymc.gui;

import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.utils.PreGenItems;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class CommandBuyGui {


    public static void MakeCommandGui(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");


        Inventory inv = Bukkit.createInventory(null, 36, "§6Boutique des commandes Farm2Win");

        inv.setItem(31, PreGenItems.getOwnerHead(player));

        ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
        ItemMeta customi = custom10.getItemMeta();
        customi.setDisplayName("§cCommande déjà possédée");
        custom10.setItemMeta(customi);

        if (!player.hasPermission("hat")) {
            ItemStack custom3 = new ItemStack(Material.LEATHER_HELMET);
            ItemMeta customc = custom3.getItemMeta();
            customc.setDisplayName("§e/hat");
            customc.setLore(Arrays.asList("§7Cette commande permet d'équiper le", "§7bloc présent dans votre main sur", "§7votre tête", "§7", "§6Prix: " + Farm2WinGui.hatprix + "$"));
            custom3.setItemMeta(customc);
            inv.setItem(10, custom3);
        } else {
            ItemStack custom3 = new ItemStack(Material.BEDROCK);
            ItemMeta customc = custom3.getItemMeta();
            customc.setDisplayName("§e/hat §c(déjà possédée)");
            customc.setLore(Arrays.asList("§7Cette commande permet d'équiper le", "§7bloc présent dans votre main sur", "§7votre tête", "§7", "§6Prix: " + Farm2WinGui.hatprix + "$"));
            custom3.setItemMeta(customc);
            inv.setItem(10, custom3);
        }

        if (!player.hasPermission("fly")) {
            ItemStack custom4 = new ItemStack(Material.FEATHER, 1);
            ItemMeta customd = custom4.getItemMeta();
            customd.setDisplayName("§e/fly");
            customd.setLore(Arrays.asList("§7Cette commande permet de voler", "§7sur votre île indéfiniment", "§7", "§6Prix: " + Farm2WinGui.flyprix + "$"));
            custom4.setItemMeta(customd);
            inv.setItem(12, custom4);
        } else {
            ItemStack custom4 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customd = custom4.getItemMeta();
            customd.setDisplayName("§e/fly §c(déjà possédée)");
            customd.setLore(Arrays.asList("§7Cette commande permet de voler", "§7sur votre île indéfiniment", "§7", "§6Prix: " + Farm2WinGui.flyprix + "$"));
            custom4.setItemMeta(customd);
            inv.setItem(12, custom4);
        }

        if (!player.hasPermission("feed")) {
            ItemStack custom5 = new ItemStack(Material.COOKED_BEEF, 1);
            ItemMeta custome = custom5.getItemMeta();
            custome.setDisplayName("§e/feed");
            custome.setLore(Arrays.asList("§7Cette commande permet de", "§7vous redonnez toute votre nourriture", "§7", "§6Prix: " + Farm2WinGui.feedprix + "$"));
            custom5.setItemMeta(custome);
            inv.setItem(14, custom5);
        } else {
            ItemStack custom5 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta custome = custom5.getItemMeta();
            custome.setDisplayName("§e/feed §c(déjà possédée)");
            custome.setLore(Arrays.asList("§7Cette commande permet de", "§7vous redonnez toute votre nourriture", "§7", "§6Prix: " + Farm2WinGui.feedprix + "$"));
            custom5.setItemMeta(custome);
            inv.setItem(14, custom5);
        }

        if (!player.hasPermission("craft")) {
            ItemStack custom6 = new ItemStack(Material.CRAFTING_TABLE, 1);
            ItemMeta customf = custom6.getItemMeta();
            customf.setDisplayName("§e/craft");
            customf.setLore(Arrays.asList("§7Cette commande permet de", "§7d'ouvrir une table de craft n'importe o§", "§7", "§6Prix: " + Farm2WinGui.craftprix + "$"));
            custom6.setItemMeta(customf);
            inv.setItem(16, custom6);
        } else {
            ItemStack custom6 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customf = custom6.getItemMeta();
            customf.setDisplayName("§e/craft §c(déjà possédée)");
            customf.setLore(Arrays.asList("§7Cette commande permet de", "§7d'ouvrir une table de craft n'importe o§", "§7", "§6Prix: " + Farm2WinGui.craftprix + "$"));
            custom6.setItemMeta(customf);
            inv.setItem(16, custom6);
        }

        if (!player.hasPermission("iridiumskyblock.rename")) {
            ItemStack custom7 = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta customg = custom7.getItemMeta();
            customg.setDisplayName("§e/is rename");
            customg.setLore(Arrays.asList("§7Cette commande permet de", "§7de renomer votre île", "§7", "§6Prix: " + Farm2WinGui.isrenameprix + "$"));
            custom7.setItemMeta(customg);
            inv.setItem(20, custom7);
        } else {
            ItemStack custom7 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customg = custom7.getItemMeta();
            customg.setDisplayName("§e/is rename §c(déjà possédée)");
            customg.setLore(Arrays.asList("§7Cette commande permet de", "§7de renomer votre île", "§7", "§6Prix: " + Farm2WinGui.isrenameprix + "$"));
            custom7.setItemMeta(customg);
            inv.setItem(20, custom7);
        }

        if (!player.hasPermission("economyshopgui.sellall")) {
            ItemStack custom7 = new ItemStack(Material.OAK_SIGN, 1);
            ItemMeta customg = custom7.getItemMeta();
            customg.setDisplayName("§e/sellall");
            customg.setLore(Arrays.asList("§7Cette commande permet de", "§7de vendre tous les items", "§7vendables dans votre inventaire", "§7", "§6Prix: " + Farm2WinGui.sellallprix + "$"));
            custom7.setItemMeta(customg);
            inv.setItem(22, custom7);
        } else {
            ItemStack custom7 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customg = custom7.getItemMeta();
            customg.setDisplayName("§e/sellall §c(déjà possédée)");
            customg.setLore(Arrays.asList("§7Cette commande permet de", "§7de vendre tous les items", "§7vendables dans votre inventaire", "§7", "§6Prix: " + Farm2WinGui.sellallprix + "$"));
            custom7.setItemMeta(customg);
            inv.setItem(22, custom7);
        }

        if (!player.hasPermission("enchantement")) {
            ItemStack custom1 = new ItemStack(Material.ENCHANTING_TABLE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§e/enchantement");
            customa.setLore(Arrays.asList("§7Cette commande permet de", "§7d'ouvrir une table d'enchantement level 30 n'importe o§", "§7", "§6Prix: " + Farm2WinGui.enchantementprix + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(24, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§e/enchantement §c(déjà possédée)");
            customa.setLore(Arrays.asList("§7Cette commande permet de", "§7d'ouvrir une table d'enchantement n'importe o§", "§7", "§6Prix: " + Farm2WinGui.enchantementprix + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(24, custom1);
        }

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(35, custom8);

        player.openInventory(inv);
    }

    public static void BuyCmd(String cmd, Player player) {
        Double money = EcoAccountsManager.instance.Moneys.get(player.getName());

        if (cmd.contains("hat")) {
            if (money >= Farm2WinGui.hatprix) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) Farm2WinGui.hatprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set hat server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /hat !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    CommandBuyGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("fly")) {
            if (money >= Farm2WinGui.flyprix) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) Farm2WinGui.flyprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set fly server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /fly !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    CommandBuyGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("feed")) {
            if (money >= Farm2WinGui.feedprix) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) Farm2WinGui.feedprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set feed server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /feed !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    CommandBuyGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("craft")) {
            if (money >= Farm2WinGui.craftprix) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) Farm2WinGui.craftprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set craft server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /craft !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    CommandBuyGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("isrename")) {
            if (money >= Farm2WinGui.isrenameprix) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) Farm2WinGui.isrenameprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set iridiumskyblock.rename server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /is rename !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    CommandBuyGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("enchantement")) {
            if (money >= Farm2WinGui.enchantementprix) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) Farm2WinGui.enchantementprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set enchantement server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /enchantement !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    CommandBuyGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("sellall")) {
            if (money >= Farm2WinGui.sellallprix) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) Farm2WinGui.sellallprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set economyshopgui.sellall server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /sellall !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    CommandBuyGui.MakeCommandGui(player);
                }
            }, 2);
        }
    }

}
