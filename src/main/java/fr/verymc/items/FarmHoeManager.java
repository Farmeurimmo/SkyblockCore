package main.java.fr.verymc.items;

import main.java.fr.verymc.shopgui.BuyShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FarmHoeManager implements Listener {

    public static ArrayList<String> replantableblocks = new ArrayList<String>();

    public static void addtolist() {
        replantableblocks.addAll(Arrays.asList("WHEAT", "CARROTS", "POTATOES",
                "NETHER_WART"));
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        if (radius == 0) {
            blocks.add(location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            return blocks;
        }
        int y = location.getBlockY();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                blocks.add(location.getWorld().getBlockAt(x, y, z));
            }
        }
        return blocks;
    }

    public static void AddBlockHaversted(Player player, ItemStack a) {
        String tosearch = a.getLore().get(0).replace("§7", "");
        boolean digit = false;
        try {
            @SuppressWarnings("unused")
            int intValue = Integer.parseInt(tosearch);
            digit = true;
        } catch (NumberFormatException e) {
            digit = false;
        }
        if (!tosearch.contains(".") && digit == true) {
            int num = Integer.parseInt(tosearch);
            num += 1;
            List<String> lores = a.getLore();
            lores.set(0, "§7" + num);
            player.getItemInHand().setLore(lores);
        }
    }

    public static Integer GetBlockHaversted(ItemStack a) {
        String tosearch = a.getLore().get(0).replace("§7", "");
        boolean digit = false;
        try {
            @SuppressWarnings("unused")
            int intValue = Integer.parseInt(tosearch);
            digit = true;
        } catch (NumberFormatException e) {
            digit = false;
        }
        if (!tosearch.contains(".") && digit == true) {
            int num = Integer.parseInt(tosearch);
            return num;
        }
        return 0;
    }

    public static Integer GetTier(ItemStack farmhoe) {
        if (farmhoe == null) {
            return null;
        }
        if (farmhoe.getType() == Material.AIR) {
            return null;
        }
        if (farmhoe.getItemMeta() == null) {
            return null;
        }
        if (farmhoe.getType() != Material.NETHERITE_HOE) {
            return null;
        }
        if (!farmhoe.isUnbreakable()) {
            return null;
        }

        if (farmhoe.getDisplayName().contains("§cIII")) {
            return 2;
        } else if (farmhoe.getDisplayName().contains("§cII")) {
            return 1;
        } else if (farmhoe.getDisplayName().contains("§cI")) {
            return 0;
        } else {
            return null;
        }
    }

    @EventHandler
    public void HoeClic(PlayerInteractEvent e) {
        if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemStack farmhoe = e.getPlayer().getItemInHand();
            if (farmhoe == null) {
                return;
            }
            if (farmhoe.getType() == Material.AIR) {
                return;
            }
            if (farmhoe.getItemMeta() == null) {
                return;
            }
            if (farmhoe.getType() != Material.NETHERITE_HOE) {
                return;
            }
            if (!farmhoe.isUnbreakable()) {
                return;
            }

            Player player = e.getPlayer();
            Location clicloc = e.getClickedBlock().getLocation();

            int tier = 0;
            if (farmhoe.getDisplayName().contains("§cIII")) {
                tier = 2;
            } else if (farmhoe.getDisplayName().contains("§cII")) {
                tier = 1;
            } else if (farmhoe.getDisplayName().contains("§cI")) {
                tier = 0;
            } else {
                return;
            }
            if (e.getPlayer().getItemInHand().getLore() == null) {
                return;
            }
            if (!farmhoe.getLore().get(0).contains("§")) {
                return;
            }

            e.setCancelled(true);

            World world = player.getWorld();
            for (Block rf : getNearbyBlocks(clicloc, tier)) {
                if (!replantableblocks.contains(rf.getType().toString())) {
                    continue;
                }
                Block bltmp = Bukkit.getWorld(world.getName()).getBlockAt(rf.getLocation());
                final Ageable ageable = (Ageable) bltmp.getState().getBlockData();
                int age = ageable.getAge();
                int fd = 1;
                if (age == ageable.getMaximumAge()) {
                    for (ItemStack eed : bltmp.getDrops(e.getItem())) {
                        if (eed.getType().toString().contains("SEED") || eed.getType() == Material.CARROT
                                || eed.getType() == Material.POTATO || eed.getType() == Material.NETHER_WART) {
                            if (fd == 1) {
                                fd = 0;
                                for (ItemStack redse : player.getInventory().getStorageContents()) {
                                    if (redse == null) continue;
                                    if (redse.getType() == eed.getType()) {
                                        redse.setAmount(redse.getAmount() - 1);
                                        break;
                                    }
                                }
                            }
                        }
                        if (BuyShopItem.GetAmountToFillInInv(eed, player) > 0) {
                            player.getInventory().addItem(eed);
                            continue;
                        }
                        world.dropItemNaturally(rf.getLocation(), eed);
                    }
                    ageable.setAge(0);
                    bltmp.setBlockData(ageable);
                    bltmp.getState().update(true);
                    AddBlockHaversted(player, farmhoe);
                } else {
                    continue;
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getPlayer().getItemInHand().getLore() == null) {
                return;
            }
            if (!e.getPlayer().getItemInHand().getLore().get(0).contains("§")) {
                return;
            }
            String tosearch = e.getPlayer().getItemInHand().getLore().get(0).replace("§7", "");
            boolean digit = false;
            try {
                @SuppressWarnings("unused")
                int intValue = Integer.parseInt(tosearch);
                digit = true;
            } catch (NumberFormatException ede) {
                digit = false;
            }
            if (!tosearch.contains(".") && digit == true) {
                FarmHoeGui.MakeGui(e.getPlayer(), GetTier(e.getPlayer().getItemInHand()));
            }
        }
    }

}
