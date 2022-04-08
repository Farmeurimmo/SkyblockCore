package main.java.fr.verymc.island.events;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.Random;

public class IslandGeneratorForm implements Listener {

    @EventHandler
    public void onBlocForm(BlockFormEvent e) {
        if (IslandManager.instance.isAnIslandByLoc(e.getBlock().getLocation())) {
            Island island = IslandManager.instance.getIslandByLoc(e.getBlock().getLocation());
            Random r = new Random();
            int i = r.nextInt(100);
            e.getNewState().setType(island.getGeneratorUpgrade().getMaterials().get(i));
        }
    }
}
