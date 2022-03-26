package main.java.fr.verymc.island;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class IslandManager {

    public static IslandManager instance;
    public World mainWorld;
    public ArrayList<Player> isInIsland = new ArrayList<>();
    public ArrayList<Island> islands = new ArrayList<>();

    public IslandManager() {
        instance = this;
        createMainWorld();
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

    public void createMainWorld() {
        WorldCreator wc = new WorldCreator("Island_world");
        wc.generator(new EmptyChunkGenerator());
        mainWorld = wc.createWorld();
    }

    public boolean isInIsland(Player p) {
        return isInIsland.contains(p);
    }

    public void addPlayerInIsland(Player p) {
        isInIsland.add(p);
    }

    public void removePlayerInIsland(Player p) {
        isInIsland.remove(p);
    }

    public World getMainWorld() {
        return mainWorld;
    }

    public void genIsland(Player p) {
        Location toReturn = null;
        int id = 0;

        if (islands.size() == 0) {
            toReturn = new Location(mainWorld, 0, 0, 0);
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
                for (int i = minz; i <= maxz; i += 400) {
                    if (toReturn != null) break;
                    if (!isAnIslandByLoc(new Location(mainWorld, minx, 0, i))) {
                        toReturn = new Location(mainWorld, minx, 0, i);
                    }
                    for (int j = minx; j <= maxx; j += 400) {
                        if (!isAnIslandByLoc(new Location(mainWorld, j, 0, i))) {
                            toReturn = new Location(mainWorld, j, 0, i);
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
                    toSumx = 400;
                } else if (randint == 1) {
                    toSumx = -400;
                } else if (randint == 2) {
                    toSumz = 400;
                } else if (randint == 3) {
                    toSumz = -400;
                }
                Location tmp = new Location(mainWorld, maxx + toSumx, 0, minz + toSumz);
                if (!isAnIslandByLoc(tmp)) {
                    toReturn = tmp;
                    break;
                }
            }

        }
        islands.add(new Island("Ile de " + p.getName() + id, p.getName(), toReturn, 50, id + 1));
    }

}
