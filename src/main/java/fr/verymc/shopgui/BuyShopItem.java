package main.java.fr.verymc.shopgui;

import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.utils.Maths;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BuyShopItem {

    public static HashMap<ItemStack, Double> pricesbuy = new HashMap<>();
    public static HashMap<ItemStack, Double> pricessell = new HashMap<>();

    public static void GenPriceShopStartup() {
        for (String bb : Main.instance1.getConfig().getConfigurationSection("Shops").getKeys(false)) {
            for (String aa : Main.instance1.getConfig().getConfigurationSection("Shops." + bb).getKeys(false)) {
                double a = (float) Main.instance1.getConfig().getDouble("Shops." + bb + "." + aa + ".buy");
                double c = (float) Main.instance1.getConfig().getDouble("Shops." + bb + "." + aa + ".sell");
                if (Material.valueOf(Main.instance1.getConfig().getString("Shops." + bb + "." + aa + ".material")) == null)
                    continue;
                ItemStack b = new ItemStack(Material.valueOf(Main.instance1.getConfig().getString("Shops." + bb + "." + aa + ".material")));
                if (Main.instance1.getConfig().getString("Shops." + bb + "." + aa + ".name") != null) {
                    b.setDisplayName(Main.instance1.getConfig().getString("Shops." + bb + "." + aa + ".name"));
                }
                pricesbuy.put(b, Maths.arrondiNDecimales(a, 2));
                pricessell.put(b, Maths.arrondiNDecimales(c, 2));
            }
        }
    }

    public static boolean isBuyable(ItemStack a) {
        if (pricesbuy.get(a) == null) {
            return false;
        }
        return pricesbuy.get(a) > 0;
    }

    public static boolean isSellable(ItemStack a) {
        if (pricessell.get(a) == null) {
            return false;
        }
        return pricessell.get(a) > 0;
    }

    public static void removeItems(Inventory inventory, Material type, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }

    public static int GetAmountInInv(ItemStack aa, Player ed) {
        int total = 0;

        int size = ed.getInventory().getSize() - 5;
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = ed.getInventory().getItem(slot);
            if (is == null) continue;
            if (aa.getType() == is.getType()) {
                total += is.getAmount();
            }
        }

        return total;
    }

    public static int GetAmountInInvNo(ItemStack aa, Inventory ed) {
        int total = 0;

        int size = ed.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = ed.getItem(slot);
            if (is == null) continue;
            if (aa.getType() == is.getType()) {
                total += is.getAmount();
            }
        }

        return total;
    }

    public static int GetAmountToFillInInv(ItemStack aa, Player player) {
        int total = 0;

        int size = player.getInventory().getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = player.getInventory().getItem(slot);
            if (is == null) {
                total += 64;
                continue;
            } else if (is.getType() == aa.getType()) {
                total += 64 - is.getAmount();
                continue;
            }
        }
        if (player.getInventory().getHelmet() == null) {
            total -= 64;
        }
        if (player.getInventory().getChestplate() == null) {
            total -= 64;
        }
        if (player.getInventory().getLeggings() == null) {
            total -= 64;
        }
        if (player.getInventory().getBoots() == null) {
            total -= 64;
        }
        if (player.getInventory().getItemInOffHand().getType() == Material.AIR) {
            total -= 64;
        }

        return total;
    }

    public static void BuyOSellItemNonStack(ItemStack a, Player player, boolean buy, double price, int amount) {
        if (buy == true) {
            if (EcoAccountsManager.instance.CheckForFounds(player, price * amount) == true) {
                if (a.getType() == Material.SPAWNER) {
                    String display = a.getDisplayName();
                    if (GenShopPage.spawneurtype.containsKey(display)) {
                        String togivetype = "";
                        if (GenShopPage.spawneurtype.get(display) == EntityType.BLAZE) {
                            togivetype = "Blaze";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.CHICKEN) {
                            togivetype = "Chicken";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.COW) {
                            togivetype = "Cow";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.CREEPER) {
                            togivetype = "Creeper";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.ENDERMAN) {
                            togivetype = "Enderman";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.IRON_GOLEM) {
                            togivetype = "ig";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.RABBIT) {
                            togivetype = "rabbit";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.SHEEP) {
                            togivetype = "Sheep";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.SKELETON) {
                            togivetype = "skeleton";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.SLIME) {
                            togivetype = "slime";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.SPIDER) {
                            togivetype = "spider";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.SQUID) {
                            togivetype = "squid";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.WITCH) {
                            togivetype = "witch";
                        } else if (GenShopPage.spawneurtype.get(display) == EntityType.ZOMBIE) {
                            togivetype = "zombie";
                        } else {
                            togivetype = "error";
                        }
                        if (togivetype.equalsIgnoreCase("") || togivetype.equalsIgnoreCase("error")) {
                            return;
                        }
                        a.setLore(null);
                        player.closeInventory();
                        a.setAmount(amount);
                        int amountininv = GetAmountToFillInInv(a, player);
                        if (amountininv <= amount) {
                            player.sendMessage("§6§lShop §8» §fIl vous manque de la place dans votre inventaire.");
                            return;
                        } else {
                            EcoAccountsManager.instance.RemoveFounds(player.getName(), price * amount, true);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " " + togivetype + " " + amount);
                            return;
                        }
                    }
                }
                a.setItemMeta(null);
                player.closeInventory();
                a.setAmount(amount);
                EcoAccountsManager.instance.RemoveFounds(player.getName(), price * amount, true);
                int amountininv = GetAmountToFillInInv(new ItemStack(a.getType()), player);
                ItemStack od = new ItemStack(a.getType());
                if (amountininv <= amount) {
                    int reste = amount - amountininv;
                    od.setAmount(amountininv);
                    player.getInventory().addItem(od);
                    od.setAmount(1);
                    while (reste > 0) {
                        player.getWorld().dropItem(player.getLocation(), od);
                        reste -= 1;
                    }
                } else {
                    player.getInventory().addItem(a);
                }
            } else {
                double loa = price * amount - EcoAccountsManager.instance.GetMoney(player.getName());
                player.sendMessage("§6§lShop §8» §fIl vous manque §6" + loa + "$§f.");
            }
        } else {
            if (player.getInventory().contains(a.getType(), amount)) {
                Double profit = pricessell.get(new ItemStack(Material.valueOf(a.getType().toString()))) * amount;
                player.closeInventory();
                removeItems(player.getInventory(), a.getType(), amount);
                player.sendMessage("§6§lShop §8» §fVous avez vendu §ax" + amount + " " + a.getType() + "§f pour §6" + profit + "$§f.");
                EcoAccountsManager.instance.AddFounds(player.getName(), profit, false);
            } else {
                player.sendMessage("§6§lShop §8» §fVous avez besoin de plus de " + a.getType() + ".");
            }
        }
    }
}
