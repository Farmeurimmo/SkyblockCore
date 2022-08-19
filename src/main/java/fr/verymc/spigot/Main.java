package main.java.fr.verymc.spigot;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import main.java.fr.verymc.JedisManager;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.commons.utils.HTTPUtils;
import main.java.fr.verymc.spigot.core.InventorySyncManager;
import main.java.fr.verymc.spigot.core.PluginMessageManager;
import main.java.fr.verymc.spigot.core.ServersManager;
import main.java.fr.verymc.spigot.core.antiafk.AntiAfk;
import main.java.fr.verymc.spigot.core.atout.AtoutCmd;
import main.java.fr.verymc.spigot.core.atout.AtoutGui;
import main.java.fr.verymc.spigot.core.atout.BuyAtoutGui;
import main.java.fr.verymc.spigot.core.auctions.AhCmd;
import main.java.fr.verymc.spigot.core.auctions.AuctionGui;
import main.java.fr.verymc.spigot.core.auctions.AuctionsManager;
import main.java.fr.verymc.spigot.core.cmd.base.*;
import main.java.fr.verymc.spigot.core.cmd.moderation.*;
import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.core.evenement.ChatReaction;
import main.java.fr.verymc.spigot.core.evenement.EventManager;
import main.java.fr.verymc.spigot.core.events.JoinLeave;
import main.java.fr.verymc.spigot.core.events.ServerCoreMicellanous;
import main.java.fr.verymc.spigot.core.events.Tabulation;
import main.java.fr.verymc.spigot.core.events.TchatManager;
import main.java.fr.verymc.spigot.core.featherfly.CountdownFly;
import main.java.fr.verymc.spigot.core.featherfly.DailyFlyCmd;
import main.java.fr.verymc.spigot.core.featherfly.FeatherFlyCmd;
import main.java.fr.verymc.spigot.core.featherfly.FeatherFlyInteract;
import main.java.fr.verymc.spigot.core.gui.*;
import main.java.fr.verymc.spigot.core.holos.HoloManager;
import main.java.fr.verymc.spigot.core.holos.HolosSetup;
import main.java.fr.verymc.spigot.core.items.*;
import main.java.fr.verymc.spigot.core.leveladv.LevelAdvCmd;
import main.java.fr.verymc.spigot.core.leveladv.LevelAdvGuis;
import main.java.fr.verymc.spigot.core.leveladv.LevelAdvListener;
import main.java.fr.verymc.spigot.core.leveladv.LevelAdvManager;
import main.java.fr.verymc.spigot.core.mobstacker.MobStackerListener;
import main.java.fr.verymc.spigot.core.mobstacker.MobStackerManager;
import main.java.fr.verymc.spigot.core.scoreboard.ScoreBoard;
import main.java.fr.verymc.spigot.core.shopgui.*;
import main.java.fr.verymc.spigot.core.spawners.SpawnerCmd;
import main.java.fr.verymc.spigot.core.spawners.SpawnersListener;
import main.java.fr.verymc.spigot.core.spawners.SpawnersManager;
import main.java.fr.verymc.spigot.core.storage.ConfigManager;
import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import main.java.fr.verymc.spigot.core.storage.StorageJSONManager;
import main.java.fr.verymc.spigot.dungeon.DungeonManager;
import main.java.fr.verymc.spigot.dungeon.cmd.DungeonAdminCmd;
import main.java.fr.verymc.spigot.dungeon.events.DungeonEntityListener;
import main.java.fr.verymc.spigot.dungeon.events.DungeonProtectionsListener;
import main.java.fr.verymc.spigot.dungeon.mobs.DungeonMobManager;
import main.java.fr.verymc.spigot.hub.crates.CratesManager;
import main.java.fr.verymc.spigot.hub.crates.KeyCmd;
import main.java.fr.verymc.spigot.hub.events.AntiExplo;
import main.java.fr.verymc.spigot.hub.events.Interact;
import main.java.fr.verymc.spigot.hub.invest.InvestCmd;
import main.java.fr.verymc.spigot.hub.invest.InvestManager;
import main.java.fr.verymc.spigot.hub.winelottery.WineGui;
import main.java.fr.verymc.spigot.hub.winelottery.WineSpawn;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.blocks.ChestListener;
import main.java.fr.verymc.spigot.island.blocks.ChestManager;
import main.java.fr.verymc.spigot.island.blocks.ChestsCmd;
import main.java.fr.verymc.spigot.island.challenges.ChallengesCmd;
import main.java.fr.verymc.spigot.island.challenges.IslandChallengesGuis;
import main.java.fr.verymc.spigot.island.challenges.IslandChallengesListener;
import main.java.fr.verymc.spigot.island.events.IslandGeneratorForm;
import main.java.fr.verymc.spigot.island.events.IslandInteractManager;
import main.java.fr.verymc.spigot.island.events.IslandPlayerMove;
import main.java.fr.verymc.spigot.island.generator.EmptyChunkGenerator;
import main.java.fr.verymc.spigot.island.guis.IslandGuiManager;
import main.java.fr.verymc.spigot.island.minions.MinionManager;
import main.java.fr.verymc.spigot.island.minions.MinionsCmd;
import main.java.fr.verymc.spigot.island.minions.MinionsListener;
import main.java.fr.verymc.spigot.island.playerwarps.PlayerWarpCmd;
import main.java.fr.verymc.spigot.island.playerwarps.PlayerWarpGuiManager;
import main.java.fr.verymc.spigot.island.playerwarps.PlayerWarpManager;
import main.java.fr.verymc.spigot.island.protections.BlockListener;
import main.java.fr.verymc.spigot.island.protections.EntityListener;
import main.java.fr.verymc.spigot.utils.FAWEUtils;
import main.java.fr.verymc.spigot.utils.UtilsManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Main extends JavaPlugin {

    public static Main instance;
    //DEV MODE /!\ ATTENTION CE MODE DÉSACTIVE L'API /!\
    //Usage non recommandé pour les personnes ne le connaissant pas ce système ni les risques
    //qui peuvent en émerger
    public static boolean devMode = true;
    public static ServerType devServerType = ServerType.SKYBLOCK_ISLAND;
    public static LuckPerms api;
    private final HashMap<String, Integer> spawncooldown = new HashMap<>();
    public ArrayList<Player> pending = new ArrayList<>();
    public ArrayList<Player> pendingTrade = new ArrayList<>();
    public ArrayList<Player> haverequest = new ArrayList<>();
    public ArrayList<Player> haveTradeRequest = new ArrayList<>();
    public HashMap<String, String> tpatarget = new HashMap<>();
    public HashMap<String, String> tradeTarget = new HashMap<>();
    public ArrayList<TradeManager> tradeInProcess = new ArrayList<>();
    public ClaimCmdSaver saver;
    public ServerType serverType;
    public String serverName;
    public World mainWorld;
    public ProtocolManager manager;

    public void setTarget(String uuid, String aaa) {
        if (aaa == null)
            tpatarget.remove(uuid);
        else
            tpatarget.put(uuid, aaa);
    }

    public String getTarget(String player) {
        return tpatarget.getOrDefault(player, null);
    }

    public void setCooldown(String uuid, Integer time) {
        if (time == null)
            spawncooldown.remove(uuid);
        else
            spawncooldown.put(uuid, time);
    }

    public int getCooldown(String player) {
        return (spawncooldown.get(player) == null ? 0 : spawncooldown.get(player));
    }

    public void setTradeTarget(String uuid, String uuid_second_player) {
        if (uuid_second_player == null)
            tradeTarget.remove(uuid);
        else
            tradeTarget.put(uuid, uuid_second_player);
    }

    public String getTradeTarget(String player) {
        return tradeTarget.getOrDefault(player, null);
    }

    @Override
    public void onEnable() {
        long starting = System.currentTimeMillis();
        instance = this;
        System.out.println("------------------------------------------------");
        //server type ???
        if (!devMode) {
            ArrayList<String> response = HTTPUtils.readFromUrl("get/all/tocreate");
            if (response == null) {
                System.out.println("§4§lError while getting server type, server going to sleep...");
                Bukkit.shutdown();
                throw new RuntimeException();
            }
            for (String str : response) {
                for (ServerType serverType1 : ServerType.values()) {
                    if (str.contains(serverType1.toString())) {
                        if (serverType1 != ServerType.SKYBLOCK_HUB && serverType1 != ServerType.SKYBLOCK_ISLAND
                                && serverType1 != ServerType.SKYBLOCK_DUNGEON) continue;
                        serverType = serverType1;
                        serverName = serverType.getDisplayName() + str.replaceAll("[^\\d.]", "");
                        break;
                    }
                }
                if (serverType != null)
                    break;
            }
        } else {
            //serverType = ServerType.SKYBLOCK_ISLAND;
            //serverName = serverType.getDisplayName();
            serverType = devServerType;
            serverName = serverType.getDisplayName() + "1";
        }

        System.out.println("§aServer type: " + serverType + " | " + serverType.getDisplayName());
        System.out.println("§aServer name: " + serverName);

        if (!devMode) {
            try {
                HTTPUtils.postMethod("starting", serverName);
            } catch (IOException e) {
                Bukkit.shutdown();
                throw new RuntimeException(e);
            }
        }

        System.out.println("------------------------------------------------");
        //CORE INIT PART 1
        System.out.println("Starting core part 1...");
        saveDefaultConfig();
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
            System.out.println("API LUCKPERMS initialisée !");
        } else {
            getLogger().warning("Le plugin LuckPerms est manquant.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            System.out.println("API CITIZENS initialisée !");
        } else {
            getLogger().warning("Le plugin Citizens est manquant.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            System.out.println("API HOLOGRAPHICDISPLAYS initialisée !");
        } else {
            getLogger().warning("Le plugin HolographicDisplays est manquant.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        saveResource("spawn.schem", true);
        saveResource("FLOOR_1.schem", true);
        saveResource("clear.schem", true);
        saveResource("ileworld.schem", true);

        new UtilsManager();

        manager = ProtocolLibrary.getProtocolManager();
        if (manager == null) {
            System.out.println("§4§lError while getting ProtocolManager, server going to sleep...");
            Bukkit.shutdown();
            return;
        }


        new HoloManager();


        new IslandManager();
        createMainWorld();

        new SkyblockUserManager();
        new LevelAdvManager();
        new EcoAccountsManager();
        new JedisManager();
        new InventorySyncManager();

        BuyShopItem.GenPriceShopStartup();
        GenShopPage.GetNumberOfPage();
        GenShopPage.GenenerateShopPageStartup("Blocs");
        GenShopPage.GenenerateShopPageStartup("Agriculture");
        GenShopPage.GenenerateShopPageStartup("Nourritures");
        GenShopPage.GenenerateShopPageStartup("Colorants");
        GenShopPage.GenenerateShopPageStartup("Minerais");
        GenShopPage.GenenerateShopPageStartup("Autres");
        GenShopPage.GenenerateShopPageStartup("Drops");
        GenShopPage.GenenerateShopPageStartup("Redstone");
        GenShopPage.GenenerateShopPageStartup("Spawneurs");
        GenAmoutShopGui.GenAmoutShopGuiStartup();
        GenMultiStacksBuyGui.GenMultiShopGuiStartup();

        startCommandModule();

        new EventManager();

        new ChestManager();
        new MinionManager();
        new PlayerWarpManager();
        new SpawnersManager();
        new MobStackerManager();

        startListenerModule();

        new ServersManager();

        new ConfigManager();
        new StorageJSONManager();

        System.out.println("Starting core part 1 FINISHED");
        System.out.println("------------------------------------------------");


        //ISLAND ADDITIONNAL STARTUP
        if (serverType == ServerType.SKYBLOCK_ISLAND) {
            System.out.println("Starting Island ADDITIONNAL module...");
            IslandManager.instance.load();
            for (Island island : IslandManager.instance.islands) {
                island.toggleTimeAndWeather();
            }

            System.out.println("Starting Island ADDITIONNAL module FINISHED");
        }

        //HUB ADDITIONNAL STARTUP
        if (serverType == ServerType.SKYBLOCK_HUB) {
            System.out.println("Starting Hub ADDITIONNAL module...");
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    WineSpawn.SpawnPnj(new Location(Main.instance.mainWorld, -184.5, 70.5, -77.5, -90, 0));
                    HolosSetup.SpawnPnj2(new Location(Main.instance.mainWorld, -155.5, 71, -60.5, 90, 0),
                            new Location(Main.instance.mainWorld, -172.5, 71, -64.5, 90, 0));
                    HolosSetup.SpawnCrates();
                    CratesManager.SpawnCrates();
                }
            }, 20 * 30L);
            new InvestManager();
            System.out.println("Starting Hub ADDITIONNAL module FINISHED");
        }


        //Dungeon ADDITIONNAL STARTUP
        if (serverType == ServerType.SKYBLOCK_DUNGEON) {
            System.out.println("Starting Dungeon ADDITIONNAL module...");

            new DungeonManager();

            System.out.println("Starting Dungeon ADDITIONNAL module FINISHED");
        }


        //CORE PART 2
        System.out.println("Starting core part 2...");
        saver = new ClaimCmdSaver();
        new AuctionsManager();
        ScoreBoard.acces.updateScoreBoard();
        ChatReaction.StartChatReaction();
        for (Player player : Bukkit.getOnlinePlayers()) {
            SkyblockUserManager.instance.checkForAccount(player);
            ScoreBoard.acces.setScoreBoard(player);
            IslandManager.instance.setWorldBorder(player);
        }
        System.out.println("Starting core part 2 FINISHED");

        if (!devMode) {
            try {
                HTTPUtils.postMethod("created", serverName);
            } catch (IOException e) {
                Bukkit.shutdown();
                throw new RuntimeException(e);
            }
        }

        System.out.println("§aDémarrage du plugin TERMINE!");
        System.out.println("------------------------------------------------");

        getServer().getMessenger().registerOutgoingPluginChannel(this, "skyblock:toproxy");
        getServer().getMessenger().registerIncomingPluginChannel(this, "skyblock:tospigot", new PluginMessageManager());

        System.out.println("§6Démarré en " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - starting) + "s");
    }

    @Override
    public void onDisable() {
        if (serverType == ServerType.SKYBLOCK_ISLAND) {
            ServersManager.instance.removeServerPlayersFromAPI();
            IslandManager.instance.saveAllIslands();
        }
        if (serverType == ServerType.SKYBLOCK_DUNGEON) {
            DungeonManager.instance.makeAllDungeonsEnd();
            DungeonMobManager.instance.removeAllMobs();
        }
        for (SkyblockUser user : SkyblockUserManager.instance.users) {
            if (user.isInInvestMode()) {
                InvestManager.instance.giveReward(user);
            }
        }
        CompletableFuture.runAsync(() -> {
            StorageJSONManager.instance.sendDataToAPIAuto(true);
        }).join();
        for (Hologram hologram : HologramsAPI.getHolograms(this)) {
            hologram.delete();
        }
        for (NPC npc : CitizensAPI.getNPCRegistry().sorted()) {
            npc.destroy();
        }
        saver.saveCooldown();

        if (!devMode) {
            try {
                HTTPUtils.postMethod("remove", serverName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Bukkit.unloadWorld(mainWorld.getName(), false);
        deleteWorld(mainWorld.getWorldFolder());

        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("Plugin stoppé !");
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    public void startListenerModule() {
        //CORE LISTENER
        getServer().getPluginManager().registerEvents(new JoinLeave(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
        getServer().getPluginManager().registerEvents(new Tabulation(), this);
        getServer().getPluginManager().registerEvents(new AfkMineCaptchaGui(), this);
        getServer().getPluginManager().registerEvents(new ChatReaction(), this);
        getServer().getPluginManager().registerEvents(new AtoutGui(), this);
        getServer().getPluginManager().registerEvents(new BuyAtoutGui(), this);
        getServer().getPluginManager().registerEvents(new MenuGui(), this);
        getServer().getPluginManager().registerEvents(new MainShopGui(), this);
        getServer().getPluginManager().registerEvents(new CateSelectGui(), this);
        getServer().getPluginManager().registerEvents(new AmountGuiManager(), this);
        getServer().getPluginManager().registerEvents(new MultiStacksShopGuiManager(), this);
        getServer().getPluginManager().registerEvents(new TchatManager(), this);
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
        getServer().getPluginManager().registerEvents(new FarmHoeManager(), this);
        getServer().getPluginManager().registerEvents(new FarmHoeGui(), this);
        getServer().getPluginManager().registerEvents(new AuctionGui(), this);
        getServer().getPluginManager().registerEvents(new AntiAfk(), this);
        getServer().getPluginManager().registerEvents(new MinionsListener(), this);
        getServer().getPluginManager().registerEvents(new PickaxeManager(), this);
        getServer().getPluginManager().registerEvents(new PickaxeGui(), this);
        getServer().getPluginManager().registerEvents(new TradeGui(), this);
        getServer().getPluginManager().registerEvents(new MoneyTradeGui(), this);
        getServer().getPluginManager().registerEvents(new PlayerWarpGuiManager(), this);
        getServer().getPluginManager().registerEvents(new ServerCoreMicellanous(), this);
        getServer().getPluginManager().registerEvents(new IslandChallengesGuis(), this);
        getServer().getPluginManager().registerEvents(new LevelAdvGuis(), this);
        getServer().getPluginManager().registerEvents(new LevelAdvListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnersListener(), this);
        if (serverType != ServerType.SKYBLOCK_DUNGEON)
            getServer().getPluginManager().registerEvents(new MobStackerListener(), this);


        //ISLAND LISTENER
        if (serverType == ServerType.SKYBLOCK_ISLAND) {
            getServer().getPluginManager().registerEvents(new Farm2WinGui(), this);
            getServer().getPluginManager().registerEvents(new WineGui(), this);
            getServer().getPluginManager().registerEvents(new WarpGui(), this);
            getServer().getPluginManager().registerEvents(new IslandChallengesListener(), this);
            getServer().getPluginManager().registerEvents(new IslandGuiManager(), this);
            getServer().getPluginManager().registerEvents(new IslandInteractManager(), this);
            getServer().getPluginManager().registerEvents(new IslandPlayerMove(), this);
            getServer().getPluginManager().registerEvents(new IslandGeneratorForm(), this);
            getServer().getPluginManager().registerEvents(new EntityListener(), this);
            getServer().getPluginManager().registerEvents(new BlockListener(), this);
            getServer().getPluginManager().registerEvents(new CountdownFly(), this);
            getServer().getPluginManager().registerEvents(new FeatherFlyInteract(), this);
        }


        //HUB LISTENER
        if (serverType == ServerType.SKYBLOCK_HUB) {
            getServer().getPluginManager().registerEvents(new Interact(), this);
            getServer().getPluginManager().registerEvents(new AntiExplo(), this);
            getServer().getPluginManager().registerEvents(new CratesManager(), this);
            getServer().getPluginManager().registerEvents(new HolosSetup(), this);
        }


        //DUNGEON LISTENER
        if (serverType == ServerType.SKYBLOCK_DUNGEON) {
            getServer().getPluginManager().registerEvents(new DungeonEntityListener(), this);
            getServer().getPluginManager().registerEvents(new DungeonProtectionsListener(), this);
        }



        /*PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);*/
    }

    public void startCommandModule() {
        this.getCommand("spawn").setExecutor(new SpawnCmd());
        this.getCommand("build").setExecutor(new BuildCmd());
        this.getCommand("farm2win").setExecutor(new Farm2WinCmd());
        this.getCommand("bar").setExecutor(new BarCmd());
        this.getCommand("menu").setExecutor(new MenuCmd());
        this.getCommand("warp").setExecutor(new WarpCmd());
        this.getCommand("warps").setExecutor(new WarpsCmd());
        this.getCommand("featherfly").setExecutor(new FeatherFlyCmd());
        this.getCommand("dailyfly").setExecutor(new DailyFlyCmd());
        this.getCommand("itemleg").setExecutor(new ItemLegCmd());
        this.getCommand("fly").setExecutor(new FlyCmd());
        this.getCommand("feed").setExecutor(new FeedCmd());
        this.getCommand("hat").setExecutor(new HatCmd());
        this.getCommand("craft").setExecutor(new CraftCmd());
        this.getCommand("enchantement").setExecutor(new EnchantementCmd());
        this.getCommand("gm").setExecutor(new GmCmd());
        this.getCommand("gms").setExecutor(new GmLCmd());
        this.getCommand("gmc").setExecutor(new GmLCmd());
        this.getCommand("gmsp").setExecutor(new GmLCmd());
        this.getCommand("gma").setExecutor(new GmLCmd());
        this.getCommand("afkmine").setExecutor(new AntiAfkMineCmd());
        this.getCommand("checkfly").setExecutor(new CheckFlyCmd());
        this.getCommand("tpa").setExecutor(new TpaCmd());
        this.getCommand("tpacancel").setExecutor(new TpaCancelCmd());
        this.getCommand("tpyes").setExecutor(new TpYesCmd());
        this.getCommand("tpno").setExecutor(new TpNoCmd());
        this.getCommand("challenges").setExecutor(new ChallengesCmd());
        this.getCommand("atout").setExecutor(new AtoutCmd());
        this.getCommand("datafile").setExecutor(new DataCmd());
        this.getCommand("key").setExecutor(new KeyCmd());
        this.getCommand("money").setExecutor(new MoneyCmd());
        this.getCommand("pay").setExecutor(new PayCmd());
        this.getCommand("baltop").setExecutor(new BaltopCmd());
        this.getCommand("shop").setExecutor(new ShopGuiCmd());
        this.getCommand("sellall").setExecutor(new SellAllCmd());
        this.getCommand("chests").setExecutor(new ChestsCmd());
        this.getCommand("farmhoe").setExecutor(new FarmHoeCmd());
        this.getCommand("reboot").setExecutor(new RebootCmd());
        this.getCommand("ah").setExecutor(new AhCmd());
        this.getCommand("events").setExecutor(new EventsCmd());
        this.getCommand("admin").setExecutor(new AdminCmd());
        this.getCommand("minions").setExecutor(new MinionsCmd());
        this.getCommand("is").setExecutor(new IslandCmd());
        this.getCommand("invest").setExecutor(new InvestCmd());
        this.getCommand("playerwarp").setExecutor(new PlayerWarpCmd());
        this.getCommand("claim").setExecutor(new ClaimCmd(saver));
        this.getCommand("trade").setExecutor(new TradeCmd());
        this.getCommand("tradeyes").setExecutor(new TradeYesCmd());
        this.getCommand("tradeno").setExecutor(new TradeNoCmd());
        this.getCommand("tradecancel").setExecutor(new TradeCancelCmd());
        this.getCommand("pickaxe").setExecutor(new PickaxeCmd());
        this.getCommand("dungeonadmin").setExecutor(new DungeonAdminCmd());
        this.getCommand("level").setExecutor(new LevelAdvCmd());
        this.getCommand("spawner").setExecutor(new SpawnerCmd());
    }

    public void createMainWorld() {
        World oldWorld = Bukkit.getWorld(serverType.getDisplayName());
        if (oldWorld != null) {
            Bukkit.unloadWorld(oldWorld, false);
            deleteWorld(oldWorld.getWorldFolder());
        }
        WorldCreator wc = new WorldCreator(serverType.getDisplayName());
        wc.generator(new EmptyChunkGenerator());
        mainWorld = wc.createWorld();
        if (serverType == ServerType.SKYBLOCK_HUB) {
            for (File file : Main.instance.getDataFolder().listFiles()) {
                if (file.getName().contains("spawn")) {
                    FAWEUtils.instance.pasteSchemWithoutLockingThread(file, SpawnCmd.Spawn.clone().add(0, -1, 0));
                }
            }
        }
    }

    public void deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        path.delete();
    }

}