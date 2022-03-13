package main.java.fr.verymc.core;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import main.java.fr.verymc.WineLottery.WineGui;
import main.java.fr.verymc.WineLottery.WineSpawn;
import main.java.fr.verymc.antiafk.AntiAfk;
import main.java.fr.verymc.atout.AtoutCmd;
import main.java.fr.verymc.atout.AtoutGui;
import main.java.fr.verymc.atout.BuyAtoutGui;
import main.java.fr.verymc.auctions.AhCmd;
import main.java.fr.verymc.auctions.AuctionGui;
import main.java.fr.verymc.auctions.AuctionsManager;
import main.java.fr.verymc.blocks.*;
import main.java.fr.verymc.challenges.ChallengesBlockBreak;
import main.java.fr.verymc.challenges.ChallengesCmd;
import main.java.fr.verymc.challenges.ChallengesGuis;
import main.java.fr.verymc.challenges.ChallengesReset;
import main.java.fr.verymc.cmd.base.*;
import main.java.fr.verymc.cmd.moderation.*;
import main.java.fr.verymc.crates.CratesManager;
import main.java.fr.verymc.crates.KeyCmd;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.eco.EconomyImplementer;
import main.java.fr.verymc.eco.VaultHook;
import main.java.fr.verymc.evenement.ChatReaction;
import main.java.fr.verymc.events.*;
import main.java.fr.verymc.featherfly.CountdownFly;
import main.java.fr.verymc.featherfly.DailyFlyCmd;
import main.java.fr.verymc.featherfly.FeatherFlyCmd;
import main.java.fr.verymc.featherfly.FeatherFlyInteract;
import main.java.fr.verymc.gui.AfkMineCaptchaGui;
import main.java.fr.verymc.gui.Farm2WinGui;
import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.gui.WarpGui;
import main.java.fr.verymc.holos.HolosSetup;
import main.java.fr.verymc.items.FarmHoeCmd;
import main.java.fr.verymc.items.FarmHoeGui;
import main.java.fr.verymc.items.FarmHoeManager;
import main.java.fr.verymc.items.ItemLegCmd;
import main.java.fr.verymc.scoreboard.ScoreBoard;
import main.java.fr.verymc.scoreboard.TABManager;
import main.java.fr.verymc.shopgui.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {

    public static Main instance;
    static LuckPerms api;
    private final HashMap<String, Integer> spawncooldown = new HashMap<>();
    public ArrayList<Player> pending = new ArrayList<Player>();
    public ArrayList<Player> haverequest = new ArrayList<Player>();
    public HashMap<String, String> tpatarget = new HashMap<>();
    public EconomyImplementer economyImplementer;
    public FileConfiguration data;
    public FileConfiguration datac;
    public FileConfiguration dataz;
    public FileConfiguration datablc;
    public FileConfiguration dataah;
    public File dfile;
    public File cfile;
    public File zfile;
    public File blcfile;
    public File ahfile;
    private VaultHook vaultHook;

    public void setTarget(String uuid, String aaa) {
        if (aaa == null)
            tpatarget.remove(uuid);
        else
            tpatarget.put(uuid, aaa);
    }

    public String getTarget(String player) {
        if (tpatarget.containsKey(player))
            return tpatarget.get(player);
        else
            return null;
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

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
        Bukkit.getPluginManager().isPluginEnabled("Citizens");
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            System.out.println("Le plugin LuckPerms a été trouvé !");
        } else {
            getLogger().warning("Le plugin LuckPerms est manquant.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");
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
        if (IridiumSkyblockAPI.getInstance() == null) {
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().warning("Le plugin IridiumSkyblock est manquant.");
        }
        System.out.println("API IRIDIUMSKYBLOCK initialisée !");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        System.out.println("Initialisation des MODULES en cours...");
        setup();
        System.out.println("Fichier yml DONE | NEXT Methods init");

        BuildCmd.Build.clear();
        spawncooldown.clear();
        ChatReaction.WriteWords();
        //BossBar.CreateBossBar();
        ChunkCollectorManager.ReadFromFile();
        SellChestManager.ReadFromFile();
        FarmHoeManager.addtolist();
        economyImplementer = new EconomyImplementer();
        vaultHook = new VaultHook();
        vaultHook.hook();
        new EcoAccountsManager();
        System.out.println("Starting one time methods DONE | NEXT Pregen shopgui ");

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
        new TABManager();
        System.out.println("PregenShopGui & price DONE | NEXT permanant loops");

        ChatReaction.StartChatReaction();
        ChallengesReset.CheckForReset();
        EcoAccountsManager.instance.updateHash();
        SellChestManager.AutoSellForVeryChest();
        new ScoreBoard();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreBoard.acces.setScoreBoard(player);
        }
        System.out.println("Starting permanant loops DONE | NEXT blocs/pnj/holos");

        WineSpawn.SpawnPnj(new Location(Bukkit.getServer().getWorld("world"), -184.5, 70.5, -77.5, -90, 0));
        HolosSetup.SpawnPnj2(new Location(Bukkit.getServer().getWorld("world"), -155.5, 71, -60.5, 90, 0),
                new Location(Bukkit.getServer().getWorld("world"), -172.5, 71, -64.5, 90, 0));
        HolosSetup.SpawnCrates();
        CratesManager.SpawnCrates();
        System.out.println("Spawn Blocs/PNJ/HOLOS DONE | NEXT listeners");

        getServer().getPluginManager().registerEvents(new JoinLeave(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new AntiExplo(), this);
        getServer().getPluginManager().registerEvents(new Farm2WinGui(), this);
        getServer().getPluginManager().registerEvents(new WineGui(), this);
        getServer().getPluginManager().registerEvents(new WarpGui(), this);
        getServer().getPluginManager().registerEvents(new CountdownFly(), this);
        getServer().getPluginManager().registerEvents(new FeatherFlyInteract(), this);
        getServer().getPluginManager().registerEvents(new SwitchWorld(), this);
        getServer().getPluginManager().registerEvents(new Tabulation(), this);
        getServer().getPluginManager().registerEvents(new AfkMineCaptchaGui(), this);
        getServer().getPluginManager().registerEvents(new ChatReaction(), this);
        getServer().getPluginManager().registerEvents(new RedstoneCheck(), this);
        getServer().getPluginManager().registerEvents(new ChallengesGuis(), this);
        getServer().getPluginManager().registerEvents(new ChallengesBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new AtoutGui(), this);
        getServer().getPluginManager().registerEvents(new BuyAtoutGui(), this);
        getServer().getPluginManager().registerEvents(new CratesManager(), this);
        getServer().getPluginManager().registerEvents(new HolosSetup(), this);
        getServer().getPluginManager().registerEvents(new MenuGui(), this);
        getServer().getPluginManager().registerEvents(new MainShopGui(), this);
        getServer().getPluginManager().registerEvents(new CateSelectGui(), this);
        getServer().getPluginManager().registerEvents(new AmountGuiManager(), this);
        getServer().getPluginManager().registerEvents(new MultiStacksShopGuiManager(), this);
        getServer().getPluginManager().registerEvents(new TchatManager(), this);
        getServer().getPluginManager().registerEvents(new ChunkCollector(), this);
        getServer().getPluginManager().registerEvents(new SellChest(), this);
        getServer().getPluginManager().registerEvents(new FarmHoeManager(), this);
        getServer().getPluginManager().registerEvents(new FarmHoeGui(), this);
        getServer().getPluginManager().registerEvents(new AuctionGui(), this);
        getServer().getPluginManager().registerEvents(new AntiAfk(), this);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        System.out.println("Listeners DONE | NEXT commands");

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
        this.getCommand("redstone").setExecutor(new RedstoneToggleCmd());
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
        this.getCommand("boost").setExecutor(new BoostCmd());
        this.getCommand("admin").setExecutor(new AdminCmd());
        System.out.println("Commands DONE | NEXT end");

        new AuctionsManager();

        System.out.println("§aDémarrage du plugin TERMINE!");
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    @Override
    public void onDisable() {
        CountdownFly.instance.WriteFlyLeft();
        HolosSetup.RemoveBoxeHolo();
        CratesManager.RemoveBoxeHolo();
        //BossBar.RemoveBossBarForPlayers();
        HolosSetup.RemoveNpc();
        WineSpawn.DestroyPnj();
        vaultHook.unhook();
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("Plugin stoppé !");
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    public void setup() {
        dfile = new File(this.getDataFolder(), "Challenges.yml");
        cfile = new File(this.getDataFolder(), "Fly.yml");
        zfile = new File(this.getDataFolder(), "Eco.yml");
        blcfile = new File(this.getDataFolder(), "Block.yml");
        ahfile = new File(this.getDataFolder(), "auctions.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Challenges.yml");
            }
        }

        data = YamlConfiguration.loadConfiguration(dfile);

        if (!cfile.exists()) {
            try {
                cfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Fly.yml");
            }
        }

        datac = YamlConfiguration.loadConfiguration(cfile);

        if (!zfile.exists()) {
            try {
                zfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Eco.yml");
            }
        }

        dataz = YamlConfiguration.loadConfiguration(zfile);

        if (!blcfile.exists()) {
            try {
                blcfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Block.yml");
            }
        }

        datablc = YamlConfiguration.loadConfiguration(blcfile);

        if (!ahfile.exists()) {
            try {
                ahfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Auctions.yml");
            }
        }

        dataah = YamlConfiguration.loadConfiguration(ahfile);


    }

    public FileConfiguration getDatac() {
        return datac;
    }

    public FileConfiguration getData() {
        return data;
    }

    public FileConfiguration getDataz() {
        return dataz;
    }

    public FileConfiguration getDatablc() {
        return datablc;
    }

    public FileConfiguration getDataah() {
        return dataah;
    }


    public void reloadData() throws IOException {
        try {
            data.load(dfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            datac.load(cfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataz.load(zfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            datablc.load(blcfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataah.load(ahfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            data.save(dfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            dataz.save(zfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            datablc.save(blcfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            dataah.save(ahfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }

    public void saveDataah() {
        try {
            dataah.save(ahfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }


    public void saveDatac() {
        try {
            datac.save(cfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }
}







