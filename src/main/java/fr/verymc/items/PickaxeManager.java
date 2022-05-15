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

public class PickaxeManager implements Listener {
    public static ArrayList<Material> breakingBlock = new ArrayList<Material>(Arrays.asList(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE));

    public static void AddBlockHaversted(Player player, ItemStack a) {
        String tosearch = a.getLore().get(0).replace("§7", "");
        if (!tosearch.contains(".")) {
            int num = Integer.parseInt(tosearch);
            num += 1;
            List<String> lores = a.getLore();
            lores.set(0, "§7" + num);
            player.getItemInHand().setLore(lores);
        }
    }

    public static Integer GetBlockHaversted(ItemStack a) {
        String tosearch = a.getLore().get(0).replace("§7", "");
        boolean digit;
        try {
            @SuppressWarnings("unused")
            int intValue = Integer.parseInt(tosearch);
            digit = true;
        } catch (NumberFormatException e) {
            digit = false;
        }
        if (!tosearch.contains(".") && digit) {
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
    public void PickaxeClick(PlayerInteractEvent e) {
        if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemStack itemInHand = e.getPlayer().getItemInHand();
            if (itemInHand.getType() == Material.AIR) {
                return;
            }
            if (itemInHand.getItemMeta() == null) {
                return;
            }
            if (itemInHand.getType() != Material.NETHERITE_PICKAXE) {
                return;
            }
            if (!itemInHand.isUnbreakable()) {
                return;
            }
            Player player = e.getPlayer();
            Location clicloc = e.getClickedBlock().getLocation();

            int tier;
            if (itemInHand.getDisplayName().contains("§cIII")) {
                tier = 2;
            } else if (itemInHand.getDisplayName().contains("§cII")) {
                tier = 1;
            } else if (itemInHand.getDisplayName().contains("§cI")) {
                tier = 0;
            } else {
                return;
            }
            if (e.getPlayer().getItemInHand().getLore() == null) {
                return;
            }
            if (!itemInHand.getLore().get(0).contains("§")) {
                return;
            }

            World world = player.getWorld();
            int gained = 0;
            for (Block rf : FarmHoeManager.getNearbyBlocks(clicloc, tier)) {
                if (!breakingBlock.contains(rf.getType())) {
                    continue;
                }
                Block bltmp = Bukkit.getWorld(world.getName()).getBlockAt(rf.getLocation());
                int fd = 1;
                    for (ItemStack eed : bltmp.getDrops(e.getItem())) {
                        if (BuyShopItem.GetAmountToFillInInv(eed, player) > 0) {
                            player.getInventory().addItem(eed);
                            continue;
                        }
                        world.dropItemNaturally(rf.getLocation(), eed);
                    }
                    gained++;
                    bltmp.getState().update(true);
                    AddBlockHaversted(player, itemInHand);
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
