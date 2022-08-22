package main.java.fr.verymc.spigot.dungeon.events;

import main.java.fr.verymc.spigot.dungeon.Dungeon;
import main.java.fr.verymc.spigot.dungeon.DungeonManager;
import main.java.fr.verymc.spigot.dungeon.mobs.DungeonMobCreator;
import main.java.fr.verymc.spigot.dungeon.mobs.DungeonMobManager;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DungeonEntityListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Dungeon dungeon = DungeonManager.instance.getDungeonByLoc(e.getEntity().getLocation());
        if (dungeon == null) return;
        if (DungeonMobManager.instance.mobs.containsKey(dungeon)) {
            DungeonMobManager.instance.mobs.get(dungeon).remove(e.getEntity());
        }
        if (e.getEntity().hasMetadata("boss")) {
            DungeonManager.instance.makeDungeonEnd(dungeon, false);
        }
    }

    @EventHandler
    public void playerDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            if (!(e.getEntity() instanceof LivingEntity)) {
                return;
            }
            LivingEntity entity = (LivingEntity) e.getEntity();
            if (!entity.hasMetadata("boss")) return;
            DungeonMobCreator.instance.dispatchBossCheck(entity, e.getFinalDamage());
            return;
        }
        Player p = (Player) e.getEntity();
        if (p.getHealth() > e.getFinalDamage()) {
            return;
        }
        Dungeon dungeon = DungeonManager.instance.getDungeonByPlayer(p);
        e.setCancelled(true);
        if (dungeon == null) {
            return;
        }
        p.getInventory().clear();
        p.setTotalExperience(0);
        p.setGameMode(GameMode.SPECTATOR);
        p.sendTitle("§cVous êtes mort", "§fVous avez donc perdu votre stuff", 1, 120, 1);
        p.sendMessage(Component.text("§6§lDongeons §l» §cVous êtes mort, votre stuff vous a donc été retiré. " +
                "( faut bien que je rentabilise mon dongeon <3 )"));
        dungeon.addDeadPlayer(p);
    }
}
