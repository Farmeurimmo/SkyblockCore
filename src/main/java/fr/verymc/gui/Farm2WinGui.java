package main.java.fr.verymc.gui;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.atout.BuyAtoutGui;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.minions.MinionManager;
import main.java.fr.verymc.minions.MinionType;
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
    public static int dieuprix = 7500000;
    public static int zeusprix = 14000000;
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
                RankBuyGui.MakeRankGui(player);
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
                CommandBuyGui.MakeCommandGui(player);
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§6Boutique des items Farm2Win")) {
            event.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                Farm2WinGui.MainBoutiqueGUI(player);
                return;
            }
            if (current.getType() == Material.DRAGON_BREATH) {
                if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= 25000) {
                    EcoAccountsManager.instance.removeFounds(player, (double) 25000, true);
                    MinionManager.instance.giveMinionItem(player, MinionType.PIOCHEUR.name());
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas assez d'argent.");
                }
                return;
            }
            if (current.getType() == Material.HOPPER) {
                if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= 500000) {
                    EcoAccountsManager.instance.removeFounds(player, (double) 500000, true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests " + player.getName() + " ChunkHoppeur");
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas assez d'argent.");
                }
                return;
            }
            if (current.getType() == Material.TRAPPED_CHEST) {
                if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= 1000000) {
                    EcoAccountsManager.instance.removeFounds(player, (double) 1000000, true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests " + player.getName() + " SellChest");
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas assez d'argent.");
                }
                return;
            }
            if (current.getType() == Material.NETHERITE_HOE) {
                if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= 500000) {
                    EcoAccountsManager.instance.removeFounds(player, (double) 500000, true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "farmhoe " + player.getName());
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas assez d'argent.");
                }
                return;
            }
            if (current.getType() == Material.NETHERITE_PICKAXE) {
                if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= 500000) {
                    EcoAccountsManager.instance.removeFounds(player, (double) 500000, true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pickaxe " + player.getName());
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas assez d'argent.");
                }
                return;
            }
            if (current.getType() == Material.CHEST) {
                if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= 75000) {
                    EcoAccountsManager.instance.removeFounds(player, (double) 75000, true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests " + player.getName() + " PlayerShop");
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas assez d'argent.");
                }
                return;
            }
            return;
        }
        if (event.getView().getTitle().equalsIgnoreCase("§6Boutique des grades Farm2Win")) {
            event.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                MainBoutiqueGUI(player);
                return;
            }
            if (current.getType() == Material.IRON_BLOCK) {
                if (!player.hasPermission("group.legende")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= legendeprix) {
                        player.sendMessage("§6Vérification de la disponibilité du grade...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (legendeachat == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    RankBuyGui.BuyRank("Légende", player);
                                }
                                current.setType(Material.IRON_BLOCK);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.IRON_BLOCK);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.GOLD_BLOCK) {
                if (!player.hasPermission("group.dieu")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= dieuprix) {
                        if (player.hasPermission("group.legende")) {
                            player.sendMessage("§6Vérification de la disponibilité du grade...");
                            current.setType(Material.BARRIER);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                                public void run() {
                                    if (legendeachat == false) {
                                        player.sendMessage("§cErreur, achat indisponible !");
                                    } else {
                                        if (player.hasPermission("group.legende")) {
                                            RankBuyGui.BuyRank("Dieu", player);
                                        } else {
                                            player.sendMessage("§cErreur, vous devez posséder tous les grades précédents !");
                                        }
                                    }
                                    current.setType(Material.GOLD_BLOCK);
                                }
                            }, 60);
                        } else {
                            player.sendMessage("§cErreur, vous devez possèder tous les grades précédents !");
                            current.setType(Material.BARRIER);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                                public void run() {
                                    current.setType(Material.GOLD_BLOCK);
                                }
                            }, 60);
                        }
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.GOLD_BLOCK);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.NETHERITE_BLOCK) {
                if (!player.hasPermission("group.zeus")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= zeusprix) {
                        if (player.hasPermission("group.legende") && player.hasPermission("group.dieu")) {
                            player.sendMessage("§6Vérification de la disponibilité du grade...");
                            current.setType(Material.BARRIER);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                                public void run() {
                                    if (legendeachat == false) {
                                        player.sendMessage("§cErreur, achat indisponible !");
                                    } else {
                                        if (player.hasPermission("group.legende") && player.hasPermission("group.dieu")) {
                                            RankBuyGui.BuyRank("Zeus", player);
                                        } else {
                                            player.sendMessage("§cErreur, vous devez posséder tous les grades précédents !");
                                        }
                                    }
                                    current.setType(Material.NETHERITE_BLOCK);
                                }
                            }, 60);
                        } else {
                            player.sendMessage("§cErreur, vous devez posséder tous les grades précédents !");
                            current.setType(Material.BARRIER);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                                public void run() {
                                    current.setType(Material.NETHERITE_BLOCK);
                                }
                            }, 60);
                        }
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.NETHERITE_BLOCK);
                            }
                        }, 60);
                    }
                }
                return;
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§6Boutique des commandes Farm2Win")) {

            event.setCancelled(true);

            if (current.getType() == Material.ARROW) {
                MainBoutiqueGUI(player);
                return;
            }
            if (current.getType() == Material.LEATHER_HELMET) {
                if (!player.hasPermission("hat")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= hatprix) {
                        player.sendMessage("§6Vérification de la disponibilité de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (hatcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    CommandBuyGui.BuyCmd("hat", player);
                                }
                                current.setType(Material.LEATHER_HELMET);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.LEATHER_HELMET);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.OAK_SIGN) {
                if (!player.hasPermission("economyshopgui.sellall")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= sellallprix) {
                        player.sendMessage("§6Vérification de la disponibilité de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (hatcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    CommandBuyGui.BuyCmd("sellall", player);
                                }
                                current.setType(Material.OAK_SIGN);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.OAK_SIGN);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.FEATHER) {
                if (!player.hasPermission("fly")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= flyprix) {
                        player.sendMessage("§6Vérification de la disponibilité de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (flycmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    CommandBuyGui.BuyCmd("fly", player);
                                }
                                current.setType(Material.FEATHER);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.FEATHER);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.COOKED_BEEF) {
                if (!player.hasPermission("feed")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= feedprix) {
                        player.sendMessage("§6Vérification de la disponibilité de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (feedcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    CommandBuyGui.BuyCmd("feed", player);
                                }
                                current.setType(Material.COOKED_BEEF);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.COOKED_BEEF);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.CRAFTING_TABLE) {
                if (!player.hasPermission("craft")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= craftprix) {
                        player.sendMessage("§6Vérification de la disponibilité de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (craftcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    CommandBuyGui.BuyCmd("craft", player);
                                }
                                current.setType(Material.CRAFTING_TABLE);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.CRAFTING_TABLE);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.NAME_TAG) {
                if (!player.hasPermission("iridiumskyblock.rename")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= isrenameprix) {
                        player.sendMessage("§6Vérification de la disponibilité de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (isrenamecmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    CommandBuyGui.BuyCmd("isrename", player);
                                }
                                current.setType(Material.NAME_TAG);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.NAME_TAG);
                            }
                        }, 60);
                    }
                }
                return;
            }
            if (current.getType() == Material.ENCHANTING_TABLE) {
                if (!player.hasPermission("enchantement")) {
                    if (EcoAccountsManager.instance.getMoney(player.getUniqueId()) >= enchantementprix) {
                        player.sendMessage("§6Vérification de la disponibilité de la commande...");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                if (enchantementcmd == false) {
                                    player.sendMessage("§cErreur, achat indisponible !");
                                } else {
                                    CommandBuyGui.BuyCmd("enchantement", player);
                                }
                                current.setType(Material.ENCHANTING_TABLE);
                            }
                        }, 60);
                    } else {
                        player.sendMessage("§cFonds insuffisants !");
                        current.setType(Material.BARRIER);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            public void run() {
                                current.setType(Material.ENCHANTING_TABLE);
                            }
                        }, 60);
                    }
                }
                return;
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


        player.openInventory(inv);
    }
}
