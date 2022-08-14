package main.java.fr.verymc.spigot.core.leveladv;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class LevelAdvListener implements Listener {

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (e.isCancelled()) return;
        LevelAdvManager.instance.blockEvent(block, player);
    }

    @EventHandler
    public void entityKillEvent(EntityDeathEvent e) {
        Player player = e.getEntity().getKiller();
        if (player == null) return;
        if (e.isCancelled()) return;
        ;
        Double exp = 0.0;
        if (e.getEntity().hasMetadata("lvl")) {
            exp = LevelAdvManager.exp_gained * e.getEntity().getMetadata("lvl").get(0).asDouble() * LevelAdvManager.exp_multiplier_in_dungeon;
        }
        LevelAdvManager.instance.entityEvent(player, exp);
    }

}
