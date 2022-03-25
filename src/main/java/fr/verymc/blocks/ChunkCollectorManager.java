package main.java.fr.verymc.blocks;

import main.java.fr.verymc.config.ConfigManager;
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
        if (ConfigManager.instance.getDataBloc().get("ChunkHoppeur.num") == null) {
            a = 1;
        } else {
            a = ConfigManager.instance.getDataBloc().getInt("ChunkHoppeur.num");
            a += 1;
        }
        ItemStack aa = new ItemStack(Material.HOPPER);
        ItemMeta ameta = aa.getItemMeta();
        if (i > 0) {
            a = i;
        } else {
            ConfigManager.instance.getDataBloc().set("ChunkHoppeur.num", a);
            ConfigManager.instance.saveData();
        }
        ameta.setDisplayName("§6Chunk Hoppeur §c(id#" + a + ")");
        aa.setUnbreakable(true);
        aa.setItemMeta(ameta);

        player.getInventory().addItem(aa);
    }

    public static void PlaceChest(Player player, Long chunkkey, Location block, int num) {
        ConfigManager.instance.getDataBloc().set("ChunkHoppeur." + chunkkey + "." + num, block);
        ConfigManager.instance.saveData();
        blcchunk.put(block, chunkkey);
    }

    public static void ReadFromFile() {
        if (!ConfigManager.instance.getDataBloc().isSet("ChunkHoppeur")) {
            return;
        }
        for (String aa : ConfigManager.instance.getDataBloc().getConfigurationSection("ChunkHoppeur").getKeys(false)) {
            if (aa.contains("num")) {
                continue;
            }
            for (String bb : ConfigManager.instance.getDataBloc().getConfigurationSection("ChunkHoppeur." + aa).getKeys(false)) {
                blcchunk.put(ConfigManager.instance.getDataBloc().getLocation("ChunkHoppeur." + aa + "." + bb), Long.parseLong(aa.replace("'", "")));
            }
        }
    }
}
