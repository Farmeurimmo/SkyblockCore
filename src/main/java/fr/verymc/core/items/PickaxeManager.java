package main.java.fr.verymc.core.items;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.challenges.IslandChallenge;
import main.java.fr.verymc.island.challenges.IslandChallengesGuis;
import main.java.fr.verymc.island.evenement.BlocBreakerContest;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PickaxeManager implements Listener {
    public static ArrayList<Material> breakingBlock = new ArrayList<>(
            Arrays.asList(Material.STONE, Material.COAL_ORE, Material.IRON_ORE,
                    Material.DIAMOND_ORE, Material.ANCIENT_DEBRIS, Material.EMERALD_ORE,
                    Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.COBBLESTONE));

    public static void AddBlockHaversted(Player player, ItemStack a, int amount) {
        String tosearch = a.getLore().get(0).replace("§7", "");
        if (!tosearch.contains(".")) {
            int num = Integer.parseInt(tosearch);
            num += amount;
            List<String> lores = a.getLore();
            lores.set(0, "§7" + num);
            player.getItemInHand().setLore(lores);
        }
    }

    public static int getAmountToAdd(Block breakingBlock) {
        switch (breakingBlock.getType()) {
            case ANCIENT_DEBRIS -> {
                return 5;
            }
            case DIAMOND_ORE, EMERALD_ORE -> {
                return 3;
            }
            case COAL_ORE, IRON_ORE, GOLD_ORE, REDSTONE_ORE, LAPIS_ORE -> {
                return 2;
            }
            default -> {
                return 1;
            }
        }
    }

    @EventHandler
    public void PickaxeClick(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
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
        if (e.getPlayer().getItemInHand().getLore() == null) {
            return;
        }
        if (!itemInHand.getLore().get(0).contains("§7")) {
            return;
        }
        Block rf = e.getBlock();
        if (!breakingBlock.contains(rf.getType())) {
            return;
        }
        if (rf.hasMetadata("placed")) {
            rf.breakNaturally(itemInHand);
            return;
        }
        AddBlockHaversted(player, itemInHand, getAmountToAdd(rf));
        rf.breakNaturally(itemInHand);
        if (BlocBreakerContest.instance.isActive) {
            if (rf.getType().equals(BlocBreakerContest.instance.material)) {
                BlocBreakerContest.instance.addBlock(player.getUniqueId());
            }
        }
        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        IslandChallenge challenge = null;
        for (IslandChallenge c : playerIsland.getChallenges()) {
            if (c.getMaterial() == rf.getType()) {
                challenge = c;
                break;
            }
        }
        if (challenge == null) {
            return;
        }
        if (!challenge.isActive()) {
            return;
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

    @EventHandler
    public void pickaxeGuiEvent(PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand().getType() != Material.NETHERITE_PICKAXE) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_AIR
                && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (!e.getPlayer().getItemInHand().isUnbreakable()) {
            return;
        }
        e.getPlayer().openInventory(
                PickaxeGui.MakeGui(e.getPlayer(), e.getPlayer().getItemInHand()));
    }
}
