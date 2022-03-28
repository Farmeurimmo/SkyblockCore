package main.java.fr.verymc.island;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.generator.EmptyChunkGenerator;
import main.java.fr.verymc.island.guis.IslandMainGui;
import main.java.fr.verymc.island.guis.IslandMemberGui;
import main.java.fr.verymc.island.perms.IslandRank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class IslandManager {

    public static IslandManager instance;
    public static int distanceBetweenIslands = 400;
    public World mainWorld;
    public ArrayList<Player> isInIsland = new ArrayList<>();
    public ArrayList<Island> islands = new ArrayList<>();
    public File fileSchematic;

    public IslandManager() {
        instance = this;
        createMainWorld();
        for (File file : Main.instance.getDataFolder().listFiles()) {
            if (file.getName().endsWith(".schem")) {
                fileSchematic = file;
            }
        }
        new IslandMainGui();
        new IslandMemberGui();
    }

    public boolean isAnIslandByLoc(Location loc) {
        for (Island i : islands) {
            if (i.getHome().getBlockX() + i.getSizeLevel() >= loc.getBlockX() && i.getHome().getBlockX() - i.getSizeLevel() <= loc.getBlockX()
                    && i.getHome().getBlockZ() + i.getSizeLevel() >= loc.getBlockZ() && i.getHome().getBlockZ() - i.getSizeLevel() <= loc.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public void teleportPlayerToIslandSafe(Player p) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(p.getUniqueId())) {
                p.teleport(i.getHome());
                p.sendMessage("§6§lIles §8» §fTéléportation sur votre île.");
                return;
            }
        }
    }

    public boolean isOwner(Player p) {
        for (Island i : islands) {
            if (i.getOwnerUUID().equals(p.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public Island getPlayerIsland(Player p) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(p.getUniqueId())) {
                return i;
            }
        }
        return null;
    }

    public void createMainWorld() {
        WorldCreator wc = new WorldCreator("Island_world");
        wc.generator(new EmptyChunkGenerator());
        mainWorld = wc.createWorld();
    }

    public boolean asAnIsland(Player p) {
        return isInIsland.contains(p);
    }

    public void addPlayerAsAnIsland(Player p) {
        isInIsland.add(p);
    }

    public void removePlayerAsAnIsland(Player p) {
        isInIsland.remove(p);
    }

    public World getMainWorld() {
        return mainWorld;
    }

    public void genIsland(Player p) {
        Location toReturn = null;
        int id = 0;
        p.sendMessage("§6§lIles §8» §aGénération de l'île en cours...");
        Long start = System.currentTimeMillis();

        if (islands.size() == 0) {
            toReturn = new Location(mainWorld, 0, 80, 0);
        } else {
            int minx = 0;
            int minz = 0;
            int maxx = 0;
            int maxz = 0;
            for (Island i : islands) {
                if (minx > i.getHome().getBlockX()) {
                    minx = i.getHome().getBlockX();
                }
                if (minz > i.getHome().getBlockZ()) {
                    minz = i.getHome().getBlockZ();
                }
                if (maxx < i.getHome().getBlockX()) {
                    maxx = i.getHome().getBlockX();
                }
                if (maxz < i.getHome().getBlockZ()) {
                    maxz = i.getHome().getBlockZ();
                }
                if (i.getId() >= id) {
                    id = i.getId();
                }
            }
            Random rand = new Random();
            while (toReturn == null) {
                for (int i = minz; i <= maxz; i += distanceBetweenIslands) {
                    if (toReturn != null) break;
                    if (!isAnIslandByLoc(new Location(mainWorld, minx, 0, i))) {
                        toReturn = new Location(mainWorld, minx, 80, i);
                    }
                    for (int j = minx; j <= maxx; j += distanceBetweenIslands) {
                        if (!isAnIslandByLoc(new Location(mainWorld, j, 0, i))) {
                            toReturn = new Location(mainWorld, j, 80, i);
                        }
                    }
                }
                if (toReturn != null) {
                    break;
                }
                int toSumx = 0;
                int toSumz = 0;
                int randint = rand.nextInt(4);
                if (randint == 0) {
                    toSumx = distanceBetweenIslands;
                } else if (randint == 1) {
                    toSumx = -distanceBetweenIslands;
                } else if (randint == 2) {
                    toSumz = distanceBetweenIslands;
                } else if (randint == 3) {
                    toSumz = -distanceBetweenIslands;
                }
                Location tmp = new Location(mainWorld, maxx + toSumx, 80, minz + toSumz);
                if (!isAnIslandByLoc(tmp)) {
                    toReturn = tmp;
                    break;
                }
            }

        }

        Location finalToReturn = toReturn;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                try {
                    com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(mainWorld);
                    ClipboardFormat format = ClipboardFormats.findByFile(fileSchematic);
                    ClipboardReader reader = format.getReader(new FileInputStream(fileSchematic));

                    Clipboard clipboard = reader.read();
                    EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld,
                            -1);

                    Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                            .to(BlockVector3.at(finalToReturn.getBlockX(), finalToReturn.getBlockY(), finalToReturn.getBlockZ())).ignoreAirBlocks(true).build();

                    try {
                        Operations.complete(operation);
                        editSession.flushSession();

                    } catch (WorldEditException e) {
                        p.sendMessage("§6§lIles §8» §cUne erreur est survenue lors de la génération de l'île. Merci de réessayer.");
                        return;
                    }
                } catch (IOException e) {
                    p.sendMessage("§6§lIles §8» §cUne erreur est survenue lors de la lecture du schématic. Merci de contacter un administrateur.");
                    return;
                }
            }
        }, 0);

        HashMap<UUID, IslandRank> members = new HashMap<>();
        members.put(p.getUniqueId(), IslandRank.CHEF);
        islands.add(new Island("Ile de " + p.getName(), p.getName(), p.getUniqueId(), toReturn, 50, id + 1, members));
        addPlayerAsAnIsland(p);
        p.sendMessage("§6§lIles §8» §aVous avez généré une nouvelle île avec succès (en " + (System.currentTimeMillis() - start) + "ms).");
        p.teleport(toReturn.add(0.5, 0.1, 0.5));

    }

}
