package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class LevelAdvBlockListener implements Listener {

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (e.isCancelled()) return;
        if (!LevelAdvManager.matPer.containsKey(block.getType())) return;

        if (block.hasMetadata("placed")) {
            final Ageable ageable = (Ageable) block.getState().getBlockData();
            int age = ageable.getAge();
            if (age != ageable.getMaximumAge()) {
                return;
            }
        }

        Double value = LevelAdvManager.matPer.get(e.getBlock().getType());

        if (value == 1) {
            LevelAdvManager.instance.addExpToPlayer(player);
            return;
        }

        if (new Random().nextDouble() >= value) return;

        LevelAdvManager.instance.addExpToPlayer(player);
    }

}
