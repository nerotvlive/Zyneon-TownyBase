package live.nerotv.townybase.api;

import live.nerotv.Preloader;
import live.nerotv.townybase.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class API {

    public static boolean mySQL = true;
    public static String date;
    public static File Config = ConfigAPI.config;
    public static YamlConfiguration cfg = ConfigAPI.cfg;
    public static boolean isStopping;
    public static int RestartDay = getYearDay()+1;

    public static String noPlayer = "§cDieser Spieler wurde nicht gefunden§8!";
    public static String noPerms = "§cDas darfst du nicht tun§8!";

    public static String getTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return format.format(now);
    }

    public static int getMonth() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return Integer.valueOf(format.format(now).replace("0",""));
    }

    public static int getYear() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return Integer.valueOf(format.format(now));
    }

    public static int getYearDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static void initConfig() {

        //MYSQL
        ConfigAPI.checkEntry("Core.Settings.MySQL.host","127.0.0.1",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.port","3306",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.database","datenbank",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.username","benutzer",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.password","passwort",Config,cfg);

        //TABLIST
        ConfigAPI.checkEntry("Core.Tablist.Image","https://nerotv.live/zyneon.png",Config,cfg);
        ConfigAPI.checkEntry("Core.Tablist.Header"," \n §5zyneon.de §8● §7Minecraft - aber mehr \n ",Config,cfg);
        ConfigAPI.checkEntry("Core.Tablist.Footer"," \n §9https://www.zyneon.de \n §7sponsored by §ftube-hosting.de \n ",Config,cfg);

        //INIT-TEXT
        ConfigAPI.checkEntry("Core.Init.Line01","§5█§5█§5█§5█§5█§5█§5█§8╗§5█§5█§8╗§8░§8░§8░§5█§5█§8╗§5█§5█§5█§8╗§8░§8░§5█§5█§8╗§5█§5█§5█§5█§5█§5█§5█§8╗§8░§5█§5█§5█§5█§5█§8╗§8░§5█§5█§5█§8╗§8░§8░§5█§5█§8╗",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line02","§8╚§8═§8═§8═§8═§5█§5█§8║§8╚§5█§5█§8╗§8░§5█§5█§8╔§8╝§5█§5█§5█§5█§8╗§8░§5█§5█§8║§5█§5█§8╔§8═§8═§8═§8═§8╝§5█§5█§8╔§8═§8═§5█§5█§8╗§5█§5█§5█§5█§8╗§8░§5█§5█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line03","§8░§8░§5█§5█§5█§8╔§8═§8╝§8░§8╚§5█§5█§5█§5█§8╔§8╝§8░§5█§5█§8╔§5█§5█§8╗§5█§5█§8║§5█§5█§5█§5█§5█§8╗§8░§8░§5█§5█§8║§8░§8░§5█§5█§8║§5█§5█§8╔§5█§5█§8╗§5█§5█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line04","§5█§5█§8╔§8═§8═§8╝§8░§8░§8░§8░§8╚§5█§5█§8╔§8╝§8░§8░§5█§5█§8║§8╚§5█§5█§5█§5█§8║§5█§5█§8╔§8═§8═§8╝§8░§8░§5█§5█§8║§8░§8░§5█§5█§8║§5█§5█§8║§8╚§5█§5█§5█§5█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line05","§5█§5█§5█§5█§5█§5█§5█§8╗§8░§8░§8░§5█§5█§8║§8░§8░§8░§5█§5█§8║§8░§8╚§5█§5█§5█§8║§5█§5█§5█§5█§5█§5█§5█§8╗§8╚§5█§5█§5█§5█§5█§8╔§8╝§5█§5█§8║§8░§8╚§5█§5█§5█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line06","§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8╚§8═§8═§8═§8═§8╝§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝",Config,cfg);

        //SAVE
        ConfigAPI.saveConfig(Config,cfg);
        ConfigAPI.reloadConfig(Config,cfg);
    }

    public static void sendInit(boolean withInformation) {
        ConfigAPI.reloadConfig(Config,cfg);
        if(cfg.contains("Core.Init.Line06")) {
            sendMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line01")));
            sendMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line02")));
            sendMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line03")));
            sendMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line04")));
            sendMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line05")));
            sendMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line06")));
        } else {
            sendMessage(" null ");
            sendMessage(" null ");
            sendMessage(" null ");
            sendMessage(" null ");
            sendMessage(" null ");
            sendMessage(" null ");
        }
        if(withInformation) {
            sendMessage("§7Plugin §a" + Main.getName() + " (v" + Main.getVersion() + ") by §c" + Main.getAuthor() + "§7!");
        }
    }

    public static void initCommand(String commandName, CommandExecutor executor) {
        sendMessage("§7 ->  lade Kommando "+commandName+"...");
        Objects.requireNonNull(Main.getInstance().getCommand(commandName)).setExecutor(executor);
    }

    public static void initListener(String listenerNames, Listener listener) {
        sendMessage("§7 ->  lade Eventlistener "+listenerNames+"...");
        Preloader.getPluginManager().registerEvents(listener,Main.getInstance());
    }

    public static void sendErrorMessage(String message) {
        sendErrorMessage(Bukkit.getConsoleSender(),message);
    }

    public static void sendErrorMessage(CommandSender receiver, String message) {
        if(receiver instanceof Player r) {
            r.playSound(r.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
        }
        receiver.sendMessage("§c"+message.replace("&","§"));
    }

    public static void sendErrorMessage(CommandSender receiver, String message,boolean sound) {
        if(sound) {
            if(receiver instanceof Player r) {
                r.playSound(r.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
            }
        }
        receiver.sendMessage("§c"+message.replace("&","§"));
    }

    public static boolean isNumericString(String check) {
        try {
            if(check == null) {
                return false;
            }
            double d = Double.parseDouble(check);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumeric(String Check) {
        if(isNumericString(Check)) {
            return !(Double.parseDouble(Check) > 999999998);
        } else {
            return false;
        }
    }

    public static void sendMessage(String message) {
        sendMessage(Bukkit.getConsoleSender(),message);
    }

    public static void sendMessage(CommandSender receiver, String message) {
        if(receiver instanceof Player r) {
            r.playSound(r.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
        receiver.sendMessage("§dTowny §8| §7"+message.replace("&","§"));
    }

    public static String getTablistImage() {
        if(ConfigAPI.cfg.contains("Core.Tablist.Image")) {
            return ConfigAPI.cfg.getString("Core.Tablist.Image");
        } else {
            return "https://nerotv.live/zyneon.png";
        }
    }

    public static void setTablist() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setPlayerListHeader(cfg.getString("Core.Tablist.Header").replace("&", "§"));
            all.setPlayerListFooter(cfg.getString("Core.Tablist.Footer").replace("&", "§"));
            PlayerAPI.setScoreboard(all);
        }
    }

    public static void switchServer(Player player, String serverName) {
        ServerAPI.sendConsoleMessage("switchServer("+player.getName()+","+serverName+")");
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArray.toByteArray());
        } catch (Exception ex) {
            ServerAPI.sendConsoleMessage("§cEin Fehler ist beim Senden von §4"+player.getName()+"§c zu §4"+serverName+"§c aufgetreten.");
        }
    }

    public static void checkForRestart() {
        if(getYearDay()==RestartDay) {
            if(!isStopping) {
                ServerAPI.scheduledShutdown();
            }
        }
    }

    public static boolean isStringBlocked(String DYM) {
        String string = DYM.toLowerCase();
        if(string.contains("nigga")) {
            return true;
        } else if(string.contains("niga")) {
            return true;
        } else if(string.contains("nega")) {
            if(string.contains("negativ")) {
                if(string.contains("ohne")) {
                    if(string.contains("tiv")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else if(string.contains("neger")) {
            return true;
        } else if(string.contains("nigger")) {
            return true;
        } else if(string.contains("niger")) {
            if(string.contains("weniger")) {
                return false;
            } else {
                return true;
            }
        } else if(string.contains("nazi")) {
            return true;
        } else if(string.contains("hitler")) {
            return true;
        } else if(string.contains("hure")) {
            return true;
        } else if(string.contains("fotze")) {
            return true;
        } else if(string.contains("vergewalti")) {
            return true;
        } else if(string.contains("misgeburt")) {
            return true;
        } else if(string.contains("mistgeburt")) {
            return true;
        } else if(string.contains("missgeburt")) {
            return true;
        } else if(string.contains("misstgeburt")) {
            return true;
        } else if(string.contains("misset")) {
            return true;
        } else if(string.contains("miset")) {
            return true;
        } else if(string.contains("missed")) {
            return true;
        } else if(string.contains("mised")) {
            return true;
        } else if(string.contains("faggot")) {
            return true;
        } else if(string.contains("schwuchtel")) {
            return true;
        } else if(string.contains("spast")) {
            return true;
        } else if(string.contains("spasst")) {
            return true;
        } else if(string.contains("cancer")) {
            return true;
        } else if(string.contains("krebs")) {
            return true;
        } else if(string.contains("corona")) {
            return true;
        } else if(string.contains("corinski")) {
            return true;
        } else if(string.contains("atilla")) {
            return true;
        } else if(string.contains("hildmann")) {
            return true;
        } else if(string.contains("hildman")) {
            return true;
        } else if(string.contains("atila")) {
            return true;
        } else {
            return false;
        }
    }

    public static int townyInt;
    public static String animatedTowny() {
        int state = townyInt;
        String towny = "§fTOWNY";
        if(state == 1) {
            towny = "§aT§fOWNY";
        } else if(state == 2) {
            towny = "§fT§aO§fWNY";
        } else if(state == 3) {
            towny = "§fTO§aW§fNY";
        } else if(state == 4) {
            towny = "§fTOW§aN§fY";
        } else if(state == 5) {
            towny = "§fTOWN§aY§f";
        }
        return towny;
    }
}