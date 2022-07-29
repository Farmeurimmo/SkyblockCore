package main.java.fr.verymc.island.protections;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public enum IslandSettings {

    MOB_GRIEFING("§7Grief/Explosion des mobs"),
    MOB_SPAWNING("§7Spawn des mobs"),
    BLOCK_BURNING("§7Combustion des blocs"),
    LIGHTNING_STRIKE("§7Foudre"),
    BLOCK_EXPLOSION("§7Explosions des blocs"),
    TIME_DEFAULT("§7Temps par défaut"),
    TIME_DAY("§7Jour permanant"),
    TIME_CREPUSCULE("§7Crépuscule permanant"),
    TIME_NIGHT("§7Nuit permanante"),
    WEATHER_DEFAULT("§7Météo par défaut"),
    WEATHER_RAIN("§7Météo pluvieuse/neigeuse"),
    WEATHER_CLEAR("§7Météo claire");

    private final String desc;

    IslandSettings(String desc) {
        this.desc = desc;
    }

    public static IslandSettings matchSettings(String string) {
        for (IslandSettings setting : values()) {
            if (string.contains(setting.name())) {
                return setting;
            }
        }
        return null;
    }

    public static ItemStack getItemForSetting(IslandSettings setting) {
        return switch (setting) {
            case MOB_GRIEFING -> new ItemStack(Material.CREEPER_SPAWN_EGG);
            case MOB_SPAWNING -> new ItemStack(Material.SPAWNER);
            case BLOCK_BURNING -> new ItemStack(Material.FLINT_AND_STEEL);
            case LIGHTNING_STRIKE -> new ItemStack(Material.ANVIL);
            case BLOCK_EXPLOSION -> new ItemStack(Material.TNT);
            case TIME_DEFAULT, TIME_CREPUSCULE, TIME_DAY, TIME_NIGHT -> new ItemStack(Material.DAYLIGHT_DETECTOR);
            case WEATHER_CLEAR, WEATHER_DEFAULT, WEATHER_RAIN -> new ItemStack(Material.CLOCK);
        };
    }

    public static IslandSettings getNext(IslandSettings setting) {
        if (setting.toString().contains("TIME")) {
            return switch (setting) {
                case TIME_DEFAULT -> TIME_DAY;
                case TIME_DAY -> TIME_CREPUSCULE;
                case TIME_CREPUSCULE -> TIME_NIGHT;
                default -> TIME_DEFAULT;
            };
        } else {
            return switch (setting) {
                case WEATHER_DEFAULT -> WEATHER_RAIN;
                case WEATHER_RAIN -> WEATHER_CLEAR;
                default -> WEATHER_DEFAULT;
            };
        }
    }

    public static IslandSettings getNextWeather(IslandSettings setting) {
        return switch (setting) {
            case WEATHER_DEFAULT -> WEATHER_RAIN;
            case WEATHER_RAIN -> WEATHER_CLEAR;
            default -> WEATHER_DEFAULT;
        };
    }

    @NotNull
    public static IslandSettings getTimeSetting(ArrayList<IslandSettings> settings) {
        for (IslandSettings setting : settings) {
            if (setting == TIME_DEFAULT || setting == TIME_DAY || setting == TIME_CREPUSCULE || setting == TIME_NIGHT) {
                return setting;
            }
        }
        return TIME_DEFAULT;
    }

    @NotNull
    public static IslandSettings getWeatherSetting(ArrayList<IslandSettings> settings) {
        for (IslandSettings setting : settings) {
            if (setting == WEATHER_CLEAR || setting == WEATHER_DEFAULT || setting == WEATHER_RAIN) {
                return setting;
            }
        }
        return WEATHER_DEFAULT;
    }

    public String getDesc() {
        return desc;
    }


}
