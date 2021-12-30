package main.java.fr.verymc.gui;

import main.java.fr.verymc.cmd.base.SpawnCmd;
import main.java.fr.verymc.utils.SendActionBar;
import main.java.fr.verymc.utils.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class AfkMineCaptchaGui implements Listener {

    public static ArrayList<Player> Captcha = new ArrayList<Player>();
    public static ArrayList<Player> SeconTry = new ArrayList<Player>();
    public static ItemStack items;

    public static void MakeAfkMineCaptchaGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Captcha AfkMine");

        if (!Captcha.contains(player)) {
            Captcha.add(player);
        }

        Random rand = new Random();
        int n = rand.nextInt(5);

        ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK, 1);
        ItemMeta customa = custom1.getItemMeta();
        customa.setDisplayName("§6Cliquez ici pour confirmer le captcha");
        custom1.setItemMeta(customa);

        ItemStack custom2 = new ItemStack(Material.DIRT, 1);
        ItemMeta customb = custom2.getItemMeta();
        customb.setDisplayName("§cNe pas cliquer ici");
        custom2.setItemMeta(customb);

        inv.setItem(n, custom1);

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                inv.setItem(i, custom2);
            }
        }


        player.openInventory(inv);
        CheckAfterTime(player);
    }

    public static void CheckAfterTime(Player player) {
        if (Captcha.contains(player)) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    if (Captcha.contains(player)) {
                        Captcha.remove(player);
                        if (SeconTry.contains(player)) {
                            SeconTry.remove(player);
                        }
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                            public void run() {
                                player.chat("/spawn");
                                SendActionBar.SendActionBarMsg(player, "§cVous n'avez pas r§ussi le Captcha, vous avez donc été envoyé au spawn");
                                player.closeInventory();
                            }
                        }, 2L);
                    }
                }
            }, 200L);
        }
    }

    @EventHandler
    public void OnInventoryInteract(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if (current == null) {
            return;
        }
        if (current.getType() == null) {
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (e.getView().getTitle().equalsIgnoreCase(p.getName())) {
                e.setCancelled(true);
            }
        }


        if (e.getView().getTitle().equalsIgnoreCase("§6Captcha AfkMine")) {
            e.setCancelled(true);
            if (current.getType() == Material.GRASS_BLOCK) {
                if (Captcha.contains(player)) {
                    Captcha.remove(player);
                }
                SendActionBar.SendActionBarMsg(player, "§aCaptcha r§ussi !");
                player.closeInventory();
                if (SeconTry.contains(player)) {
                    SeconTry.remove(player);
                }
                if (Captcha.contains(player)) {
                    Captcha.remove(player);
                }
            }
            if (current.getType() == Material.DIRT) {
                if (Captcha.contains(player)) {
                    if (!SeconTry.contains(player)) {
                        SendActionBar.SendActionBarMsg(player, "§cCliquez sur le bloc d'herbe !");
                        SeconTry.add(player);
                    } else {
                        if (SeconTry.contains(player)) {
                            SeconTry.remove(player);
                        }
                        if (Captcha.contains(player)) {
                            Captcha.remove(player);
                        }
                        player.closeInventory();
                        TeleportPlayer.TeleportPlayerFromRequest(player, SpawnCmd.Spawn, 0);
                        SendActionBar.SendActionBarMsg(player, "§cVous n'avez pas r§ussi le Captcha, vous avez donc été envoyé au spawn");
                    }
                } else {
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void CloseInventory(InventoryCloseEvent e) {
        Player player = Bukkit.getPlayer(e.getPlayer().getName());
        String aaa = e.getView().getTitle();
        if (aaa.equalsIgnoreCase("§6Captcha AfkMine")) {
            if (Captcha.contains(player)) {
                if (!SeconTry.contains(player)) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                        public void run() {
                            MakeAfkMineCaptchaGui(player);
                            SeconTry.add(player);
                        }
                    }, 5);
                } else {
                    if (SeconTry.contains(player)) {
                        SeconTry.remove(player);
                    }
                    if (Captcha.contains(player)) {
                        Captcha.remove(player);
                    }
                    player.closeInventory();
                    TeleportPlayer.TeleportPlayerFromRequest(player, SpawnCmd.Spawn, 0);
                    SendActionBar.SendActionBarMsg(player, "§cVous n'avez pas r§ussi le Captcha, vous avez donc été envoyé au spawn");
                }
                return;
            }
        }
    }
}
