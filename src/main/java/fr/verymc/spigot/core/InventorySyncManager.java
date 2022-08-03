package main.java.fr.verymc.spigot.core;

import main.java.fr.verymc.spigot.utils.InventorySyncUtils;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;

public class InventorySyncManager {

    public static InventorySyncManager instance;
    public ArrayList<Player> inSync = new ArrayList<>();

    public InventorySyncManager() {
        instance = this;
    }

    public boolean isSync(Player p) {
        return inSync.contains(p);
    }

    public void addSync(Player p) {
        inSync.add(p);
    }

    public void removeSync(Player p) {
        inSync.remove(p);
    }

    public void setInventory(Player player, String dataInv) {
        ItemStack[] inventory;
        try {
            inventory = InventorySyncUtils.instance.itemStackArrayFromBase64(dataInv);
        } catch (IOException e) {
            e.printStackTrace();
            player.kickPlayer("§c§lErreur lors de la récupération de l'inventaire.\n§cMerci de faire un ticket sur discord. \nhttps://discord.verymc.fr");
            return;
        }
        player.getInventory().setContents(inventory);
        player.updateInventory();
    }

    public void setEnderChest(Player player, String dataInv) {
        ItemStack[] inventory;
        try {
            inventory = InventorySyncUtils.instance.itemStackArrayFromBase64(dataInv);
        } catch (IOException e) {
            e.printStackTrace();
            player.kickPlayer("§c§lErreur lors de la récupération de l'EnderChest.\n§cMerci de faire un ticket sur discord. \nhttps://discord.verymc.fr");
            return;
        }
        player.getEnderChest().setContents(inventory);
    }

    public String saveInventory(ItemStack[] items) {
        return InventorySyncUtils.instance.itemStackArrayToBase64(items);
    }

    public void playerJoin(Player player) {
        if (!isSync(player)) addSync(player);
        String[] strings;
        try {
            strings = JedisManager.instance.getFromRedis("sync:" + player.getUniqueId()).split(ObjectConverter.SEPARATOR);
            setInventory(player, strings[0]);
            setEnderChest(player, strings[4]);
        } catch (Exception e) {
            e.printStackTrace();
            playerQuit(player);
            return;
        }
        player.setTotalExperience(Integer.parseInt(strings[1]));
        player.setHealth(Double.parseDouble(strings[2]));
        player.setFoodLevel(Integer.parseInt(strings[3]));
        if (isSync(player)) removeSync(player);
    }

    public void playerQuit(Player player) {
        if (isSync(player)) {
            removeSync(player);
            return;
        }
        JedisManager.instance.sendToRedis("sync:" + player.getUniqueId(), saveInventory(player.getInventory().getContents()) + ObjectConverter.SEPARATOR +
                player.getTotalExperience() + ObjectConverter.SEPARATOR + player.getHealth() + ObjectConverter.SEPARATOR + player.getFoodLevel()
                + ObjectConverter.SEPARATOR + saveInventory(player.getEnderChest().getContents()));
    }

    public void sendReasonForEventCancelled(Player player) {
        player.sendMessage("§6§lSynchronisation §8» §cVotre inventaire est en synchronisation / n'a pas pu être chargé." +
                "\n§cSi le problème persiste, merci de faire un ticket sur discord. \nhttps://discord.verymc.fr");
    }
}