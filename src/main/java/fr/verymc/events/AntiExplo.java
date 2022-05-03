package main.java.fr.verymc.events;

import main.java.fr.verymc.cmd.base.SpawnCmd;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.weather.LightningStrikeEvent;


public class AntiExplo implements Listener {

    @EventHandler
    public void onExplo(ExplosionPrimeEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void lightning(LightningStrikeEvent e) {
        if (e.getLightning().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void Explo1(EntityExplodeEvent e) {
        if (e.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
                e.setCancelled(true);
                if (e.getEntity().getLocation().getY() < -1) {
                    Player player = (Player) e.getEntity();
                    player.teleport(SpawnCmd.Spawn);
                }
                return;
            }
            Player player = (Player) e.getEntity();
            if (!e.isCancelled() && player.getHealth() < e.getFinalDamage()) {
                e.setCancelled(true);
                player.teleport(SpawnCmd.Spawn);
                player.sendMessage("§6§lIles §8» §cTu es mort, tu as donc été téléporté au spawn.");
                return;
            }
        }
    }

    @EventHandler
    public void blockfire(BlockBurnEvent e) {
        if (e.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void portal(PlayerPortalEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage("§6§lIles §8» §cLe nether est désactivé.");
    }
}
