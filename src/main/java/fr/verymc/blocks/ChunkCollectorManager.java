package main.java.fr.verymc.blocks;

import main.java.fr.verymc.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ChunkCollectorManager {

    public static HashMap<Location, Long> blcchunk = new HashMap<>();

    public static void GiveChest(Player player, int i) {
        int a = 0;
        if (Main.instance.getDatablc().get("ChunkHoppeur.num") == null) {
            a = 1;
        } else {
            a = Main.instance.getDatablc().getInt("ChunkHoppeur.num");
            a += 1;
        }
        ItemStack aa = new ItemStack(Material.HOPPER);
        ItemMeta ameta = aa.getItemMeta();
        if (i > 0) {
            a = i;
        } else {
            Main.instance.getDatablc().set("ChunkHoppeur.num", a);
            Main.instance.saveData();
        }
        ameta.setDisplayName("§6Chunk Hoppeur §c(id#" + a + ")");
        aa.setUnbreakable(true);
        aa.setItemMeta(ameta);

        player.getInventory().addItem(aa);
    }

    public static void PlaceChest(Player player, Long chunkkey, Location block, int num) {
        Main.instance.getDatablc().set("ChunkHoppeur." + chunkkey + "." + num, block);
        Main.instance.saveData();
        blcchunk.put(block, chunkkey);
    }

    public static void ReadFromFile() {
        if (!Main.instance.getDatablc().isSet("ChunkHoppeur")) {
            return;
        }
        for (String aa : Main.instance.getDatablc().getConfigurationSection("ChunkHoppeur").getKeys(false)) {
            if (aa.contains("num")) {
                continue;
            }
            for (String bb : Main.instance.getDatablc().getConfigurationSection("ChunkHoppeur." + aa).getKeys(false)) {
                blcchunk.put(Main.instance.getDatablc().getLocation("ChunkHoppeur." + aa + "." + bb), Long.parseLong(aa.replace("'", "")));
            }
        }
    }
}
