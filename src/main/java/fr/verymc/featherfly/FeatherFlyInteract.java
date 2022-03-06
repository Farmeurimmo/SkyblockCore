package main.java.fr.verymc.featherfly;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class FeatherFlyInteract implements Listener {

    @EventHandler
    public void onInteractWithFeather(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand().getType() == Material.FEATHER && player.getItemInHand().getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)) {
            String sample = player.getItemInHand().getItemMeta().getDisplayName();
            char[] chars = sample.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            if (!sb.isEmpty()) {
                e.setCancelled(true);
                int count = player.getInventory().getItemInHand().getAmount();
                CountdownFly.instance.EnableFlyForPlayer(player, sample.replace("§eFly de ", "").
                        replace(sb, "").replace(" ", ""), sb.toString());
                ItemStack aaa = player.getItemInHand();
                aaa.setAmount(count - 1);
                player.getInventory().setItemInHand(aaa);
            }
        }
    }

    @EventHandler
    public void OnDropFeather(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() == Material.FEATHER && e.getItemDrop().getItemStack().isUnbreakable()) {
            e.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void OnToggleFly(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            if (player.getWorld().getName().equalsIgnoreCase("world")) {
                e.setCancelled(true);
                player.sendActionBar("§cVous ne pouvez pas fly au spawn.");
                player.setAllowFlight(false);
            } else {
                e.setCancelled(false);
            }
        }
    }

}
