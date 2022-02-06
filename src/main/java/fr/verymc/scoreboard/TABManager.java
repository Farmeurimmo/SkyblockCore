package main.java.fr.verymc.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TABManager {

   public TABManager(){
      SendTab();
   }

   public static ArrayList<String> Vanished = new ArrayList<String>();

   public void SendTab(){
      Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
         public void run() {
            Vanished.clear();
            for(Player p : Bukkit.getOnlinePlayers()){
               if(!p.getMetadata("vanished").isEmpty()){
                  Vanished.add(p.getName());
               }
            }
            int online = Bukkit.getOnlinePlayers().size()-Vanished.size();
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
