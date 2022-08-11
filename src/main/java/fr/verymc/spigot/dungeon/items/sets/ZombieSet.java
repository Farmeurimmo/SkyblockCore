package main.java.fr.verymc.spigot.dungeon.items.sets;

import main.java.fr.verymc.spigot.dungeon.items.DungeonItemManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ZombieSet {

    public static final int helmet_defense = 3;
    public static final int helmet_defense_toughness = 3;
    public static final double helmet_health_boost = 1;
    public static final int chestplate_defense = 8;
    public static final int chestplate_defense_toughness = 3;
    public static final double chestplate_health_boost = 2.5;
    public static final int legging_defense = 6;
    public static final int legging_defense_toughness = 3;
    public static final double legging_health_boost = 100.5;
    public static final int boots_defense = 3;
    public static final int boots_defense_toughness = 3;
    public static final double boots_health_boost = 100;
    public static final double sword_speed_attack_add = 0.8;
    public static final double sword_attack_damage_add = 3;
    public static ZombieSet instance;


    public ZombieSet() {
        instance = this;
    }

    public ItemStack getZombieHelmet(Color color) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

        helmet.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS.toString(),
                helmet_defense_toughness, AttributeModifier.Operation.ADD_NUMBER));
        helmet.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Attribute.GENERIC_ARMOR.toString(),
                helmet_defense, AttributeModifier.Operation.ADD_NUMBER));
        helmet.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(Attribute.GENERIC_MAX_HEALTH.toString(),
                helmet_health_boost, AttributeModifier.Operation.ADD_NUMBER));

        return applyCommonPart(helmet, color);
    }

    public ItemStack getZombieChestPlate(Color color) {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

        chestplate.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS.toString(),
                chestplate_defense_toughness, AttributeModifier.Operation.ADD_NUMBER));
        chestplate.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Attribute.GENERIC_ARMOR.toString(),
                chestplate_defense, AttributeModifier.Operation.ADD_NUMBER));
        chestplate.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(Attribute.GENERIC_MAX_HEALTH.toString(),
                chestplate_health_boost, AttributeModifier.Operation.ADD_NUMBER));

        return applyCommonPart(chestplate, color);
    }

    public ItemStack getZombieLegging(Color color) {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);

        leggings.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS.toString(),
                legging_defense_toughness, AttributeModifier.Operation.ADD_NUMBER));
        leggings.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Attribute.GENERIC_ARMOR.toString(),
                legging_defense, AttributeModifier.Operation.ADD_NUMBER));
        leggings.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(Attribute.GENERIC_MAX_HEALTH.toString(),
                legging_health_boost, AttributeModifier.Operation.ADD_NUMBER));

        return applyCommonPart(leggings, color);
    }

    public ItemStack getZombieBoots(Color color) {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        boots.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS.toString(),
                boots_defense_toughness, AttributeModifier.Operation.ADD_NUMBER));
        boots.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Attribute.GENERIC_ARMOR.toString(),
                boots_defense, AttributeModifier.Operation.ADD_NUMBER));
        boots.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(Attribute.GENERIC_MAX_HEALTH.toString(),
                boots_health_boost, AttributeModifier.Operation.ADD_NUMBER));

        return applyCommonPart(boots, color);
    }

    public ItemStack getZombieSword() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);

        sword.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE.toString(),
                sword_attack_damage_add, AttributeModifier.Operation.ADD_NUMBER));
        sword.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Attribute.GENERIC_ATTACK_SPEED.toString(),
                sword_speed_attack_add, AttributeModifier.Operation.ADD_NUMBER));

        return applyCommonPart(sword, null);
    }

    public ItemStack applyCommonPart(ItemStack itemStack, Color color) {
        if (color != null) {
            ItemMeta helmetMeta = itemStack.getItemMeta();
            ((LeatherArmorMeta) helmetMeta).setColor(color);
            itemStack.setItemMeta(helmetMeta);
        }

        itemStack.setUnbreakable(true);

        itemStack.setLore(DungeonItemManager.instance.getStatsLore(itemStack));

        itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        return itemStack;
    }

}
