package main.java.fr.verymc.gui;

import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.atout.BuyAtoutGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Farm2WinGui implements Listener {

    public static int hatprix = 500000;
    public static int sellallprix = 10000000;
    public static int flyprix = 10000000;
    public static int craftprix = 750000;
    public static int enchantementprix = 500000;
    public static int isrenameprix = 1250000;
    public static int feedprix = 750000;
    public static int legendeprix = 2500000;
    public static int dieuprix = 5000000;
    static boolean hatcmd = true;
    static boolean flycmd = true;
    static boolean feedcmd = true;
    static boolean craftcmd = true;
    static boolean isrenamecmd = true;
    static boolean sellallcmd = true;
    static boolean enchantementcmd = true;
    static boolean legendeachat = true;
    static boolean dieueachat = true;

    @EventHandler
    public static void OnInventoryClic(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (current == null) {
            return;
        }
        if (current.getType() == null) {
            return;
        }
        if (event.getView().getTitle().equalsIgnoreCase("§6Boutique Farm2Win")) {
            event.setCancelled(true);
            if (current.getType() == Material.EMERALD_BLOCK) {
                MakeRanksGui.MakeRankGui(player);
            }
            if (current.getType() == Material.CHEST) {
                MakeItemGui.MakeRankGui(player);
            }
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu(player);
            }
            if (current.getType() == Material.POTION) {
                BuyAtoutGui.MakeBuyAtoutGui(player);
            }
            if (current.getType() == Material.COMMAND_BLOCK_MINECART) {
                MakeCommandsGui.MakeCommandGui(player);
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§6Boutique des items Farm2Win")) {
            event.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                Farm2WinGui.MainBoutiqueGUI(player);
            }
            if (current.getType() == Material.DRAGON_BREATH) {
                if (EcoAccountsManager.instance.GetMoney(player.getName()) >= 25000) {
                    EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) 25000);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "aminion give " + player.getName());
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas asser d'argent.");
                }
            }
            if (current.getType() == Material.HOPPER) {
                if (EcoAccountsManager.instance.GetMoney(player.getName()) >= 500000) {
                    EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) 500000);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests " + player.getName() + " ChunkHoppeur");
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas asser d'argent.");
                }
            }
            if (current.getType() == Material.TRAPPED_CHEST) {
                if (EcoAccountsManager.instance.GetMoney(player.getName()) >= 1000000) {
                    EcoAccountsManager.instance.RemoveFounds(player.getName(), (double) 1000000);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests " + player.getName() + " SellChest");
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas asser d'argent.");
                }
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§6Boutique des grades Farm2Win")) {
            event.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                MainBoutiqueGUI(player);
            }
            if (current.getType() == Material.NETHERITE_BLOCK) {
                if (!player.hasPermission("legende")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= legendeprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ du grade...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (legendeachat == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyRanks.BuyRank("legende", player);
                                }
                                current.setType(Material.NETHERITE_BLOCK);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.NETHERITE_BLOCK);
                            }
                        }, 60);
                    }
                }
            }
            if (current.getType() == Material.BEACON) {
                if (!player.hasPermission("dieu")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= dieuprix) {
                        if (player.hasPermission("legende")) {
                            player.sendMessage("§6V§rification de la disponibilit§ du grade...");
                            current.setType(Material.BARRIER);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                                public void run() {
                                    if (legendeachat == false) {
                                        player.sendMessage("§cErreur, achat indisponible !");
                                    } else {
                                        if (player.hasPermission("legende")) {
                                            BuyRanks.BuyRank("dieu", player);
                                        } else {
                                            player.sendMessage("§cErreur, vous devez possèder tous les grades pr§c§dents !");
                                        }
                                    }
                                    current.setType(Material.BEACON);
                                }
                            }, 60);
                        } else {
                            player.sendMessage("§cErreur, vous devez possèder tous les grades pr§c§dents !");
                            current.setType(Material.BARRIER);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                                public void run() {
                                    current.setType(Material.BEACON);
                                }
                            }, 60);
                        }
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.BEACON);
                            }
                        }, 60);
                    }
                }
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§6Boutique des commandes Farm2Win")) {

            event.setCancelled(true);

            if (current.getType() == Material.ARROW) {
                player.closeInventory();
                MainBoutiqueGUI(player);
            }
            if (current.getType() == Material.LEATHER_HELMET) {
                if (!player.hasPermission("hat")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= hatprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (hatcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyCommand.BuyCmd("hat", player);
                                }
                                current.setType(Material.LEATHER_HELMET);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.LEATHER_HELMET);
                            }
                        }, 60);
                    }
                }
            }
            if (current.getType() == Material.OAK_SIGN) {
                if (!player.hasPermission("economyshopgui.sellall")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= sellallprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (hatcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyCommand.BuyCmd("sellall", player);
                                }
                                current.setType(Material.OAK_SIGN);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.OAK_SIGN);
                            }
                        }, 60);
                    }
                }
            }
            if (current.getType() == Material.FEATHER) {
                if (!player.hasPermission("fly")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= flyprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (flycmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyCommand.BuyCmd("fly", player);
                                }
                                current.setType(Material.FEATHER);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.FEATHER);
                            }
                        }, 60);
                    }
                }
            }
            if (current.getType() == Material.COOKED_BEEF) {
                if (!player.hasPermission("feed")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= feedprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (feedcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyCommand.BuyCmd("feed", player);
                                }
                                current.setType(Material.COOKED_BEEF);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.COOKED_BEEF);
                            }
                        }, 60);
                    }
                }
            }
            if (current.getType() == Material.CRAFTING_TABLE) {
                if (!player.hasPermission("craft")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= craftprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (craftcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyCommand.BuyCmd("craft", player);
                                }
                                current.setType(Material.CRAFTING_TABLE);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.CRAFTING_TABLE);
                            }
                        }, 60);
                    }
                }
            }
            if (current.getType() == Material.NAME_TAG) {
                if (!player.hasPermission("iridiumskyblock.rename")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= isrenameprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (isrenamecmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyCommand.BuyCmd("isrename", player);
                                }
                                current.setType(Material.NAME_TAG);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.NAME_TAG);
                            }
                        }, 60);
                    }
                }
            }
            if (current.getType() == Material.ENCHANTING_TABLE) {
                if (!player.hasPermission("enchantement")) {
                    if (EcoAccountsManager.instance.Moneys.get(player.getName()) >= enchantementprix) {
                        player.sendMessage("§6V§rification de la disponibilit§ de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                if (enchantementcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    BuyCommand.BuyCmd("enchantement", player);
                                }
                                current.setType(Material.ENCHANTING_TABLE);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                current.setType(Material.ENCHANTING_TABLE);
                            }
                        }, 60);
                    }
                }
            }
        }
    }

    public static void MainBoutiqueGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique Farm2Win");

        ItemStack custom1 = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta customa = custom1.getItemMeta();
        customa.setDisplayName("§6Boutique des grades §8| §7(clic gauche)");
        custom1.setItemMeta(customa);
        inv.setItem(10, custom1);

        ItemStack custom3 = new ItemStack(Material.CHEST, 1);
        ItemMeta customc = custom3.getItemMeta();
        customc.setDisplayName("§6Boutique des items §8| §7(clic gauche)");
        custom3.setItemMeta(customc);
        inv.setItem(14, custom3);

        ItemStack custom2 = new ItemStack(Material.COMMAND_BLOCK_MINECART, 1);
        ItemMeta customb = custom2.getItemMeta();
        customb.setDisplayName("§6Boutique des commandes §8| §7(clic gauche)");
        custom2.setItemMeta(customb);
        inv.setItem(12, custom2);

        ItemStack custom4 = new ItemStack(Material.POTION, 1);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§6Boutique des atouts §8| §7(clic gauche)");
        custom4.setItemMeta(customd);
        inv.setItem(16, custom4);

        ItemStack custom9 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom9.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom9.setItemMeta(customh);
        inv.setItem(26, custom9);

        ItemStack custom8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemMeta meta8 = custom8.getItemMeta();
        meta8.setDisplayName("§6");
        custom8.setItemMeta(meta8);
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                inv.setItem(i, custom8);
            }
        }


        player.openInventory(inv);
    }
}
