package main.java.fr.verymc.spigot.hub.events;

import main.java.fr.verymc.spigot.core.cmd.base.SpawnCmd;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.weather.LightningStrikeEvent;


public class AntiExplo implements Listener {

    @EventHandler
    public void onExplo(ExplosionPrimeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void lightning(LightningStrikeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void Explo1(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void OnDamage(EntityDamageEvent e) {
        e.setCancelled(true);
        if (e.getEntity().getLocation().getY() < -1) {
            Player player = (Player) e.getEntity();
            player.teleport(SpawnCmd.Spawn);
        }
    }

    @EventHandler
    public void blockfire(BlockBurnEvent e) {
        e.setCancelled(true);
    }
}
