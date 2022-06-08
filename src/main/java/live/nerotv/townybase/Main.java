package live.nerotv.townybase;

import com.zyneonstudios.api.Zyneon;
import live.nerotv.Preloader;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.commands.Balance;
import live.nerotv.townybase.commands.Check;
import live.nerotv.townybase.economy.Economy;
import live.nerotv.townybase.economy.Ecosystem;
import live.nerotv.townybase.economy.VaultEco;
import live.nerotv.townybase.listener.*;
import live.nerotv.townybase.manager.BroadcastManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;

import static org.bukkit.Bukkit.getServer;

public class Main {

    private static VaultEco vaultImpl;
    private static Economy eco = new Ecosystem();
    public static Preloader getInstance() { return Preloader.getInstance(); }
    public static String getVersion() { return Preloader.getVersion(); }
    public static String getAuthor() { return Preloader.getAuthor(); }
    public static String getName() { return Preloader.getPluginName(); }
    public static Economy getEco() { return eco; }

    public static void onLoad() {
        API.initConfig();
        API.sendInit(false);
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        Zyneon.getZyneonServer().sendMessage("Lade plugin...");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        Zyneon.getZyneonServer().sendMessage("Config-Datei wird überprüft und ersellt, ergänzt oder korrigiert...");
        Zyneon.getZyneonServer().sendMessage("Config-Datei wurde initialisiert!");
        Zyneon.getZyneonServer().sendMessage("Plugin geladen! ");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        API.sendInit(false);
    }

    public static void onEnable() {
        API.initConfig();
        API.sendInit(true);
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        Zyneon.getZyneonServer().sendMessage("Aktiviere plugin...");
        API.date = Zyneon.getAPI().getTime();
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        API.config.reloadConfig();
        setupEconomy();
        initCommands();
        initListener();
        BroadcastManager.start();
        Zyneon.getZyneonServer().sendMessage("Plugin aktiviert!");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
        API.sendInit(true);
    }

    public static void onDisable() {
        API.sendInit(true);
        vaultImpl = null;
        eco = null;
        API.sendInit(true);
    }

    private static void initCommands() {
        Zyneon.getZyneonServer().sendMessage("§fLade Kommandos...");
        API.initCommand("Balance",new Balance());
        API.initCommand("Check",new Check());
        Zyneon.getZyneonServer().sendMessage("§fKommandos geladen!");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
    }

    private static void initListener() {
        Zyneon.getZyneonServer().sendMessage("§fLade Eventlistener...");
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerChat(),Preloader.getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerJoin(),Preloader.getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerQuit(),Preloader.getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerTown(),Preloader.getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new TownNation(),Preloader.getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new WorldChange(),Preloader.getInstance());
        Zyneon.getZyneonServer().sendMessage("§fEventlistener geladen!");
        Zyneon.getZyneonServer().sendMessage(" §0 ");
    }

    private static void setupEconomy() {
        boolean setupEconomy;
        eco = new Ecosystem();
        vaultImpl = new VaultEco();
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            setupEconomy = false;
        } else {
            getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class,vaultImpl,getInstance(), ServicePriority.Normal);
            setupEconomy = true;
        }
        if(!setupEconomy) {
            Zyneon.getZyneonServer().sendMessage("§cEconomy konnte nicht gestartet werden.§8!");
        }
        Zyneon.getZyneonServer().sendMessage(" §0 ");
    }

    public static void setPrefix(Player player) {
        String Name = player.getName();
        org.bukkit.scoreboard.Scoreboard Scoreboard = player.getScoreboard();
        if(Scoreboard.getTeam("03Spieler")==null) {
            Scoreboard.registerNewTeam("00000Team");
            Scoreboard.registerNewTeam("01Creator");
            Scoreboard.registerNewTeam("02Premium");
            Scoreboard.registerNewTeam("03Spieler");
            Scoreboard.getTeam("00000Team").setPrefix("§5Team §8● §f");
            Scoreboard.getTeam("01Creator").setPrefix("§5Creator §8● §f");
            Scoreboard.getTeam("02Premium").setPrefix("§dPremium §8● §f");
            Scoreboard.getTeam("03Spieler").setPrefix("§dSpieler §8● §f");
            Scoreboard.getTeam("00000Team").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("01Creator").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("02Premium").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("03Spieler").setCanSeeFriendlyInvisibles(false);
        }
        for(Player p: Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("zyneon.team")) {
                Scoreboard.getTeam("00000Team").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("00000Team").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.creator")) {
                Scoreboard.getTeam("01Creator").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("01Creator").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.premium")) {
                Scoreboard.getTeam("02Premium").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("02Premium").getPrefix() + Name);
            } else {
                Scoreboard.getTeam("03Spieler").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("03Spieler").getPrefix() + Name);
            }
        }
    }
}