package fr.farmeurimmo.criptmania;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

import fr.farmeurimmo.criptmania.WineLottery.WineGui;
import fr.farmeurimmo.criptmania.WineLottery.WineSpawn;
import fr.farmeurimmo.criptmania.atout.AtoutCmd;
import fr.farmeurimmo.criptmania.atout.AtoutGui;
import fr.farmeurimmo.criptmania.atout.BuyAtoutGui;
import fr.farmeurimmo.criptmania.challenges.ChallengesBlockBreak;
import fr.farmeurimmo.criptmania.challenges.ChallengesCmd;
import fr.farmeurimmo.criptmania.challenges.ChallengesGuis;
import fr.farmeurimmo.criptmania.challenges.ChallengesReset;
import fr.farmeurimmo.criptmania.cmd.base.BarCmd;
import fr.farmeurimmo.criptmania.cmd.base.CraftCmd;
import fr.farmeurimmo.criptmania.cmd.base.EnchantementCmd;
import fr.farmeurimmo.criptmania.cmd.base.Farm2WinCmd;
import fr.farmeurimmo.criptmania.cmd.base.FeedCmd;
import fr.farmeurimmo.criptmania.cmd.base.FlyCmd;
import fr.farmeurimmo.criptmania.cmd.base.HatCmd;
import fr.farmeurimmo.criptmania.cmd.base.MenuCmd;
import fr.farmeurimmo.criptmania.cmd.base.SpawnCmd;
import fr.farmeurimmo.criptmania.cmd.base.TpNoCmd;
import fr.farmeurimmo.criptmania.cmd.base.TpYesCmd;
import fr.farmeurimmo.criptmania.cmd.base.TpaCancelCmd;
import fr.farmeurimmo.criptmania.cmd.base.TpaCmd;
import fr.farmeurimmo.criptmania.cmd.base.WarpCmd;
import fr.farmeurimmo.criptmania.cmd.base.WarpsCmd;
import fr.farmeurimmo.criptmania.cmd.base.WikiCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.AntiAfkMineCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.BuildCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.CheckFlyCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.DataCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.GmCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.GmLCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.InvseeCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.RedstoneToggleCmd;
import fr.farmeurimmo.criptmania.cmd.moderation.SSCmd;
import fr.farmeurimmo.criptmania.crates.CratesManager;
import fr.farmeurimmo.criptmania.crates.KeyCmd;
import fr.farmeurimmo.criptmania.evenement.ChatReaction;
import fr.farmeurimmo.criptmania.events.AfkMineBreakCheck;
import fr.farmeurimmo.criptmania.events.AntiExplo;
import fr.farmeurimmo.criptmania.events.Interact;
import fr.farmeurimmo.criptmania.events.JoinLeave;
import fr.farmeurimmo.criptmania.events.RedstoneCheck;
import fr.farmeurimmo.criptmania.events.SwitchWorld;
import fr.farmeurimmo.criptmania.events.Tabulation;
import fr.farmeurimmo.criptmania.events.Tchat;
import fr.farmeurimmo.criptmania.featherfly.DailyFlyCmd;
import fr.farmeurimmo.criptmania.featherfly.FeatherFlyCmd;
import fr.farmeurimmo.criptmania.featherfly.FeatherFlyInteract;
import fr.farmeurimmo.criptmania.gui.AfkMineCaptchaGui;
import fr.farmeurimmo.criptmania.gui.Farm2WinGui;
import fr.farmeurimmo.criptmania.gui.SanctionSetGuiManager;
import fr.farmeurimmo.criptmania.gui.WarpGui;
import fr.farmeurimmo.criptmania.gui.WikiGui;
import fr.farmeurimmo.criptmania.items.ItemLegCmd;
import fr.farmeurimmo.criptmania.items.PermanantItem;
import fr.farmeurimmo.criptmania.scoreboard.ScoreBoard;
import fr.farmeurimmo.criptmania.utils.BossBar;
import net.luckperms.api.LuckPerms;

public class Main extends JavaPlugin implements Listener {
	
	public static ArrayList<Player> pending = new ArrayList<Player>();
	public static ArrayList<Player> haverequest = new ArrayList<Player>();
	
	static LuckPerms api;
    private static Main instance;
    
    public static Main instance1;
    
    public static Main getInstance() {
        return instance;
    }
    
   private static HashMap<String, String> tpatarget = new HashMap<>();
	
	public void setTarget(String uuid, String aaa) {
		if (aaa == null)
			tpatarget.remove(uuid);
		else
			tpatarget.put(uuid, aaa);
	}
    public String getTarget(String player) {
    	if(tpatarget.containsKey(player))
    		return tpatarget.get(player);
    	else
    		return null;
    }
    public void ClearPlayerAndTarget(String player) {
    	if(tpatarget.containsKey(player))
    	tpatarget.remove(player);
    }
	
