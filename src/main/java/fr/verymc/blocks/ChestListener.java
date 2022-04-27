package main.java.fr.verymc.blocks;

import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ChestListener implements Listener {

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getPlayer().isSneaking()) {
            return;
        }
        if (e.getClickedBlock().getType() == Material.CHEST) {
            BlockState bs = e.getClickedBlock().getState();
            org.bukkit.block.Chest hopper = (org.bukkit.block.Chest) bs;
            if (hopper.getCustomName() == null) {
                return;
            }
            if (hopper.getCustomName().contains("§6Player shop")) {
                for (Chest c : ChestManager.instance.chests) {
                    if (c.getType() == 2 && c.getBlock().equals(e.getClickedBlock().getLocation())) {
                        PlayerShopGuis.instance.mainShopGui(c, e.getPlayer());
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void checkForItem(ItemSpawnEvent e) {
        if (!(e.getEntity() instanceof Item)) {
            return;
        }
        if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        for (Chest c : ChestManager.instance.chests) {
            if (c.getType() == 0) {
                if (c.getChunkKey().equals(e.getLocation().getChunk().getChunkKey())) {
                    if (c.getBlock().getBlock().getType() == Material.HOPPER) {
                        Hopper blhopper = (Hopper) c.getBlock().getBlock().getState();
                        if (!blhopper.getCustomName().contains("§6Chunk Hoppeur")) {
                            continue;
                        }
                        ItemStack a = e.getEntity().getItemStack();
                        if (InventoryUtils.instance.getAmountToFillInInv(a, blhopper.getInventory()) > 0) {
                            e.getEntity().remove();
                            blhopper.getInventory().addItem(a);
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void inventoryClicEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (e.getCurrentItem().getItemMeta() == null) {
            return;
        }
        ItemStack current = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        Chest c = null;
        if (PlayerShopGuis.instance.opened.get(p) != null) {
            c = PlayerShopGuis.instance.opened.get(p);
        } else {
            return;
        }
        if (c == null) {
            return;
        }

        if (PlayerShopGuis.instance.itemEditing.containsKey(p)) {
            if (PlayerShopGuis.instance.itemEditing.get(p).equals(c)) {
                if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
                    if (current == null) {
                        return;
                    }
                    if (current.getType() == Material.AIR) {
                        return;
                    }
                    ItemStack item = current.clone();
                    item.setAmount(1);
                    if (item.hasItemMeta()) {
                        p.sendMessage("§6§lPlayerShop §8» §cImpossible de choisir cet item car il possède une metadata.");
                        e.setCancelled(true);
                        return;
                    }
                    PlayerShopGuis.instance.itemEditing.get(p).setItemToBuySell(item);
                    PlayerShopGuis.instance.mainShopGui(c, p);
                    e.setCancelled(true);
                    p.sendMessage("§6§lPlayerShop §8» §fItem défini !");
                    PlayerShopGuis.instance.itemEditing.remove(p);
                } else {
                    p.sendMessage("§6§lPlayerShop §8» §fMerci de choisir un item de votre inventaire !");
                    e.setCancelled(true);
                }
                return;
            }
        }

        if (e.getView().getTitle().contains("§6Player shop de ")) {
            e.setCancelled(true);

            if (c.getOwner().equals(p.getUniqueId())) {
                if (current.getType().toString().contains("WOOL") && e.getSlot() == 10) {
                    if (c.isSell()) {
                        c.setSell(false);
                    } else {
                        c.setSell(true);
                    }
                    PlayerShopGuis.instance.mainShopGui(c, p);
                    return;
                }
                if (current.getType() == Material.SUNFLOWER) {
                    if (PlayerShopGuis.instance.priceEditing.containsKey(p)) {
                        PlayerShopGuis.instance.priceEditing.remove(p);
                        PlayerShopGuis.instance.mainShopGui(c, p);
                    } else {
                        PlayerShopGuis.instance.priceEditing.put(p, c);
                        p.closeInventory();
                        p.sendMessage("§6§lPlayerShop §8» §fEntrez le prix de vente de l'item dans le tchat.");
                    }
                    return;
                }
                if (current.getType() == Material.BARRIER || current.getType() == (c.getItemToBuySell() == null ? Material.AIR : c.getItemToBuySell().getType())) {
                    if (PlayerShopGuis.instance.itemEditing.containsKey(p)) {
                        if (PlayerShopGuis.instance.itemEditing.get(p).equals(c)) {
                            PlayerShopGuis.instance.itemEditing.remove(p);
                            PlayerShopGuis.instance.mainShopGui(c, p);
                            p.sendMessage("§6§lPlayerShop §8» §fVous avez annulé l'édition de l'item.");
                        }
                    } else {
                        PlayerShopGuis.instance.itemEditing.put(p, c);
                        PlayerShopGuis.instance.mainShopGui(c, p);
                        p.sendMessage("§6§lPlayerShop §8» §fCliquez dans votre inventaire pour choisir un item à vendre.");
                    }
                }
                if (current.getType().toString().contains("CONCRETE") && e.getSlot() == 22) {
                    if (c.isActiveSellOrBuy()) {
                        c.setActiveSellOrBuy(false);
                        p.sendMessage("§6§lPlayerShop §8» §fVous avez désactivé le player shop.");
                    } else {
                        if (c.getItemToBuySell() != null) {
                            c.setActiveSellOrBuy(true);
                            p.sendMessage("§6§lPlayerShop §8» §fVous avez activé le player shop.");
                        } else {
                            p.sendMessage("§6§lPlayerShop §8» §cVous devez choisir un item à vendre ou acheter !");
                        }
                    }
                    PlayerShopGuis.instance.mainShopGui(c, p);
                    return;
                }
            } else {
                if (e.getSlot() != 10) {
                    if (c.getItemToBuySell() == null) {
                        p.sendMessage("§6§lPlayerShop §8» §cLe shop ne possède pas d'item défini.");
                        return;
                    }
                    if (!c.isActiveSellOrBuy()) {
                        p.sendMessage("§6§lPlayerShop §8» §cLe shop n'est pas activé.");
                        return;
                    }
                    int amount = current.getAmount();

                    if (current.getType().toString().contains("STAINED_GLASS_PANE")) {

                        BlockState bs = p.getWorld().getBlockAt(c.getBlock()).getState();

                        if (!(bs instanceof org.bukkit.block.Chest)) {
                            p.sendMessage("§6§lPlayerShop §8» §cErreur lors de la tentative de résolution du coffre de vente.");
                            return;
                        }

                        org.bukkit.block.Chest chest = (org.bukkit.block.Chest) bs;
                        Inventory inv = chest.getBlockInventory();
                        ItemStack[] items = inv.getContents();

                        if (!c.isSell()) {
                            if (!EcoAccountsManager.instance.checkForFounds(p, c.getPrice() * amount)) {
                                p.sendMessage("§6§lPlayerShop §8» §cVous n'avez pas assez d'argent.");
                                return;
                            }

                            if (InventoryUtils.instance.hasItemWithStackCo(c.getItemToBuySell(), inv) < amount) {
                                p.sendMessage("§6§lPlayerShop §8» §cLe coffre n'a pas assez d'item.");
                                return;
                            }
                            if (InventoryUtils.instance.hasPlaceWithStackCo(c.getItemToBuySell(), p.getInventory(), p) < amount) {
                                p.sendMessage("§6§lPlayerShop §8» §cVotre inventaire n'a pas assez de place.");
                                return;
                            }

                            int removed = 0;
                            ItemStack itemTo = c.getItemToBuySell().clone();
                            for (ItemStack item : items) {
                                if (item == null) {
                                    continue;
                                }

                                itemTo.setAmount(item.getAmount());
                                if (item.equals(itemTo)) {
                                    if (item.getAmount() >= amount) {
                                        item.setAmount(item.getAmount() - amount);
                                        removed += amount;
                                    } else {
                                        removed += item.getAmount();
                                        item.setAmount(0);
                                    }
                                    if (removed >= amount) {
                                        break;
                                    }
                                }
                            }
                            inv.setContents(items);
                            ItemStack togive = c.getItemToBuySell().clone();
                            togive.setAmount(amount);
                            p.getInventory().addItem(togive);
                            EcoAccountsManager.instance.removeFounds(p, amount * c.getPrice(), false);
                            EcoAccountsManager.instance.addFoundsUUID(c.getOwner(), amount * c.getPrice(), false);
                        } else {

                            if (!EcoAccountsManager.instance.checkForFoundsUUID(c.getOwner(), c.getPrice() * amount)) {
                                p.sendMessage("§6§lPlayerShop §8» §cLe propriétaire n'a pas assez d'argent.");
                                return;
                            }

                            if (InventoryUtils.instance.hasItemWithStackCo(c.getItemToBuySell(), p.getInventory()) < amount) {
                                p.sendMessage("§6§lPlayerShop §8» §cVotre inventaire n'a pas assez d'items'.");
                                return;
                            }
                            if (InventoryUtils.instance.hasPlaceWithStackCo(c.getItemToBuySell(), inv, p) < amount) {
                                p.sendMessage("§6§lPlayerShop §8» §cLe coffre n'a pas assez de place.");
                                return;
                            }

                            int added = 0;
                            ItemStack itemTo = c.getItemToBuySell().clone();
                            for (ItemStack item : items) {
                                if (item == null) {
                                    continue;
                                }

                                itemTo.setAmount(item.getAmount());
                                if (item.equals(itemTo)) {
                                    if (item.getAmount() + amount <= item.getMaxStackSize()) {
                                        item.setAmount(item.getAmount() + amount);
                                        added += amount;
                                    } else if (item.getAmount() + amount > item.getMaxStackSize()) {
                                        added += item.getMaxStackSize() - item.getAmount();
                                        item.setAmount(item.getMaxStackSize());
                                    }
                                    if (added >= amount) {
                                        break;
                                    }
                                }
                            }
                            if (added < amount) {
                                while (added < amount) {
                                    ItemStack tmp = c.getItemToBuySell().clone();
                                    if (amount - added < tmp.getMaxStackSize()) {
                                        tmp.setAmount(amount - added);
                                        added += amount - added;
                                    } else {
                                        tmp.setAmount(tmp.getMaxStackSize());
                                        added += tmp.getMaxStackSize();
                                    }
                                    for (int i = 0; i < Arrays.stream(items).count(); i++) {
                                        if (items[i] == null) {
                                            items[i] = tmp;
                                            break;
                                        }
                                    }
                                }
                            }
                            inv.setContents(items);
                            ItemStack togive = c.getItemToBuySell().clone();
                            togive.setAmount(amount);
                            p.getInventory().removeItem(togive);
                            EcoAccountsManager.instance.removeFoundsUUID(c.getOwner(), amount * c.getPrice(), false);
                            EcoAccountsManager.instance.addFounds(p, amount * c.getPrice(), false);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void breakEvent(BlockBreakEvent e) {
        if (e.getBlock() == null) {
            return;
        }
        if (e.getBlock().getType() == null) {
            return;
        }
        if (e.getBlock().getState() == null) {
            return;
        }
        int type;
        if (e.getBlock().getType() == Material.HOPPER) {
            Hopper blhopper = (Hopper) e.getBlock().getState();
            if (blhopper.getCustomName().contains("§6Chunk Hoppeur")) {
                type = 0;
            } else {
                return;
            }
        } else if (e.getBlock().getType() == Material.CHEST) {
            org.bukkit.block.Chest blchest = (org.bukkit.block.Chest) e.getBlock().getState();
            if (blchest.getCustomName().contains("§6SellChest")) {
                type = 1;
            } else if (blchest.getCustomName().contains("§6Player shop")) {
                type = 2;
            } else {
                return;
            }
        } else {
            return;
        }
        if (ChestManager.instance.getOwner(e.getBlock().getLocation()) == null) {
            return;
        }
        if (!ChestManager.instance.getOwner(e.getBlock().getLocation()).equals(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage("§6§lCoffres §8» §fVous devez être le propriétaire du coffre pour le détruire.");
            e.setCancelled(true);
            return;
        }
        ChestManager.instance.removeChestFromLoc(e.getBlock().getLocation());
        ChestManager.instance.giveChest(e.getPlayer(), type);
        e.setDropItems(false);
    }

    @EventHandler
    public void placeEvent(BlockPlaceEvent e) {
        if (e.getItemInHand() == null) {
            return;
        }
        if (e.getItemInHand().getType() == null) {
            return;
        }
        int type;
        if (e.getItemInHand().getDisplayName().contains("§6Chunk Hoppeur")) {
            type = 0;
        } else if (e.getItemInHand().getDisplayName().contains("§6SellChest")) {
            type = 1;
        } else if (e.getItemInHand().getDisplayName().contains("§6Player shop")) {
            type = 2;
        } else {
            return;
        }
        ChestManager.instance.placeChest(e.getPlayer(), e.getBlock().getLocation(), type, null, 0.0);
    }

}
