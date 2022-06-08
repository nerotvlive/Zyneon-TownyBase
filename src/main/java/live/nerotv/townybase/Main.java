package live.nerotv.townybase;

import live.nerotv.Preloader;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.api.ConfigAPI;
import live.nerotv.townybase.api.MySQL;
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
        API.sendInit(false);
        API.sendMessage(" §0 ");
        API.sendMessage(" §0 ");
        API.sendMessage("Lade plugin...");
        API.sendMessage(" §0 ");
        API.sendMessage("Config-Datei wird überprüft und ersellt, ergänzt oder korrigiert...");
        API.initConfig();
        API.sendMessage("Config-Datei wurde initialisiert!");
        API.sendMessage("Plugin geladen! ");
        API.sendMessage(" §0 ");
        API.sendMessage(" §0 ");
        API.sendInit(false);
    }

    public static void onEnable() {
        API.sendInit(true);
        API.sendMessage(" §0 ");
        API.sendMessage(" §0 ");
        API.sendMessage("Aktiviere plugin...");
        API.date = API.getTime();
        API.sendMessage(" §0 ");
        API.initConfig();
        ConfigAPI.reloadConfig(API.Config,API.cfg);
        setupEconomy();
        initCommands();
        initListener();
        BroadcastManager.start();
        API.sendMessage("Plugin aktiviert!");
        API.sendMessage(" §0 ");
        API.sendMessage(" §0 ");
        API.sendInit(true);
    }

    public static void onDisable() {
        API.sendInit(true);
        vaultImpl = null;
        eco = null;
        if(API.mySQL) {
            if(MySQL.isConnected()) {
                MySQL.disconnect();
            }
        }
        API.sendInit(true);
    }

    private static void initCommands() {
        API.sendMessage("§fLade Kommandos...");
        API.initCommand("Balance",new Balance());
        API.initCommand("Check",new Check());
        API.sendMessage("§fKommandos geladen!");
        API.sendMessage(" §0 ");
    }

    private static void initListener() {
        API.sendMessage("§fLade Eventlistener...");
        API.initListener("PlayerChat",new PlayerChat());
        API.initListener("PlayerJoin",new PlayerJoin());
        API.initListener("PlayerQuit",new PlayerQuit());
        API.initListener("PlayerTown",new PlayerTown());
        API.initListener("TownNation",new TownNation());
        API.initListener("WorldChange",new WorldChange());
        API.sendMessage("§fEventlistener geladen!");
        API.sendMessage(" §0 ");
    }

    private static void setupEconomy() {
        API.sendMessage("Versuche eine MySQL-Verbindung herzustellen...");
        boolean setupEconomy;
        eco = new Ecosystem();
        vaultImpl = new VaultEco();
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            setupEconomy = false;
        } else {
            getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class,vaultImpl,getInstance(), ServicePriority.Normal);
            setupEconomy = true;
        }
        if(setupEconomy) {
            API.sendMessage("§fMySQL-Verbindung wurde erfolgreich hergestellt§8!");
        } else {
            API.sendMessage("§cMySQL-Verbindung konnte nicht hergestellt werden§8!");
        }
        API.sendMessage(" §0 ");
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