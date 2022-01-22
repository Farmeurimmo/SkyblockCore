package main.java.fr.verymc.scoreboard;

import fr.verymc.Commands.CommandMod;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TABManager {

   public TABManager(){
      SendTab();
   }

   public void SendTab(){
      Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
         public void run() {
            int online = Bukkit.getOnlinePlayers().size()-CommandMod.Vanish.size();
            for(Player player : Bukkit.getOnlinePlayers()){
               player.setPlayerListHeaderFooter("\n§f• §6§lVery§f§lMc §f•\n\n§7Connecté(s) §7▸ §a"+online+
                 "\n§7Une question ? contacte un §9§lSTAFF §7!\n§7Tu veux le discord §d/discord\n",
                 "\n§7Serveur Mini-Jeux Francophone\n§7Vous êtes sur ▸ §fplay.§6§lvery§f§lmc§f.fr §7◂");
            }
            SendTab();
         }
      }, 20);
   }
}