   private static HashMap<String, Integer> spawncooldown = new HashMap<>();
	
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
		instance1 = this;
		Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
		Bukkit.getPluginManager().isPluginEnabled("Citizens");
		Bukkit.getPluginManager().isPluginEnabled("TheNewEconomy");
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
			System.out.println("Le plugin LuckPerms a été trouvé !");
		} else {
			getLogger().warning("Le plugin LuckPerms est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Initialisation de l'api LuckPerms en cours...");
		if (provider != null) {
			api = provider.getProvider();
			System.out.println("API initialisé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		}
		if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
			System.out.println("Le plugin Citizens a été trouvé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("Initialisation de l'api Citizens en cours...");
			System.out.println("API initialisé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		} else {
			getLogger().warning("Le plugin Citizens est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		if (Bukkit.getPluginManager().getPlugin("TheNewEconomy") != null) {
			System.out.println("Le plugin TNE a été trouvé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("Initialisation de l'api TNE en cours...");
			System.out.println("API initialisé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		} else {
			getLogger().warning("Le plugin TheNewEconomy est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		System.out.println("§aDémarrage du plugin réussi !");
		System.out.println("-----------------------------------------------------------------------------------------------------");
		setup();
		getServer().getPluginManager().registerEvents(new JoinLeave(), this);
		getServer().getPluginManager().registerEvents(new Tchat(), this);
		getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
		getServer().getPluginManager().registerEvents(new Interact(), this);
		getServer().getPluginManager().registerEvents(new PermanantItem(), this);
		getServer().getPluginManager().registerEvents(new AntiExplo(), this);
		getServer().getPluginManager().registerEvents(new WikiGui(), this);
		getServer().getPluginManager().registerEvents(new Farm2WinGui(), this);
		getServer().getPluginManager().registerEvents(new WineGui(), this);
		getServer().getPluginManager().registerEvents(new WarpGui(), this);
		getServer().getPluginManager().registerEvents(new FeatherFlyInteract(), this);
		getServer().getPluginManager().registerEvents(new SwitchWorld(), this);
		getServer().getPluginManager().registerEvents(new Tabulation(), this);
		getServer().getPluginManager().registerEvents(new AfkMineCaptchaGui(), this);
		getServer().getPluginManager().registerEvents(new AfkMineBreakCheck(), this);
		getServer().getPluginManager().registerEvents(new ChatReaction(), this);
		getServer().getPluginManager().registerEvents(new SanctionSetGuiManager(), this);
		getServer().getPluginManager().registerEvents(new RedstoneCheck(), this);
		getServer().getPluginManager().registerEvents(new ChallengesGuis(), this);
		getServer().getPluginManager().registerEvents(new ChallengesBlockBreak(), this);
		getServer().getPluginManager().registerEvents(new AtoutGui(), this);
		getServer().getPluginManager().registerEvents(new BuyAtoutGui(), this);
		getServer().getPluginManager().registerEvents(new CratesManager(), this);
		this.getCommand("spawn").setExecutor(new SpawnCmd());
		this.getCommand("wiki").setExecutor(new WikiCmd());
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
		this.getCommand("sanctionset").setExecutor(new SSCmd());
		this.getCommand("checkfly").setExecutor(new CheckFlyCmd());
		this.getCommand("invsee").setExecutor(new InvseeCmd());
		this.getCommand("redstone").setExecutor(new RedstoneToggleCmd());
		this.getCommand("tpa").setExecutor(new TpaCmd());
		this.getCommand("tpacancel").setExecutor(new TpaCancelCmd());
		this.getCommand("tpyes").setExecutor(new TpYesCmd());
		this.getCommand("tpno").setExecutor(new TpNoCmd());
		this.getCommand("challenges").setExecutor(new ChallengesCmd());
		this.getCommand("atout").setExecutor(new AtoutCmd());
		this.getCommand("datafile").setExecutor(new DataCmd());
		this.getCommand("key").setExecutor(new KeyCmd());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		Main.spawncooldown.clear();
		BossBar.CreateBossBar(1);
		BuildCmd.Build.clear();
		WineSpawn.SpawnPnj(new Location(Bukkit.getServer().getWorld("world"), -194.5, 109, -49.5, -90, 2));
		for(Player player : Bukkit.getOnlinePlayers()) {
			ScoreBoard.setScoreBoard(player);
		}
		ChatReaction.mots.addAll(Arrays.asList("CriptMania","Skyblock","Ile","Farm2Win"));
	    ChatReaction.StartChatReaction();
	    ChallengesReset.CheckForReset();
	    CratesManager.SpawnCrates();
	}
	@Override
	public void onDisable() {
		WineSpawn.RemovePnj();
		BossBar.RemoveBossBarForPlayers();
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Plugin stoppé !");
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
	public FileConfiguration data;
    public File dfile;
   
    public void setup() {
        dfile = new File(this.getDataFolder(), "Challenges.yml");
       
        if(!dfile.exists()) {
            try {
                dfile.createNewFile();
            }
            catch(IOException e) {
                getLogger().info("§c§lErreur lors de la création de Challenges.yml");
            }
        }
       
        data = YamlConfiguration.loadConfiguration(dfile);
       
    }
   
    public FileConfiguration getData() {
        return data;
    }
    
    public void reloadData() throws FileNotFoundException, IOException {
            try {
				data.load(dfile);
			} catch (InvalidConfigurationException e) {
				getLogger().info("§c§lErreur lors de la sauvegarde!");
				e.printStackTrace();
        }
    }
   
    public void saveData() {
        try {
            data.save(dfile);
        }
        catch(IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }
}







