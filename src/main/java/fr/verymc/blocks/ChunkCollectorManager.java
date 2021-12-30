package main.java.fr.verymc.blocks;

import main.java.fr.verymc.core.Main;
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
        if (Main.getInstance().getDatablc().get("ChunkHoppeur.num") == null) {
            a = 1;
        } else {
            a = Main.getInstance().getDatablc().getInt("ChunkHoppeur.num");
            a += 1;
        }
        ItemStack aa = new ItemStack(Material.HOPPER);
        ItemMeta ameta = aa.getItemMeta();
        if (i > 0) {
            a = i;
        } else {
            Main.getInstance().getDatablc().set("ChunkHoppeur.num", a);
            Main.getInstance().saveData();
        }
        ameta.setDisplayName("§6Chunk Hoppeur §c(id#" + a + ")");
        aa.setUnbreakable(true);
        aa.setItemMeta(ameta);

        player.getInventory().addItem(aa);
    }

    public static void PlaceChest(Player player, Long chunkkey, Location block, int num) {
        Main.getInstance().getDatablc().set("ChunkHoppeur." + chunkkey + "." + num, block);
        Main.getInstance().saveData();
        blcchunk.put(block, chunkkey);
    }

    public static void ReadFromFile() {
        if (!Main.getInstance().getDatablc().isSet("ChunkHoppeur")) {
            return;
        }
        for (String aa : Main.getInstance().getDatablc().getConfigurationSection("ChunkHoppeur").getKeys(false)) {
            if (aa.contains("num")) {
                continue;
            }
            for (String bb : Main.getInstance().getDatablc().getConfigurationSection("ChunkHoppeur." + aa).getKeys(false)) {
                blcchunk.put(Main.getInstance().getDatablc().getLocation("ChunkHoppeur." + aa + "." + bb), Long.parseLong(aa.replace("'", "")));
            }
        }
    }
}
