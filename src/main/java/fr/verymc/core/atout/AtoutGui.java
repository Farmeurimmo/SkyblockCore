package main.java.fr.verymc.core.atout;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.core.gui.MenuGui;
import main.java.fr.verymc.core.storage.SkyblockUser;
import main.java.fr.verymc.core.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class AtoutGui implements Listener {

    public static void MakeAtoutGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Atouts");

        SkyblockUserManager.instance.checkForAccount(player);

        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());

        if (!skyblockUser.hasHaste()) {
            ItemStack custom1 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Haste 2");
            customa.setLore(Arrays.asList("§7", "§cAchetez cet atout dans le /farm2win"));
            custom1.setItemMeta(customa);
            inv.setItem(10, custom1);
        } else if (skyblockUser.hasHaste()) {
            ItemStack custom1 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
            ItemMeta customa = custom1.getItemMeta();
            if (skyblockUser.hasHasteActive()) {
                customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                customa.setDisplayName("§6Haste 2 §a(Actif)");
            } else {
                customa.setDisplayName("§6Haste 2 §c(Inactif)");
            }
            custom1.setItemMeta(customa);
            inv.setItem(10, custom1);
        }


        if (!skyblockUser.hasSpeed()) {
            ItemStack custom1 = new ItemStack(Material.SUGAR, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Speed 2");
            customa.setLore(Arrays.asList("§7", "§cAchetez cet atout dans le /farm2win"));
            custom1.setItemMeta(customa);
            inv.setItem(12, custom1);
        } else if (skyblockUser.hasSpeed()) {
            ItemStack custom1 = new ItemStack(Material.SUGAR, 1);
            ItemMeta customa = custom1.getItemMeta();
            if (skyblockUser.hasSpeedActive()) {
                customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                customa.setDisplayName("§6Speed 2 §a(Actif)");
            } else {
                customa.setDisplayName("§6Speed 2 §c(Inactif)");
            }
            custom1.setItemMeta(customa);
            inv.setItem(12, custom1);
        }


        if (!skyblockUser.hasJump()) {
            ItemStack custom1 = new ItemStack(Material.RABBIT_FOOT, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Jumpboost 3");
            customa.setLore(Arrays.asList("§7", "§cAchetez cet atout dans le /farm2win"));
            custom1.setItemMeta(customa);
            inv.setItem(14, custom1);
        } else if (skyblockUser.hasJump()) {
            ItemStack custom1 = new ItemStack(Material.RABBIT_FOOT, 1);
            ItemMeta customa = custom1.getItemMeta();
            if (skyblockUser.hasJumpActive()) {
                customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                customa.setDisplayName("§6Jumpboost 3 §a(Actif)");
            } else {
                customa.setDisplayName("§6Jumpboost 3 §c(Inactif)");
            }
            custom1.setItemMeta(customa);
            inv.setItem(14, custom1);
        }


        ItemStack custom9 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom9.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom9.setItemMeta(customh);
        inv.setItem(26, custom9);


        player.openInventory(inv);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                SkyblockUserManager.instance.checkForAccount(player);
                SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
                if (skyblockUser.hasHaste()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
                }
                if (skyblockUser.hasSpeedActive()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                }
                if (skyblockUser.hasJumpActive()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
                }
            }
        }, 5);
    }

    @EventHandler
    public void MilkConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            Player player = e.getPlayer();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    SkyblockUserManager.instance.checkForAccount(player);
                    SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
                    if (skyblockUser.hasHaste()) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
                    }
                    if (skyblockUser.hasSpeedActive()) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                    }
                    if (skyblockUser.hasJumpActive()) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
                    }
                }
            }, 1);
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Atouts")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu(player);
            }
            SkyblockUserManager.instance.checkForAccount(player);
            SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
            if (current.getType() == Material.GOLDEN_PICKAXE && skyblockUser.hasHasteActive() && skyblockUser.hasHaste()) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                skyblockUser.setHasteActive(false);
                player.sendActionBar("§6Atout haste §c§ldésactivé !");
                AtoutGui.MakeAtoutGui(player);
                return;
            }
            if (current.getType() == Material.GOLDEN_PICKAXE && !skyblockUser.hasHasteActive()) {
                if (skyblockUser.hasHaste() && !skyblockUser.hasHasteActive()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
                    player.sendActionBar("§6Atout haste §a§lactivé !");
                    skyblockUser.setHasteActive(true);
                    AtoutGui.MakeAtoutGui(player);
                    return;
                } else {
                    player.sendActionBar("§cAchetez cet atout dans le /farm2win");
                }
            }


            if (current.getType() == Material.SUGAR && skyblockUser.hasSpeedActive() && skyblockUser.hasSpeed()) {
                player.removePotionEffect(PotionEffectType.SPEED);
                skyblockUser.setSpeedActive(false);
                player.sendActionBar("§6Atout speed §c§ldésactivé !");
                AtoutGui.MakeAtoutGui(player);
                return;
            }
            if (current.getType() == Material.SUGAR && !skyblockUser.hasSpeedActive()) {
                if (!skyblockUser.hasSpeedActive() && skyblockUser.hasSpeed()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                    player.sendActionBar("§6Atout speed §a§lactivé !");
                    skyblockUser.setSpeedActive(true);
                    AtoutGui.MakeAtoutGui(player);
                    return;
                } else {
                    player.sendActionBar("§cAchetez cet atout dans le /farm2win");
                }
            }


            if (current.getType() == Material.RABBIT_FOOT && skyblockUser.hasJumpActive() && skyblockUser.hasJump()) {
                player.removePotionEffect(PotionEffectType.JUMP);
                skyblockUser.setJumpActive(false);
                player.sendActionBar("§6Atout Jumpboost §c§ldésactivé !");
                AtoutGui.MakeAtoutGui(player);
                return;
            }
            if (current.getType() == Material.RABBIT_FOOT) {
                if (skyblockUser.hasJump() && !skyblockUser.hasJumpActive()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
                    player.sendActionBar("§6Atout Jumpboost §a§lactivé !");
                    skyblockUser.setJumpActive(true);
                    AtoutGui.MakeAtoutGui(player);
                    return;
                } else {
                    player.sendActionBar("§cAchetez cet atout dans le /farm2win");
                }
            }
        }
    }
}
