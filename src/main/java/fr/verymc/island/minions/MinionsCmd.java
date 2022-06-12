package main.java.fr.verymc.island.minions;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;

public class MinionsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 2) {
            sender.sendMessage("§6§lMinions §8» §fErreur, utilisation: /minion <pseudo> <minion>");
            return true;
        }

        if (args[0] == null) return true;
        if (Bukkit.getPlayer(args[0]) == null) return true;

        Player ptarget = Bukkit.getPlayer(args[0]);

        if (ptarget.getInventory().firstEmpty() == -1) {
            sender.sendMessage("§6§lMinions §8» §fErreur, la cible ne possède pas la place dans son inventaire.");
            return true;
        }

        if (args[1].equalsIgnoreCase("piocheur")) {
            MinionManager.instance.giveMinionItem(ptarget, MinionType.PIOCHEUR.name());
            ptarget.sendMessage("§6§lMinions §8» §fVous avez reçu §ax1 Minion Piocheur §fdans votre inventaire.");
            sender.sendMessage("§6§lMinions §8» §fLa cible vient de recevoir §ax1 Minion Piocheur§f.");
            return true;
        }
        if (args[1].equalsIgnoreCase("repop")) {
            ArrayList<Minion> minions = new ArrayList<>();
            for (Island island : IslandManager.instance.islands) {
                minions.addAll(island.getMinions());
            }
            for (Minion minion : minions) {
                if (!minion.getBlocLocation().isChunkLoaded()) {
                    minion.getBlocLocation().getChunk().load();
                }
                final ArmorStand stand = (ArmorStand) minion.getBlocLocation().getWorld().spawnEntity(minion.getBlocLocation(), EntityType.ARMOR_STAND);
                final EntityEquipment equipment = stand.getEquipment();
                stand.setMetadata("minion", new FixedMetadataValue(Main.instance, true));
                stand.setVisible(true);
                stand.setCustomName("§eMinion " + minion.getMinionType().getName(minion.getMinionType()));
                stand.setCustomNameVisible(true);
                stand.setGravity(false);
                stand.setArms(true);
                stand.setSmall(true);
                stand.setBasePlate(false);
                stand.setInvulnerable(true);
                final ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
                final LeatherArmorMeta lam3 = (LeatherArmorMeta) chestPlate.getItemMeta();
                lam3.setColor(Color.fromRGB(249, 128, 29));
                chestPlate.setItemMeta((ItemMeta) lam3);
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
            }

        }
        if (args[1].equalsIgnoreCase("repopnearest")) {
            ArrayList<Minion> minions = new ArrayList<>();
            for (Island island : IslandManager.instance.islands) {
                minions.addAll(island.getMinions());
            }
            for (Minion minion : minions) {
                if (minion.getBlocLocation().getBlock().equals(ptarget.getLocation().getBlock())) {
                    final ArmorStand stand = (ArmorStand) minion.getBlocLocation().getWorld().spawnEntity(minion.getBlocLocation(), EntityType.ARMOR_STAND);
                    final EntityEquipment equipment = stand.getEquipment();
                    stand.setMetadata("minion", new FixedMetadataValue(Main.instance, true));
                    stand.setVisible(true);
                    stand.setCustomName("§eMinion " + minion.getMinionType());
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

                    equipment.setHelmet(PreGenItems.instance.getHead(ptarget));

                    stand.setRightLegPose(new EulerAngle(0.0, 0.0, -50.0));
                    stand.setLeftLegPose(new EulerAngle(0.0, 0.0, 50.0));
                    stand.setRightArmPose(new EulerAngle(206.0, 0.0, 0.0));

                    ptarget.sendMessage("§6§lMinions §8» §aRepoped nearest minion, loc: " + minion.getBlocLocation().getX() + " " + minion.getBlocLocation().getY() + " " + minion.getBlocLocation().getZ());
                    break;
                }
            }
            return true;
        }

        sender.sendMessage("Minions disponibles: Piocheur");

        return true;
    }
}
