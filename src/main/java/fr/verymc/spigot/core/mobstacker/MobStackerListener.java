package main.java.fr.verymc.spigot.core.mobstacker;

import io.papermc.paper.event.entity.EntityMoveEvent;
import main.java.fr.verymc.spigot.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
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
            if (entity instanceof Player) continue;
            if (ent.getType() != entity.getType()) continue;
            if (entity.equals(ent)) continue;
            int amount = 1;
            int sec_amount = 1;
            if (ent.hasMetadata("stacker")) {
                amount = ent.getMetadata("stacker").get(0).asInt();
            }
            if (amount >= MobStackerManager.max_par_stacker) continue;
            if (entity.hasMetadata("stacker")) {
                sec_amount = entity.getMetadata("stacker").get(0).asInt();
                if (sec_amount >= MobStackerManager.max_par_stacker) continue;
            }
            amount += sec_amount;
            if (amount > MobStackerManager.max_par_stacker) {
                int temp = 0;
                sec_amount = 0;
                for (int i=0; i<amount; i++) {
                    if (i < MobStackerManager.max_par_stacker) temp++;
                    else sec_amount++;
                }
                amount = temp;
                ent.setMetadata("stacker", new FixedMetadataValue(Main.instance, sec_amount));
                ent.setCustomName(MobStackerManager.instance.mobName(entity.getType(), sec_amount));
            } else {
                ent.remove();
            }
            entity.setMetadata("stacker", new FixedMetadataValue(Main.instance, amount));
            entity.setCustomName(MobStackerManager.instance.mobName(entity.getType(), amount));
            break;
        }
    }
}
