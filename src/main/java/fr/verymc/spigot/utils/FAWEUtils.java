package main.java.fr.verymc.spigot.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.dungeon.Dungeon;
import main.java.fr.verymc.spigot.dungeon.DungeonManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class FAWEUtils {

    public static FAWEUtils instance;

    public ArrayList<Dungeon> awaiting = new ArrayList<>();

    public FAWEUtils() {
        instance = this;
    }

    public void pasteSchem(File file, Location pos) {
        try {
            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(pos.getWorld());
            ClipboardFormat format = ClipboardFormats.findByFile(file);
            ClipboardReader reader = format.getReader(new FileInputStream(file));

            Clipboard clipboard = reader.read();
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld,
                    -1);

            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                    .to(BlockVector3.at(pos.getX(), pos.getY(), pos.getZ())).ignoreAirBlocks(true).copyBiomes(true).build();


            Operations.complete(operation);
            editSession.flushSession();
            if (Main.instance.serverType != ServerType.SKYBLOCK_DUNGEON) return;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> checkForDungeonsAwaiting(pos), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pasteSchemWithoutLockingThread(File file, Location pos) {

        CompletableFuture.runAsync(() -> {
            pasteSchem(file, pos);
        });

    }

    public void pasteSchemLockThread(File file, Location pos) {

        CompletableFuture.runAsync(() -> {
            pasteSchem(file, pos);
        }).join();
    }

    public void checkForDungeonsAwaiting(Location loc) {
        for (Dungeon dungeon : awaiting) {
            if (dungeon.getLocDungeon().equals(loc)) {
                DungeonManager.instance.secondPartLoad(dungeon);
                awaiting.remove(dungeon);
                return;
            }
        }
    }

    /*IslandManager.instance.saveSchem("aaaa", island.getCenter().clone().add(50, -15, 50),
                        island.getCenter().clone().add(-50, 40, -50), island.getCenter().getWorld(), island.getCenter().clone());*/

    public void saveSchem(String filename, Location loc1, Location loc2, World world, Location center) {
        try {
            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
            BlockVector3 pos1 = BlockVector3.at(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
            BlockVector3 pos2 = BlockVector3.at(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
            Region cReg = new CuboidRegion(weWorld, pos2, pos1);
            File file = new File(Main.instance.getDataFolder(), filename + ".schem");
            Clipboard clipboard = Clipboard.create(cReg);
            clipboard.setOrigin(BlockVector3.at(center.getX(), center.getY(), center.getZ()));

            try (ClipboardWriter writer = BuiltInClipboardFormat.FAST.getWriter(new FileOutputStream(file))) {
                writer.write(clipboard);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
