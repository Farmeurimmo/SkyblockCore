package main.java.fr.verymc.island.challenges;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.core.cmd.moderation.BuildCmd;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.evenement.BlocBreakerContest;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Arrays;

public class IslandChallengesListener implements Listener {

    public static int cobble = 96;
    public static int coal = 48;
    public static int iron = 64;
    public static int gold = 32;
    public static int diamond = 64;
    public static int emerald = 16;
    public static int debris = 16;

    public static int oak_log = 32;
    public static int birch_log = 32;
    public static int spruce_log = 32;
    public static int dark_oak_log = 32;
    public static int acacia_log = 32;
    public static int jungle_log = 32;
    public static int wheat = 64;
    public static int pumpkin = 64;
    public static int melon = 64;
    public static int cocoa = 64;
    public static int nether_wart = 64;
    public static int carrot = 64;
    public static int potato = 64;

    public ArrayList<Material> excluded = new ArrayList<>(Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOT,
            Material.NETHER_WART, Material.COCOA));

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        if (!excluded.contains(e.getBlock().getType())) {
            e.getBlock().setMetadata("placed", new FixedMetadataValue(Main.instance, e.getPlayer().getName()));
        }
    }

    @EventHandler
    public void BlockForm(BlockFormEvent e) {
        if (e.getBlock().getMetadata("placed") != null) {
            e.getBlock().removeMetadata("placed", Main.instance);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Material currenttype = e.getBlock().getType();
        Block block = e.getBlock();
        if (block.hasMetadata("placed")) {
            if (!excluded.contains(currenttype)) {
                return;
            }
            final Ageable ageable = (Ageable) block.getState().getBlockData();
            int age = ageable.getAge();
            if (age != ageable.getMaximumAge()) {
                return;
            }
        }
        if (!e.isCancelled() && !BuildCmd.Build.contains(player.getUniqueId())) {
            if (BlocBreakerContest.instance.isActive) {
                if (e.getBlock().getType().equals(BlocBreakerContest.instance.material)) {
                    BlocBreakerContest.instance.addBlock(player.getUniqueId());
                }
            }
            if (!IslandManager.instance.asAnIsland(player)) {
                return;
            }
            Island playerIsland = IslandManager.instance.getPlayerIsland(player);
            IslandChallenge challenge = null;

            for (IslandChallenge c : playerIsland.getChallenges()) {
                if (c.getMaterial() == currenttype) {
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
                if (challenge.getPalier() >= IslandChallengesReset.maxPalier) {
                    challenge.setActive(false);
                }
                challenge.setPalier(challenge.getPalier() + 1);
                IslandChallengesGuis.CompleteChallenge(player, challenge);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        if (playerIsland == null) {
            return;
        }
        for (IslandChallenge islandChallenge : playerIsland.getChallenges()) {
            if (islandChallenge.getType() != 1) continue;
            if (islandChallenge.getToGet().contains(e.getItem().getItemStack().getType())) {
                islandChallenge.addProgress(1);
                islandChallenge.getToGet().remove(e.getItem().getItemStack().getType());
                if (islandChallenge.getToGet().size() == 0) {
                    islandChallenge.setActive(false);
                    IslandChallengesGuis.CompleteChallenge(player, islandChallenge);
                    return;
                }
            }
        }
    }


}
