package fr.farmeurimmo.verymc.items;

import fr.farmeurimmo.verymc.shopgui.BuyShopItem;
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
        replantableblocks.addAll(Arrays.asList("WHEAT"));
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        if (radius == 1) {
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

    @EventHandler
    public void HoeClic(PlayerInteractEvent e) {
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

            if (!farmhoe.getDisplayName().contains("I")) {
                return;
            }

            int tier = 0;
            if (farmhoe.getDisplayName().contains("IV")) {
                tier = 3;
            } else if (farmhoe.getDisplayName().contains("III")) {
                tier = 2;
            } else if (farmhoe.getDisplayName().contains("II")) {
                tier = 1;
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
                if (age == 7) {
                    for (ItemStack eed : bltmp.getDrops()) {
                        if (eed.getType().toString().contains("SEED") && fd == 1) {
                            fd = 0;
                            for (ItemStack redse : player.getInventory().getStorageContents()) {
                                if (redse == null) continue;
                                if (redse.getType() == eed.getType()) {
                                    redse.setAmount(redse.getAmount() - 1);
                                    break;
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
    }

}
