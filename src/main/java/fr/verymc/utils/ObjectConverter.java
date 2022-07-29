package main.java.fr.verymc.utils;

import main.java.fr.verymc.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ObjectConverter {

    public static final String SEPARATOR = ";";
    public static final String SEPARATOR_ELEMENT = "|";
    public static final String LOC_SEPARATOR = "µ";
    public static ObjectConverter instance;

    public ObjectConverter() {
        instance = this;
    }

    public String locationToString(@NotNull Location location) {
        return location.getX() + LOC_SEPARATOR + location.getY() + LOC_SEPARATOR + location.getZ() + LOC_SEPARATOR + location.getPitch() +
                LOC_SEPARATOR + location.getYaw();
    }

    public Location locationFromString(@NotNull String string) {
        String[] splited = string.split(LOC_SEPARATOR);
        double x = Double.parseDouble(splited[0]);
        double y = Double.parseDouble(splited[1]);
        double z = Double.parseDouble(splited[2]);
        float pitch = Float.parseFloat(splited[3]);
        float yaw = Float.parseFloat(splited[4]);
        return new Location(Main.instance.mainWorld, x, y, z, yaw, pitch);
    }

    public String itemStackToString(ItemStack itemStack) {
        return itemStack.getType() + SEPARATOR + itemStack.getAmount();
    }

    public ItemStack fromString(String str) {
        String[] strings = str.split(SEPARATOR);
        if (Material.matchMaterial(strings[0]) == null) {
            return null;
        }
        return new ItemStack(Material.valueOf(strings[0]), Integer.parseInt(strings[1]));
    }

    public String blockToString(Block block) {
        return locationToString(block.getLocation());
    }

    public Block blockFromString(String str) {
        return locationFromString(str).getBlock();
    }

    public List<String> stringToArrayList(String str) {
        str = str.replace("[", "").replace("]", "");
        return Arrays.asList(str.split(","));
    }

    public int getNextCaraPos(String cara, String search, int type) {
        if (type == 0) {
            return search.replace(search.substring(search.indexOf("["), search.indexOf("]") + 1), "").indexOf(cara);
        } else if (type == 1) {
            return search.replace(search.substring(search.indexOf("{"), search.indexOf("}") + 1), "").indexOf(cara);
        }
        return search.indexOf(cara) + 1;
    }

    public HashMap<String, String> stringToHashMap(String str, int type) {
        HashMap<String, String> toReturn = new HashMap<>();
        str = str.replace("{", "").replace("}", "");
        int operations = 0;
        while (str.length() > 0) {
            String worker = "";
            try {
                worker = str.substring(0, getNextCaraPos(",", str, type));
            } catch (Exception e) {
                System.out.println(worker + " " + str);
            }
            String[] strings = worker.split(":");
            if (Arrays.stream(strings).toList().size() == 2) {
                toReturn.put(strings[0], strings[1]);
            }
            str = str.replace(worker, "");
            if (str.startsWith(",")) {
                str.replace(str.substring(0, 1), "");
            }
            operations++;
            if (operations > 999) { //<--- Timeout if there is a problem with the string
                break;
            }
        }
        return toReturn;
    }

}




