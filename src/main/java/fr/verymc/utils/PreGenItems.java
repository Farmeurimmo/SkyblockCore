package main.java.fr.verymc.utils;

import main.java.fr.verymc.eco.EcoAccountsManager;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class PreGenItems {

   public static ItemStack getOwnerHead(Player player){
      User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
      String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");

      ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
      SkullMeta customi = (SkullMeta) custom10.getItemMeta();
      customi.setOwner(player.getName());
      customi.setDisplayName("§7" + player.getName());
      customi.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + EcoAccountsManager.instance.MoneyGetarrondiNDecimales(player.getName(), 2)));
      custom10.setItemMeta(customi);
      return custom10;
   }

}
