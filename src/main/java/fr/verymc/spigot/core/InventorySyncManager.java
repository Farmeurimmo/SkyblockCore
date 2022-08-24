package main.java.fr.verymc.spigot.core;

import main.java.fr.verymc.JedisManager;
import main.java.fr.verymc.spigot.utils.InventorySyncUtils;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        player.getActivePotionEffects().clear();
        double health = Double.parseDouble(strings[2]);
        if (health > 20) {
            player.setHealth(20);
            health -= 20;
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1 + (int) health / 4, 0));
        } else {
            player.setHealth(health);
        }
        player.setFoodLevel(Integer.parseInt(strings[3]));
        if (strings.length > 5) {
            for (String s : strings[5].split(ObjectConverter.SEPARATOR)) {
                String[] split = s.split(ObjectConverter.LOC_SEPARATOR);
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
        }
        if (isSync(player)) removeSync(player);
    }

    public void playerQuit(Player player) {
        if (isSync(player)) {
            removeSync(player);
            return;
        }
        StringBuilder potionStr = new StringBuilder();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            potionStr.append(effect.getType().getName()).append(ObjectConverter.LOC_SEPARATOR).append(effect.getDuration()).append(ObjectConverter.LOC_SEPARATOR).append(effect.getAmplifier()).append(ObjectConverter.LOC_SEPARATOR);
        }
        player.getActivePotionEffects().clear();
        JedisManager.instance.sendToRedis("sync:" + player.getUniqueId(), saveInventory(player.getInventory().getContents()) + ObjectConverter.SEPARATOR +
                player.getTotalExperience() + ObjectConverter.SEPARATOR + player.getHealth() + ObjectConverter.SEPARATOR + player.getFoodLevel()
                + ObjectConverter.SEPARATOR + saveInventory(player.getEnderChest().getContents()) + ObjectConverter.SEPARATOR + potionStr);
    }

    public void sendReasonForEventCancelled(Player player) {
        player.sendMessage("""
                §6§lSynchronisation §8» §cVotre inventaire est en synchronisation / n'a pas pu être chargé.
                §cSi le problème persiste, merci de faire un ticket sur discord.\s
                https://discord.verymc.fr""");
    }
}