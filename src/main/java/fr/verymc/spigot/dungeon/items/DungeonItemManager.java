package main.java.fr.verymc.spigot.dungeon.items;

import com.google.common.collect.Multimap;
import main.java.fr.verymc.spigot.dungeon.items.sets.ZombieSet;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DungeonItemManager {

    public static DungeonItemManager instance;

    public final NumberFormat numberFormat;

    public DungeonItemManager() {
        instance = this;

        numberFormat = NumberFormat.getNumberInstance();


        new ZombieSet();
    }

    public List<String> getStatsLore(ItemStack itemStack) {
        List<String> stats = (itemStack.hasLore() ? itemStack.getLore() : new ArrayList<String>());
        if (!itemStack.hasAttributeModifiers()) {
            stats.add("");
            return stats;
        }
        stats.add("");
        Multimap<Attribute, AttributeModifier> modifiers = itemStack.getAttributeModifiers();
        Collection<AttributeModifier> current = modifiers.get(Attribute.GENERIC_ARMOR);
        for (AttributeModifier modifier : current) {
            if (modifier.getName().equals(Attribute.GENERIC_ARMOR.toString()))
                stats.add(getDefenseString(modifier.getAmount()));
        }
        current = modifiers.get(Attribute.GENERIC_MAX_HEALTH);
        for (AttributeModifier modifier : current) {
            if (modifier.getName().equals(Attribute.GENERIC_MAX_HEALTH.toString()))
                stats.add(getHealthString(modifier.getAmount()));
        }
        current = modifiers.get(Attribute.GENERIC_ATTACK_SPEED);
        for (AttributeModifier modifier : current) {
            if (modifier.getName().equals(Attribute.GENERIC_ATTACK_SPEED.toString()))
                stats.add(getAttackSpeed(modifier.getAmount()));
        }
        current = modifiers.get(Attribute.GENERIC_ATTACK_DAMAGE);
        for (AttributeModifier modifier : current) {
            if (modifier.getName().equals(Attribute.GENERIC_ATTACK_DAMAGE.toString()))
                stats.add(getAttackDamage(modifier.getAmount()));
        }
        return stats;
    }

    public String getDefenseString(Double amount) {
        String toReturn = "§a✤ Défense ";
        if (amount >= 0) {
            toReturn += "+";
        } else {
            toReturn += "-";
        }
        return toReturn + numberFormat.format(amount);
    }

    public String getHealthString(Double amount) {
        String toReturn = "§2✤ Vie ";
        if (amount >= 0) {
            toReturn += "+";
        } else {
            toReturn += "-";
        }
        return toReturn + numberFormat.format(amount);
    }

    public String getAttackDamage(Double amount) {
        String toReturn = "§c✸ Dégâts ";
        if (amount >= 0) {
            toReturn += "+";
        } else {
            toReturn += "-";
        }
        return toReturn + numberFormat.format(amount);
    }

    public String getAttackSpeed(Double amount) {
        String toReturn = "§e✦ Vitesse d'attaque ";
        if (amount >= 0) {
            toReturn += "+";
        } else {
            toReturn += "-";
        }
        return toReturn + numberFormat.format(amount);
    }

}
