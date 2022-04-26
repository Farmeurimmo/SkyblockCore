package main.java.fr.verymc.minions;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MinionsListener implements Listener {

    @EventHandler
    public void onInventoryClicEvent(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (current == null) return;
        Material currentType = current.getType();
        if (currentType == null) return;
        if (e.getView().getTitle() == null) return;
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().contains("§6Menu du minion " + MinionType.PIOCHEUR.getName(MinionType.PIOCHEUR))) {
            e.setCancelled(true);
            if (!MinionsGui.instance.minionOpened.containsKey(player.getName())) {
                return;
            }
            if (currentType == Material.CHEST) {
                if (MinionsGui.instance.linking.contains(player)) {
                    MinionsGui.instance.linking.remove(player);
                    player.sendMessage("§6§lMinions §8» §fMode édition du coffre du minion §cdésactivé§f.");
                } else {
                    MinionsGui.instance.linking.add(player);
                    player.sendMessage("§6§lMinions §8» §fMode édition du coffre du minion §aactivé§f. (Pour lier un coffre, " +
                            "faites sneak + clic droit ou gauche)");
                    player.closeInventory();
                }
                return;
            }
            if (currentType == Material.DRAGON_BREATH) {
                MinionManager.instance.giveMinionItemForExistingMinion(player, MinionsGui.instance.minionOpened.get(player.getName()));
                MinionManager.instance.removeMinion(MinionsGui.instance.minionOpened.get(player.getName()));
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    @Override
                    public void run() {
                        if (MinionsGui.instance.linking.contains(player)) MinionsGui.instance.linking.remove(player);
                        if (MinionsGui.instance.minionOpened.containsKey(player.getName()))
                            MinionsGui.instance.minionOpened.remove(player.getName());
                    }
                }, 1);
                player.sendMessage("§6§lMinions §8» §fMinion §arécupéré§f, les niveau de celui ci ont été sauvegardés.");
                player.closeInventory();
                return;
            }
            if (currentType == Material.BLAZE_POWDER) {
                MinionsGui.instance.openUpgradeShop(player, MinionsGui.instance.minionOpened.get(player.getName()));
                return;
            }
            if (currentType == Material.PAPER) {
                MinionsGui.instance.minionParamGui(player, MinionsGui.instance.minionOpened.get(player.getName()));
                return;
            }
            return;
        }
        if (e.getView().getTitle().contains("§6Améliorations du minion")) {
            e.setCancelled(true);
            if (!MinionsGui.instance.minionOpened.containsKey(player.getName())) {
                return;
            }
            if (currentType == Material.ARROW) {
                MinionsGui.instance.minionMainGui(player, MinionsGui.instance.minionOpened.get(player.getName()));
                return;
            }
            if (currentType == Material.PLAYER_HEAD) {
                int level = current.getAmount() - 1;
                if (!MinionManager.instance.getBeforeBooleanUpgrade(level, MinionsGui.instance.minionOpened.get(player.getName()))) {
                    double cost = MinionManager.instance.getNextUpgradeCost(level, MinionsGui.instance.minionOpened.get(player.getName()).getLevelInt());
                    if (cost > 0) {
                        if (EcoAccountsManager.instance.getMoneyUUID(player.getUniqueId()) >= cost) {
                            EcoAccountsManager.instance.removeFounds(player, cost, true);
                            MinionsGui.instance.minionOpened.get(player.getName()).setLevelInt(level);
                            MinionsGui.instance.openUpgradeShop(player, MinionsGui.instance.minionOpened.get(player.getName()));
                            return;
                        }
                    }
                }
            }
        }
        if (e.getView().getTitle().contains("§6Paramètres du minion ")) {
            e.setCancelled(true);
            if (!MinionsGui.instance.minionOpened.containsKey(player.getName())) {
                return;
            }
            if (currentType == Material.ARROW) {
                MinionsGui.instance.minionMainGui(player, MinionsGui.instance.minionOpened.get(player.getName()));
                return;
            }
            if (currentType == Material.FURNACE) {
                if (MinionsGui.instance.minionOpened.get(player.getName()).isAutoSmelt()) {
                    MinionsGui.instance.minionOpened.get(player.getName()).setAutoSmelt(false);
                } else {
                    MinionsGui.instance.minionOpened.get(player.getName()).setAutoSmelt(true);
                }
                MinionsGui.instance.minionParamGui(player, MinionsGui.instance.minionOpened.get(player.getName()));
                return;
            }
        }

    }

    @EventHandler
    public void interactMinion(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getLocation() == null) return;
        Location locBloc = e.getClickedBlock().getLocation();
        Player player = e.getPlayer();
        if (MinionsGui.instance.linking.contains(player) && MinionsGui.instance.minionOpened.containsKey(player.getName()) && player.isSneaking()) {
            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
                return;
            }
            if (locBloc.getBlock().getType() == Material.CHEST) {
                MinionsGui.instance.minionOpened.get(player.getName()).setChestLinked(locBloc.getBlock());
                MinionsGui.instance.minionOpened.remove(player.getName());
                MinionsGui.instance.linking.remove(player);
                player.sendMessage("§6§lMinions §8» §fCoffre en x: " + locBloc.getBlockX() + " y: " + locBloc.getBlockY() +
                        " z: " + locBloc.getBlockZ() + " lié avec succès avec le minion.");
                player.sendMessage("§6§lMinions §8» §fMode édition du coffre du minion §cdésactivé§f.");
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void placeMinion(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (e.getItem().getType() == null) return;
        if (!e.getItem().isUnbreakable()) {
            return;
        }
        if (e.getItem().getType() == Material.DRAGON_BREATH && e.getPlayer().isSneaking()) {
            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
                return;
            }
            int plcount = 0;
            for (Minion minion : MinionManager.instance.minions) {
                if (minion.getOwnerUUID() == e.getPlayer().getUniqueId()) {
                    plcount += 1;
                }
            }
            if (plcount >= 3) {
                return;
            }
            for (Minion minions : MinionManager.instance.minions) {
                if (e.getClickedBlock().getLocation().getBlock().getLocation().add(0, 1, 0).equals(minions.getBlocLocation().getBlock().getLocation())) {
                    e.setCancelled(true);
                    return;
                }
            }
            if (e.getItem().getDisplayName().contains(MinionType.PIOCHEUR.getName(MinionType.PIOCHEUR))
                    && e.getItem().getDisplayName().contains("§6Minion")) {
                Player player = e.getPlayer();
                Integer levelInt = 0;
                if (e.getItem().getLore().size() >= 1) {
                    String lore1 = e.getItem().getLore().get(0);
                    lore1 = lore1.replace("§6Niveau §e", "");
                    levelInt = Integer.parseInt(lore1);
                }
                e.setCancelled(true);
                if (!MinionManager.instance.faceBloc.contains(player.getFacing())) {
                    player.sendMessage("§6§lMinions §8» §fVous devez placer un minion dans une direction précise; " +
                            "sud, nord, est, ouest");
                    return;
                }
                MinionManager.instance.addMinion(player, e.getClickedBlock().getLocation(),
                        MinionType.PIOCHEUR, player.getFacing(), levelInt);
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                return;
            }
        }
    }

    @EventHandler
    public void exploOnMinion(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (e.getEntity().getType().equals(EntityType.ARMOR_STAND) && !e.getEntity().hasGravity()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        if (e.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            Entity clicked = e.getRightClicked();
            if (clicked.isInvulnerable()) {
                Minion minion = null;
                for (Minion minions : MinionManager.instance.minions) {
                    if (!e.getRightClicked().getLocation().equals(minions.getBlocLocation())) {
                        continue;
                    }
                    minion = minions;
                    break;
                }
                if (minion == null) return;
                e.setCancelled(true);
                if (minion.getOwnerUUID().equals(e.getPlayer().getUniqueId())) {
                    MinionsGui.instance.minionMainGui(player, minion);
                    return;
                }
                if (player.hasPermission("*")) {
                    MinionsGui.instance.minionMainGui(player, minion);
                    player.sendMessage("§6§lMinions §8» §fVous venez d'entrer de force dans le minion de " + minion.getOwnerName() + ".");
                    return;
                }
                player.sendMessage("§6§lMinions §8» §fCe minion ne vous appartient pas.");
                return;
            }
        }
    }

    @EventHandler
    public void pistonExtend(BlockPistonExtendEvent e) {
        /*int sticky = 0;
        for (Block block : e.getBlocks()) {
            if (block.getType() == Material.SLIME_BLOCK || block.getType() == Material.HONEY_BLOCK) {
                sticky += 1;
            }
        }
        if (sticky == 0) return;*/
        for (Entity entity : e.getBlock().getWorld().getNearbyEntities(e.getBlock().getLocation(), 13, 13, 13)) {
            if (entity instanceof ArmorStand) {
                if (!entity.hasGravity()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void pistonRetract(BlockPistonRetractEvent e) {
        /*int sticky = 0;
        for (Block block : e.getBlocks()) {
            if (block.getType() == Material.SLIME_BLOCK || block.getType() == Material.HONEY_BLOCK) {
                sticky += 1;
            }
        }
        if (sticky == 0) return;*/
        for (Entity entity : e.getBlock().getWorld().getNearbyEntities(e.getBlock().getLocation(), 13, 13, 13)) {
            if (entity instanceof ArmorStand) {
                if (!entity.hasGravity()) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
