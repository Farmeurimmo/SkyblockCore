package main.java.fr.verymc.spigot.core.mobstacker;

import io.papermc.paper.event.entity.EntityMoveEvent;
import main.java.fr.verymc.spigot.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class MobStackerListener implements Listener {

    @EventHandler
    public void entityDeathEvent(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Mob)) return;
        if (!e.getEntity().hasMetadata("stacker")) return;
        int amount = e.getEntity().getMetadata("stacker").get(0).asInt();
        if (amount > 1) {
            e.setCancelled(true);
            amount--;
            e.getEntity().setMetadata("stacker", new FixedMetadataValue(Main.instance, amount));
            for (ItemStack itemStack : e.getDrops()) {
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
            }
            e.getEntity().setCustomName(MobStackerManager.instance.mobName(e.getEntity().getType(), amount));
        }
    }

    @EventHandler
    public void checkForEntityToStack(EntityMoveEvent e) {
        Entity ent = e.getEntity();
        if (!(ent instanceof Mob)) return;
        List<Entity> entities = ent.getNearbyEntities(8, 4, 8);
        for (Entity entity : entities) {
            if (!(ent instanceof Mob)) continue;
            if (ent.getType() != entity.getType()) continue;
            if (entity.equals(ent)) continue;
            int amount = 1;
            if (ent.hasMetadata("stacker")) {
                amount = ent.getMetadata("stacker").get(0).asInt();
            }
            if (entity.hasMetadata("stacker")) {
                amount += entity.getMetadata("stacker").get(0).asInt();
            }
            ent.remove();
            entity.setMetadata("stacker", new FixedMetadataValue(Main.instance, amount));
            entity.setCustomName(MobStackerManager.instance.mobName(entity.getType(), amount));
        }
    }
}
