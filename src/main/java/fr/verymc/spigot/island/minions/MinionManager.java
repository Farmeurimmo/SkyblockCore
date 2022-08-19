package main.java.fr.verymc.spigot.island.minions;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.storage.AsyncConfig;
import main.java.fr.verymc.spigot.core.storage.ConfigManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class MinionManager {

    public static MinionManager instance;
    public static Integer level1 = 50000;
    public static Integer level2 = 75000;
    public static Integer level3 = 100000;
    public static Integer level4 = 150000;
    public static Integer level5 = 250000;
    public static Integer level6 = 500000;
    public ArrayList<BlockFace> faceBloc = new ArrayList<>();


    public MinionManager() {
        instance = this;
        faceBloc.addAll(Arrays.asList(BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST));

        if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
            new MinionsGui();
            new MinionHarvest();
        }
    }

    public void giveMinionItem(Player player, String type) {
        ItemStack custom1 = new ItemStack(Material.DRAGON_BREATH, 1);
        custom1.setUnbreakable(true);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Minion " + type);
        meta1.setLore(Arrays.asList("§6Niveau §e0"));
        custom1.setItemMeta(meta1);

        player.getInventory().addItem(custom1);
    }

    public void giveMinionItemForExistingMinion(Player player, Minion minion) {
        ItemStack custom1 = new ItemStack(Material.DRAGON_BREATH, 1);
        custom1.setUnbreakable(true);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Minion " + minion.getMinionType().getName(minion.getMinionType()));
        meta1.setLore(Arrays.asList("§6Niveau §e" + minion.getLevelInt()));
        custom1.setItemMeta(meta1);

        player.getInventory().addItem(custom1);
    }

    public Boolean getBeforeBooleanUpgrade(Integer level, Minion minion) {
        if (level > minion.getLevelInt()) {
            return false;
        } else {
            return true;
        }
    }

    public Integer getMinerDelay(Integer level) {
        return switch (level) {
            case 1 -> 9;
            case 2 -> 8;
            case 3 -> 7;
            case 4 -> 6;
            case 5 -> 5;
            case 6 -> 4;
            default -> 10;
        };
    }

    public Integer getNextUpgradeCost(Integer level, Integer currentLevel) {
        Integer toReturn = 0;
        if (Objects.equals(currentLevel, level)) {
            return switch (level) {
                case 2 -> level2;
                case 3 -> level3;
                case 4 -> level4;
                case 5 -> level5;
                case 6 -> level6;
                default -> level1;
            };
        } else {
            for (int i = currentLevel + 1; i <= level; i++) {
                if (i == 1) toReturn += level1;
                if (i == 2) toReturn += level2;
                if (i == 3) toReturn += level3;
                if (i == 4) toReturn += level4;
                if (i == 5) toReturn += level5;
                if (i == 6) toReturn += level6;
            }
        }
        return toReturn;
    }

    public void makeAllMinionRepop() {
        for (Island island : IslandManager.instance.islands) {
            for (Minion minion : island.getMinions()) {
                spawnMinion(minion);
            }
        }
    }

    public void makeAllMinionDepop() {
        for (Island island : IslandManager.instance.islands) {
            for (Minion minion : island.getMinions()) {
                despawnMinion(minion);
            }
        }
    }

    public void addMinion(Player player, Location blocLoc, MinionType minionType, BlockFace blockFace, Integer levelInt) {
        blocLoc.add(0.5, 1, 0.5);
        blocLoc.setDirection(player.getLocation().getDirection());

        Minion minion = new Minion(levelInt, blocLoc, minionType,
                blockFace, false, null, false);

        spawnMinion(minion);

        IslandManager.instance.getPlayerIsland(player).addMinion(minion);

        player.sendMessage("§6§lMinions §8» §fMinion §aplacé§f, pour qu'il fonctionne, il faut lier un coffre au minion. " +
                "(Aller dans l'inventaire du minion puis cliquer le coffre)");
    }

    public void spawnMinion(Minion minion) {
        try {
            if (minion.getBlocLocation().getDirection() != minion.getBlockFace().getDirection()) {
                minion.getBlocLocation().setDirection(minion.getBlockFace().getDirection());
            }
            final ArmorStand stand = (ArmorStand) minion.getBlocLocation().getWorld().spawnEntity(minion.getBlocLocation(), EntityType.ARMOR_STAND);
            final EntityEquipment equipment = stand.getEquipment();
            stand.setMetadata("minion", new FixedMetadataValue(Main.instance, true));
            stand.setVisible(true);
            stand.setCustomName("§eMinion Piocheur");
            stand.setCustomNameVisible(true);
            stand.setGravity(false);
            stand.setArms(true);
            stand.setSmall(true);
            stand.setBasePlate(false);
            stand.setInvulnerable(true);
            final ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
            final LeatherArmorMeta lam3 = (LeatherArmorMeta) chestPlate.getItemMeta();
            lam3.setColor(Color.fromRGB(249, 128, 29));
            chestPlate.setItemMeta(lam3);
            equipment.setChestplate(chestPlate);
            final ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS, 1);
            final LeatherArmorMeta lam4 = (LeatherArmorMeta) pants.getItemMeta();
            lam4.setColor(Color.fromRGB(249, 128, 29));
            pants.setItemMeta(lam4);
            equipment.setLeggings(pants);
            final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
            final LeatherArmorMeta lam5 = (LeatherArmorMeta) boots.getItemMeta();
            lam5.setColor(Color.fromRGB(249, 128, 29));
            boots.setItemMeta(lam5);
            equipment.setBoots(boots);
            equipment.setItemInMainHand(new ItemStack(Material.DIAMOND_PICKAXE));

            equipment.setHelmet(PreGenItems.instance.getHeadMinion());

            stand.setRightLegPose(new EulerAngle(0.0, 0.0, -50.0));
            stand.setLeftLegPose(new EulerAngle(0.0, 0.0, 50.0));
            stand.setRightArmPose(new EulerAngle(206.0, 0.0, 0.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void despawnMinion(Minion minion) {
        for (Entity entity : Bukkit.getWorld(minion.getBlocLocation().getWorld().getName()).getEntities()) {
            if (!(entity instanceof ArmorStand)) continue;
            if (entity.getLocation().getBlock().getLocation().equals(minion.getBlocLocation().getBlock().getLocation())
                    && !entity.hasGravity()) {
                entity.remove();
            }
        }
    }

    public void removeMinion(Minion minion, Island island) {
        despawnMinion(minion);

        island.removeMinion(minion);
        HashMap<String, Object> toEdit = new HashMap<>();
        toEdit.put(island.getUUID().toString(), null);
        AsyncConfig.instance.setAndSaveAsync(toEdit, ConfigManager.instance.getDataIslands(), ConfigManager.instance.islandsFile);
    }

}
