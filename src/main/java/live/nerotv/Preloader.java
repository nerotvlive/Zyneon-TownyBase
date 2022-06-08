package live.nerotv;

import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Preloader extends JavaPlugin {

    private static PluginManager pluginManager = Bukkit.getPluginManager();
    private static String ver;
    private static String name;
    private static String author;
    private static Preloader instance;
    public static String getVersion() { return ver; }
    public static String getPluginName() { return name; }
    public static String getAuthor() { return author; }
    public static Preloader getInstance() { return instance; }
    public static PluginManager getPluginManager() { return pluginManager; }

    private void sendMessage(String message) {
        API.sendErrorMessage("§7[Towny] §e[PRELOADER] §7"+message);
    }

    @Override
    public void onLoad() {
        Main.onLoad();
    }

    @Override
    public void onEnable() {
        instance = this;
        ver = getDescription().getVersion();
        name = getDescription().getName();
        author = "nerotvlive";
        sendMessage("Checking for plugin dependencies...");
        if(pluginManager.getPlugin("floodgate")==null) {
            sendMessage("Couldn't find floodgate, disabling Bedrock Edition features!");
        }
        if(pluginManager.getPlugin("Vault")!=null) {
            Main.onEnable();
        } else {
            sendMessage("Couldn't find Vault, disabling TownyBase! §cPLEASE INSTALL VAULT");
            pluginManager.disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Main.onDisable();
        author = null;
        name = null;
        ver = null;
        instance = null;
    }
}