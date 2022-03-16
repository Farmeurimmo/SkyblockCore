package main.java.fr.verymc.minions;

import main.java.fr.verymc.core.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MinionManager {

    public static MinionManager instance;
    public ArrayList<Minion> minions = new ArrayList<>();

    public HashMap<Player, Minion> openedMinion = new HashMap<>();

    public MinionManager() {
        instance = this;
        readForMinions();
    }

    public void readForMinions() {
        if (Main.instance.getDataMinion().getConfigurationSection("Minions.mineur") == null) {
            return;
        }
        for (String id : Main.instance.getDataMinion().getConfigurationSection("Minions.mineur").getKeys(false)) {
            Long idMinion = Long.parseLong(id);
            String ownerS = Main.instance.getDataMinion().getString("Minions.mineur." + id + ".ownerS");
            UUID ownerUUID = UUID.fromString(Main.instance.getDataMinion().getString("Minions.mineur." + id + ".ownerUUID"));
            Integer levelInt = Main.instance.getDataMinion().getInt("Minions.mineur." + id + ".levelint");
            Integer delay = Main.instance.getDataMinion().getInt("Minions.mineur." + id + ".delay");
            Location blocLoc = Main.instance.getDataMinion().getLocation("Minions.mineur." + id + ".blocLoc");
            MinionType minionType = MinionType.valueOf(Main.instance.getDataMinion().getString("Minions.mineur." + id + ".minionType"));
            BlockFace blockFace = BlockFace.valueOf(Main.instance.getDataMinion().getString("Minions.mineur." + id + ".blocFace"));

            minions.add(new Minion(idMinion, ownerS, ownerUUID, levelInt, blocLoc, minionType, delay, blockFace));
        }
    }

    public void giveMinionItem(Player player, String type) {
        ItemStack custom1 = new ItemStack(Material.DRAGON_BREATH, 1);
        custom1.setUnbreakable(true);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Minion " + type);
        custom1.setItemMeta(meta1);

        player.getInventory().addItem(custom1);
    }

    public Integer getMinerDelay(Integer level) {
        Integer toReturn = 100;
        if (level == 0) {
            toReturn = 5;
        }
        return toReturn;
    }

    public void addMinion(Player player, Location blocLoc, MinionType minionType, BlockFace blockFace) {
        Long id = System.currentTimeMillis();
        Integer delay = getMinerDelay(0);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".ownerS", player.getName());
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".ownerUUID", player.getUniqueId().toString());
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".levelInt", 0);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".blocLoc", blocLoc);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".minionType", minionType.getName(minionType));
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".delay", delay);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".blocFace", blockFace.toString());
        Main.instance.saveDataMinions();
        minions.add(new Minion(id, player.getName(), player.getUniqueId(), 0, blocLoc, minionType, delay, blockFace));
    }

    public void removeMinion(Location blocLocMinion) {
        Minion minion = null;
        for (Minion aa : minions) {
            if (aa.getBlocLocation() != blocLocMinion) {
                continue;
            }
            minion = aa;
            break;
        }
        if (minion == null) {
            return;
        }

        Main.instance.getDataMinion().set("Minions.mineur." + minion.getID(), null);
        Main.instance.saveDataMinions();
    }

}
