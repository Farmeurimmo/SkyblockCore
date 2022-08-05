package main.java.fr.verymc.spigot.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.fr.verymc.spigot.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectConverter {

    public static final String SEPARATOR = ";";
    public static final String ITEMSTACK_SEPARATOR = ",";
    public static final String SEPARATOR_ELEMENT = "~";
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
        return itemStack.getType() + ITEMSTACK_SEPARATOR + itemStack.getAmount();
    }

    public ItemStack fromString(String str) {
        String[] strings = str.split(ITEMSTACK_SEPARATOR);
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

    public HashMap<String, String> stringToHashMap(String str) {
        HashMap<String, String> toReturn = new HashMap<>();
        if (str == null) {
            return toReturn;
        }
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            toReturn.put(entry.getKey(), entry.getValue().toString());
        }
        return toReturn;
    }

}




