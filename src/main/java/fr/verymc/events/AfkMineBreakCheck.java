package main.java.fr.verymc.events;

import main.java.fr.verymc.gui.AfkMineCaptchaGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class AfkMineBreakCheck implements Listener {

    private final HashMap<Player, Integer> numberbloc = new HashMap<>();

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (e.getBlock().getType() == Material.ANCIENT_DEBRIS || e.getBlock().getType() == Material.EMERALD_ORE || e.getBlock().getType() == Material.DIAMOND_ORE
                || e.getBlock().getType() == Material.GOLD_ORE || e.getBlock().getType() == Material.LAPIS_ORE || e.getBlock().getType() == Material.REDSTONE_ORE
                || e.getBlock().getType() == Material.IRON_ORE) {
            if (numberbloc.get(player) == null) {
                numberbloc.put(player, 1);
            } else {
                numberbloc.put(player, numberbloc.get(player) + 1);
            }

            if (numberbloc.get(player) == 500) {
                numberbloc.remove(player);
                AfkMineCaptchaGui.MakeAfkMineCaptchaGui(player);
            }
        }
    }
}
