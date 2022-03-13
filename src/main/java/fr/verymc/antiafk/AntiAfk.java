package main.java.fr.verymc.antiafk;

import main.java.fr.verymc.gui.AfkMineCaptchaGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class AntiAfk implements Listener {

    public Map<Player, Vector> playerLastDirection = new HashMap<>();
    public Map<Player, Integer> playerCountTimesAfkAsAResult = new HashMap<>();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();
        playerLastDirection.put(p, p.getLocation().getDirection());
        playerCountTimesAfkAsAResult.put(p, 0);

    }

    @EventHandler
    public void onPlayerInterractEvent(PlayerInteractEvent e){

        Player p = e.getPlayer();

        Vector pDirection = p.getLocation().getDirection();


        if( playerLastDirection.get(p).equals(pDirection)){


            playerCountTimesAfkAsAResult.put(p, playerCountTimesAfkAsAResult.get(p) + 1);

            if(playerCountTimesAfkAsAResult.get(p) == 50){

                p.sendTitle("§CAfk-Mine détecté !", "§fMerci de déplacer votre curseur.");
                p.sendMessage("§cAnti-Afk §7§l>> §fAttention, nous avons détecté que tu étais peut être afk, si tu ne l'es pas merci de bouger ton curseur de temps en temps.");

            }else if(playerCountTimesAfkAsAResult.get(p) == 60){

                p.sendMessage("§cAnti-Afk §7>> §fAttention, dernier avertissement, merci de bouger ton curseur.");
                p.sendTitle("§CAfk-Mine détecté !", "§fMerci de déplacer votre curseur.");

            }else if(playerCountTimesAfkAsAResult.get(p) == 70){

                AfkMineCaptchaGui.MakeAfkMineCaptchaGui(p);

            }

        }else{
            playerCountTimesAfkAsAResult.put(p, 0);

        }
        playerLastDirection.put(p, pDirection);





    }






}

