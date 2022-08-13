package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class LevelAdvBlockListener implements Listener {

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player);

        if (e.isCancelled()) return;
        if (!LevelAdvManager.matPer.containsKey(e.getBlock().getType())) return;

        Double value = LevelAdvManager.matPer.get(e.getBlock().getType());

        if (value == 1) {
            LevelAdvManager.instance.addExpToPlayer(player);
            return;
        }

        Random rand = new Random();

        if (rand.nextDouble() >= value) return;

        LevelAdvManager.instance.addExpToPlayer(player);
    }

}
