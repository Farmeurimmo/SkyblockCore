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
    public void onPlayerInterractEvent(PlayerInteractEvent e){

        Player p = e.getPlayer();

        Vector pDirection = p.getLocation().getDirection();

        if(!playerLastDirection.containsKey(p)){
            playerCountTimesAfkAsAResult.put(p, 0);
            playerLastDirection.put(p, pDirection);
        }

        if( playerLastDirection.get(p).equals(pDirection)){


            playerCountTimesAfkAsAResult.put(p, playerCountTimesAfkAsAResult.get(p) + 1);

            int result = playerCountTimesAfkAsAResult.get(p);

            if(result == 160){

                p.sendTitle("§CAfk-Farm détecté !", "§fMerci de déplacer votre curseur.");
                p.sendMessage("§6§lAntiAFK §8» §fAttention, nous avons détecté que tu étais peut être en afk farm, si tu ne l'es pas merci de bouger ton curseur de temps en temps.");

            }else if(result == 200){

                p.sendMessage("§6§lAntiAFK §8» §fAttention, dernier avertissement, merci de bouger ton curseur.");
                p.sendTitle("§CAfk-Farm détecté !", "§fMerci de déplacer votre curseur.");

            }else if(result == 230){

                AfkMineCaptchaGui.MakeAfkMineCaptchaGui(p);
                playerCountTimesAfkAsAResult.put(p, 0);
            }

        }else{
            playerCountTimesAfkAsAResult.put(p, 0);

        }
        playerLastDirection.put(p, pDirection);





    }






}

