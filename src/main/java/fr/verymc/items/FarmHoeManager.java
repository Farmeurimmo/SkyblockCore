package main.java.fr.verymc.items;

import main.java.fr.verymc.evenement.BlocBreakerContest;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.challenges.IslandChallenge;
import main.java.fr.verymc.island.challenges.IslandChallengesGuis;
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

    public static ArrayList<String> replantableblocks = new ArrayList<String>(Arrays.asList("WHEAT", "CARROTS", "POTATOES",
            "NETHER_WART", "BEETROOTS", "COCOA"));

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
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
        try {
            Integer.parseInt(tosearch);
        } catch (NumberFormatException e) {
            return 0;
        }
        if (!tosearch.contains(".")) {
            return Integer.parseInt(tosearch);
        }
        return 0;
    }

    public static Integer GetTier(ItemStack farmhoe) {
        if (farmhoe == null) {
            return -1;
        }
        if (farmhoe.getType() == Material.AIR) {
            return -1;
        }
        if (farmhoe.getItemMeta() == null) {
            return -1;
        }
        if (farmhoe.getType() != Material.NETHERITE_HOE) {
            return -1;
        }
        if (!farmhoe.isUnbreakable()) {
            return -1;
        }

        if (farmhoe.getDisplayName().contains("§cIII")) {
            return 2;
        } else if (farmhoe.getDisplayName().contains("§cII")) {
            return 1;
        } else if (farmhoe.getDisplayName().contains("§cI")) {
            return 0;
        } else {
            return -1;
        }
    }

    @EventHandler
    public void HoeClic(PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemStack farmhoe = e.getPlayer().getItemInHand();
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

            int tier = GetTier(farmhoe);
            if (tier < 0) {
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
                int gained = 0;
                if (age == ageable.getMaximumAge()) {
                    for (ItemStack eed : bltmp.getDrops(e.getItem())) {
                        if (eed.getType().toString().contains("SEED") || eed.getType() == Material.CARROT
                                || eed.getType() == Material.POTATO || eed.getType() == Material.NETHER_WART
                                || eed.getType() == Material.COCOA) {
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
                    gained++;
                    bltmp.setBlockData(ageable);
                    bltmp.getState().update(true);
                    AddBlockHaversted(player, farmhoe);
                    if (BlocBreakerContest.instance.isActive) {
                        if (bltmp.getType().equals(BlocBreakerContest.instance.material)) {
                            BlocBreakerContest.instance.addBlock(player.getUniqueId());
                        }
                    }
                    if (!IslandManager.instance.asAnIsland(player)) {
                        return;
                    }
                    Island playerIsland = IslandManager.instance.getPlayerIsland(player);
                    IslandChallenge challenge = null;

                    for (IslandChallenge c : playerIsland.getChallenges()) {
                        if (c.getMaterial() == bltmp.getType()) {
                            challenge = c;
                            break;
                        }
                    }
                    if (challenge == null) {
                        continue;
                    }

                    if (!challenge.isActive()) {
                        continue;
                    }

                    challenge.addProgress(1);

                    if (challenge.getProgress() >= challenge.getMaxProgress() * (challenge.getPalier() + 1) * playerIsland.getMembers().size()) {
                        challenge.setProgress(0);
                        if (challenge.getPalier() == 4) {
                            challenge.setActive(false);
                        }
                        challenge.setPalier(challenge.getPalier() + 1);
                        IslandChallengesGuis.CompleteChallenge(player, challenge);
                    }
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
