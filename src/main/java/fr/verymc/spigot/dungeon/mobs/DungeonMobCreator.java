package main.java.fr.verymc.spigot.dungeon.mobs;

import main.java.fr.verymc.spigot.dungeon.DungeonBossBarManager;
import main.java.fr.verymc.spigot.dungeon.items.DungeonItemManager;
import main.java.fr.verymc.spigot.dungeon.items.sets.ZombieSet;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DungeonMobCreator {

    public static DungeonMobCreator instance;

    public HashMap<Integer, String> zombieNameFromLevel = new HashMap<>();
    public HashMap<Integer, Color> zombieColorFromLevel = new HashMap<>();
    public ArrayList<Integer> zombieLvlAvailable = new ArrayList<>();
    public double baseZombieHealth = 8;
    public double default_speed_zombie = 0.25;

    public DungeonMobCreator() {
        instance = this;
        zombieNameFromLevel.put(1, "§cInfecté");
        zombieNameFromLevel.put(3, "§cZombie");
        zombieNameFromLevel.put(5, "§cZombie mutant");
        zombieNameFromLevel.put(10, "§4§lBOSS §cZombie originel");

        zombieColorFromLevel.put(1, Color.fromRGB(0, 255, 0));
        zombieColorFromLevel.put(3, Color.fromRGB(64, 127, 0));
        zombieColorFromLevel.put(5, Color.fromRGB(127, 64, 0));
        zombieColorFromLevel.put(10, Color.fromRGB(255, 0, 0));

        zombieLvlAvailable.addAll(Arrays.asList(1, 3, 5, 10));

        new DungeonItemManager();
    }

    public LivingEntity createAndSpawnZombie(Location spawnLoc, int level) {
        LivingEntity mob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ZOMBIE, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
        Zombie z = (Zombie) mob;

        level = checkForInvalidLevel(zombieLvlAvailable, level);

        Color color = zombieColorFromLevel.get(level);

        z.getEquipment().setHelmet(ZombieSet.instance.getZombieHelmet(color));
        z.getEquipment().setChestplate(ZombieSet.instance.getZombieChestPlate(color));
        z.getEquipment().setLeggings(ZombieSet.instance.getZombieLegging(color));
        z.getEquipment().setBoots(ZombieSet.instance.getZombieBoots(color));
        z.getEquipment().setItemInMainHand(ZombieSet.instance.getZombieSword());

        z.setCustomName("§6Niveau " + level + " §7| " + zombieNameFromLevel.get(level));
        z.setShouldBurnInDay(false);
        z.setCustomNameVisible(true);
        z.setBaby(false);
        z.setMaxHealth(baseZombieHealth * level);
        z.setHealth(z.getMaxHealth());
        z.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 2, 10, false, false));
        z.setAI(true);

        if (level == 10) {
            DungeonBossBarManager.instance.createBossBar(z, z.getCustomName());
        }

        z.registerAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        z.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(default_speed_zombie + (level / 11));
        z.registerAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        z.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(3 + (level / 2));
        z.registerAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        z.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
        z.registerAttribute(Attribute.GENERIC_ATTACK_SPEED);
        z.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(0.25);
        z.registerAttribute(Attribute.GENERIC_FOLLOW_RANGE);
        z.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(50);

        z.getEquipment().setHelmetDropChance(100);
        z.getEquipment().setChestplateDropChance(100);
        z.getEquipment().setLeggingsDropChance(100);
        z.getEquipment().setBootsDropChance(100);
        z.getEquipment().setItemInHandDropChance(100);

        return mob;
    }

    public Integer checkForInvalidLevel(ArrayList<Integer> levelAva, int lvl) {
        if (!levelAva.contains(lvl)) {
            int maxLvl = 1;
            int minLvl = 1;
            for (Integer levelsAva : zombieLvlAvailable) {
                if (maxLvl < levelsAva) {
                    maxLvl = levelsAva;
                }
                if (minLvl > levelsAva) {
                    minLvl = levelsAva;
                }
            }
            if (lvl > maxLvl) {
                lvl = maxLvl;
            } else {
                lvl = minLvl;
            }
            return checkForInvalidLevel(levelAva, lvl);
        }
        return lvl;
    }
}
