package main.java.fr.verymc.hub.events;

import main.java.fr.verymc.core.cmd.moderation.BuildCmd;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Interact implements Listener {

    public static ArrayList<UUID> Build = BuildCmd.Build;

    public ArrayList<Material> allowedBlocks = new ArrayList<>(Arrays.asList(Material.ENDER_CHEST, Material.ENCHANTING_TABLE,
            Material.CRAFTING_TABLE, Material.ANVIL));

    @EventHandler
    public void OnInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        if (!Build.contains(player.getUniqueId())) e.setCancelled(true);
        if (e.getClickedBlock() == null) {
            return;
        }
        if (allowedBlocks.contains(e.getClickedBlock().getType())) {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void BucketEmpty(PlayerBucketEmptyEvent e) {
        if (e.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
            if (Build.contains(e.getPlayer().getUniqueId())) {
                return;
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BucketFill(PlayerBucketFillEvent e) {
        if (e.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
            if (Build.contains(e.getPlayer().getUniqueId())) {
                return;
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            if (Build.contains(e.getPlayer().getUniqueId())) {
                return;
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            if (Build.contains(e.getPlayer().getUniqueId())) {
                return;
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntitySpawnEvent(EntitySpawnEvent e) {
        if (e.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            if (!(e.getEntity() instanceof Item)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void Bucketevent(PlayerItemConsumeEvent e) {
        if (e.getItem().getType().toString().contains("BUCKET")) {
            if (Build.contains(e.getPlayer().getUniqueId())) {
                return;
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerlosefood(FoodLevelChangeEvent e) {
        Player player = (Player) e.getEntity();
        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            if (e.getItem() == null) {
                e.setCancelled(true);
            }
        }
    }
}
