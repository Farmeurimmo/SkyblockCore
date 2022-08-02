package main.java.fr.verymc.spigot.dungeon;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
    }

    public void createAndSpawnZombie(Location spawnLoc, int level) {
        LivingEntity mob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ZOMBIE, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
        Zombie z = (Zombie) mob;

        z.setCustomName("§6Niveau " + level + " §7| " + zombieNameFromLevel.get(level));
        z.setShouldBurnInDay(false);
        z.setCustomNameVisible(true);
        z.setBaby(false);
        z.setMaxHealth(baseZombieHealth * level);
        z.setHealth(z.getMaxHealth());
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


        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                3, AttributeModifier.Operation.ADD_NUMBER));
        helmet.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                3, AttributeModifier.Operation.ADD_NUMBER));
        helmet.setUnbreakable(true);
        ItemMeta helmetMeta = helmet.getItemMeta();
        ((LeatherArmorMeta) helmetMeta).setColor(zombieColorFromLevel.get(level));
        helmet.setItemMeta(helmetMeta);

        z.getEquipment().setHelmet(helmet);


        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.setUnbreakable(true);
        chestplate.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                3, AttributeModifier.Operation.ADD_NUMBER));
        chestplate.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                8, AttributeModifier.Operation.ADD_NUMBER));
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        ((LeatherArmorMeta) chestplateMeta).setColor(zombieColorFromLevel.get(level));
        chestplate.setItemMeta(chestplateMeta);
        z.getEquipment().setChestplate(chestplate);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.setUnbreakable(true);
        leggings.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                3, AttributeModifier.Operation.ADD_NUMBER));
        leggings.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                6, AttributeModifier.Operation.ADD_NUMBER));
        ItemMeta leggingsMeta = leggings.getItemMeta();
        ((LeatherArmorMeta) leggingsMeta).setColor(zombieColorFromLevel.get(level));
        leggings.setItemMeta(leggingsMeta);
        z.getEquipment().setLeggings(leggings);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.setUnbreakable(true);
        boots.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                3, AttributeModifier.Operation.ADD_NUMBER));
        boots.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Enchantment.PROTECTION_ENVIRONMENTAL.getName(),
                3, AttributeModifier.Operation.ADD_NUMBER));
        ItemMeta bootsMeta = boots.getItemMeta();
        ((LeatherArmorMeta) bootsMeta).setColor(zombieColorFromLevel.get(level));
        boots.setItemMeta(bootsMeta);
        z.getEquipment().setBoots(boots);

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.setUnbreakable(true);
        sword.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Enchantment.DAMAGE_ALL.getName(),
                3, AttributeModifier.Operation.ADD_NUMBER));
        sword.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Enchantment.DAMAGE_ALL.getName(),
                0.8, AttributeModifier.Operation.ADD_NUMBER));
        z.getEquipment().setItemInMainHand(sword);


        z.getEquipment().setHelmetDropChance(100);
        z.getEquipment().setChestplateDropChance(100);
        z.getEquipment().setLeggingsDropChance(100);
        z.getEquipment().setBootsDropChance(100);
        z.getEquipment().setItemInHandDropChance(100);
        z.spawnAt(spawnLoc, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
    }
}
