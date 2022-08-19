package main.java.fr.verymc.spigot.core.mobstacker;

import main.java.fr.verymc.spigot.Main;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.text.NumberFormat;

public class MobStackerManager {

    public static final int max_par_stacker = 40;
    public static final String start_of_name = "§6x";
    public static final String metadata_name = "stacker";
    public static MobStackerManager instance;

    public MobStackerManager() {
        instance = this;
    }

    public void spawnMobStacked(EntityType entityType, Location location, int amount) {
        Entity entity = location.getWorld().spawnEntity(location, entityType, CreatureSpawnEvent.SpawnReason.SPAWNER);
        entity.setMetadata(metadata_name, new FixedMetadataValue(Main.instance, amount));
        entity.setCustomNameVisible(true);
        entity.setCustomName(mobName(entityType, amount));
    }

    public String mobName(EntityType entityType, int amount) {
        return start_of_name + NumberFormat.getInstance().format(amount) + " §f" + entityType;
    }
}
