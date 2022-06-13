package main.java.fr.verymc;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import main.java.fr.verymc.core.antiafk.AntiAfk;
import main.java.fr.verymc.core.atout.AtoutCmd;
import main.java.fr.verymc.core.atout.AtoutGui;
import main.java.fr.verymc.core.atout.BuyAtoutGui;
import main.java.fr.verymc.core.auctions.AhCmd;
import main.java.fr.verymc.core.auctions.AuctionGui;
import main.java.fr.verymc.core.auctions.AuctionsManager;
import main.java.fr.verymc.core.cmd.base.*;
import main.java.fr.verymc.core.cmd.moderation.*;
import main.java.fr.verymc.core.eco.EcoAccountsManager;
import main.java.fr.verymc.core.featherfly.CountdownFly;
import main.java.fr.verymc.core.featherfly.DailyFlyCmd;
import main.java.fr.verymc.core.featherfly.FeatherFlyCmd;
import main.java.fr.verymc.core.featherfly.FeatherFlyInteract;
import main.java.fr.verymc.core.gui.*;
import main.java.fr.verymc.core.holos.HoloBlocManager;
import main.java.fr.verymc.core.holos.HolosSetup;
import main.java.fr.verymc.core.items.*;
import main.java.fr.verymc.core.playerwarps.PlayerWarpCmd;
import main.java.fr.verymc.core.playerwarps.PlayerWarpGuiManager;
import main.java.fr.verymc.core.playerwarps.PlayerWarpManager;
import main.java.fr.verymc.core.scoreboard.ScoreBoard;
import main.java.fr.verymc.core.scoreboard.TABManager;
import main.java.fr.verymc.core.shopgui.*;
import main.java.fr.verymc.core.storage.ConfigManager;
import main.java.fr.verymc.core.storage.SkyblockUser;
import main.java.fr.verymc.core.storage.SkyblockUserManager;
import main.java.fr.verymc.core.storage.StorageYAMLManager;
import main.java.fr.verymc.hub.crates.CratesManager;
import main.java.fr.verymc.hub.crates.KeyCmd;
import main.java.fr.verymc.hub.events.*;
import main.java.fr.verymc.hub.invest.InvestCmd;
import main.java.fr.verymc.hub.invest.InvestManager;
import main.java.fr.verymc.hub.winelottery.WineGui;
import main.java.fr.verymc.hub.winelottery.WineSpawn;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.blocks.ChestListener;
import main.java.fr.verymc.island.blocks.ChestManager;
import main.java.fr.verymc.island.blocks.ChestsCmd;
import main.java.fr.verymc.island.challenges.ChallengesCmd;
import main.java.fr.verymc.island.challenges.IslandChallengesGuis;
import main.java.fr.verymc.island.challenges.IslandChallengesListener;
import main.java.fr.verymc.island.challenges.IslandChallengesReset;
import main.java.fr.verymc.island.cmds.IslandCmd;
import main.java.fr.verymc.island.evenement.ChatReaction;
import main.java.fr.verymc.island.evenement.EventManager;
import main.java.fr.verymc.island.events.IslandGeneratorForm;
import main.java.fr.verymc.island.events.IslandInteractManager;
import main.java.fr.verymc.island.events.IslandPlayerMove;
import main.java.fr.verymc.island.guis.IslandGuiManager;
import main.java.fr.verymc.island.minions.*;
import main.java.fr.verymc.island.protections.BlockListener;
import main.java.fr.verymc.island.protections.EntityListener;
import main.java.fr.verymc.utils.UtilsManager;
import main.java.fr.verymc.utils.WorldBorderUtil;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {

    public static Main instance;
    static LuckPerms api;
    private final HashMap<String, Integer> spawncooldown = new HashMap<>();
    public ArrayList<Player> pending = new ArrayList<>();

    public ArrayList<Player> pendingTrade = new ArrayList<>();
    public ArrayList<Player> haverequest = new ArrayList<>();

    public ArrayList<Player> haveTradeRequest = new ArrayList<>();
    public HashMap<String, String> tpatarget = new HashMap<>();

    public HashMap<String, String> tradeTarget = new HashMap<>();

    public ArrayList<TradeManager> tradeInProcess = new ArrayList<>();

    public ClaimCmdSaver saver;
    public main.java.fr.verymc.utils.enums.ServerType serverType;

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
        System.out.println("------------------------------------------------");
        //server type ???

        System.out.println("------------------------------------------------");
        //CORE INIT PART 1
        System.out.println("Starting core part 1...");
        saveDefaultConfig();
        instance = this;
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

        new SkyblockUserManager();
        new EcoAccountsManager();

        new UtilsManager();

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

        new TABManager();

        new PlayerWarpManager();
        new EventManager();

        startListenerModule();

        System.out.println("Starting core part 1 FINISHED");
        System.out.println("------------------------------------------------");


        //ISLAND ADDITIONNAL STARTUP
        //if (serverType == ServerType.ISLAND) {
        System.out.println("Starting Island ADDITIONNAL module...");
        saveResource("ileworld.schem", true);
        saveResource("clear.schem", true);
        new IslandManager();
        new WorldBorderUtil(this);
        new IslandChallengesReset();

        new ChestManager();
        ChestManager.instance.autoSellForVeryChest();

        new MinionManager();
        new MinionsGui();
        new MinionHarvest();

        new HoloBlocManager();

        for (Island island : IslandManager.instance.islands) {
            island.toggleTimeAndWeather();
            island.setBorderColor(island.getBorderColor());
        }
        //}


        System.out.println("Starting Island ADDITIONNAL module FINISHED");

        //HUB ADDITIONNAL STARTUP
        //if (serverType == ServerType.HUB) {
        System.out.println("Starting Hub ADDITIONNAL module...");
        WineSpawn.SpawnPnj(new Location(Bukkit.getServer().getWorld("world"), -184.5, 70.5, -77.5, -90, 0));
        HolosSetup.SpawnPnj2(new Location(Bukkit.getServer().getWorld("world"), -155.5, 71, -60.5, 90, 0),
                new Location(Bukkit.getServer().getWorld("world"), -172.5, 71, -64.5, 90, 0));
        HolosSetup.SpawnCrates();
        CratesManager.SpawnCrates();
        new InvestManager();
        System.out.println("Starting Hub ADDITIONNAL module FINISHED");
        //}


        //Dungeon ADDITIONNAL STARTUP
        /*if (serverType == ServerType.DUNGEON) {
        System.out.println("Starting Dungeon ADDITIONNAL module...");

        System.out.println("Starting Dungeon ADDITIONNAL module FINISHED");
        }*/


        //CORE PART 2
        System.out.println("Starting core part 2...");
        new ConfigManager();
        new StorageYAMLManager();
        saver = new ClaimCmdSaver();
        new AuctionsManager();
        ScoreBoard.acces.updateScoreBoard();
        ChatReaction.StartChatReaction();
        for (Player player : Bukkit.getOnlinePlayers()) {
            SkyblockUserManager.instance.checkForAccount(player);
            ScoreBoard.acces.setScoreBoard(player);
        }
        System.out.println("Starting core part 2 FINISHED");

        System.out.println("§aDémarrage du plugin TERMINE!");
        System.out.println("------------------------------------------------");
    }

    @Override
    public void onDisable() {
        for (SkyblockUser user : SkyblockUserManager.instance.users) {
            if (user.isInInvestMode()) {
                InvestManager.instance.giveReward(user);
            }
        }
        StorageYAMLManager.instance.sendDataToAPIAuto(true);
        for (Hologram hologram : HologramsAPI.getHolograms(this)) {
            hologram.delete();
        }
        for (NPC npc : CitizensAPI.getNPCRegistry().sorted()) {
            npc.destroy();
        }
        saver.saveCooldown();
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
        getServer().getPluginManager().registerEvents(new TradeGui(), this);
        getServer().getPluginManager().registerEvents(new MoneyTradeGui(), this);
        getServer().getPluginManager().registerEvents(new PlayerWarpGuiManager(), this);


        //ISLAND LISTENER
        //if (serverType == ServerType.ISLAND) {
        getServer().getPluginManager().registerEvents(new Farm2WinGui(), this);
        getServer().getPluginManager().registerEvents(new WineGui(), this);
        getServer().getPluginManager().registerEvents(new WarpGui(), this);
        getServer().getPluginManager().registerEvents(new IslandChallengesGuis(), this);
        getServer().getPluginManager().registerEvents(new IslandChallengesListener(), this);
        getServer().getPluginManager().registerEvents(new IslandGuiManager(), this);
        getServer().getPluginManager().registerEvents(new IslandInteractManager(), this);
        getServer().getPluginManager().registerEvents(new IslandPlayerMove(), this);
        getServer().getPluginManager().registerEvents(new IslandGeneratorForm(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new CountdownFly(), this);
        getServer().getPluginManager().registerEvents(new FeatherFlyInteract(), this);
        getServer().getPluginManager().registerEvents(new SwitchWorld(), this);
        //}


        //HUB LISTENER
        //if (serverType == ServerType.HUB) {
        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new AntiExplo(), this);
        getServer().getPluginManager().registerEvents(new CratesManager(), this);
        getServer().getPluginManager().registerEvents(new HolosSetup(), this);
        //}


        //DUNGEON LISTENER
        if (serverType == main.java.fr.verymc.utils.enums.ServerType.DUNGEON) {

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
    }

}