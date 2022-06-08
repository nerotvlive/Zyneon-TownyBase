package live.nerotv.townybase.api;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.configuration.Config;
import live.nerotv.townybase.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import java.util.Objects;

public class API {

    public static Config config = new Config("plugins/TownyBase/config.yml");
    public static String date;

    public static void initConfig() {

        //TABLIST
        config.checkEntry("Core.Tablist.Header"," \n §5zyneon.de §8● §7Minecraft - aber mehr \n ");
        config.checkEntry("Core.Tablist.Footer"," \n §9https://www.zyneon.de \n §7sponsored by §ftube-hosting.de \n ");

        //INIT-TEXT
        config.checkEntry("Core.Init.Line01","§5█§5█§5█§5█§5█§5█§5█§8╗§5█§5█§8╗§8░§8░§8░§5█§5█§8╗§5█§5█§5█§8╗§8░§8░§5█§5█§8╗§5█§5█§5█§5█§5█§5█§5█§8╗§8░§5█§5█§5█§5█§5█§8╗§8░§5█§5█§5█§8╗§8░§8░§5█§5█§8╗");
        config.checkEntry("Core.Init.Line02","§8╚§8═§8═§8═§8═§5█§5█§8║§8╚§5█§5█§8╗§8░§5█§5█§8╔§8╝§5█§5█§5█§5█§8╗§8░§5█§5█§8║§5█§5█§8╔§8═§8═§8═§8═§8╝§5█§5█§8╔§8═§8═§5█§5█§8╗§5█§5█§5█§5█§8╗§8░§5█§5█§8║");
        config.checkEntry("Core.Init.Line03","§8░§8░§5█§5█§5█§8╔§8═§8╝§8░§8╚§5█§5█§5█§5█§8╔§8╝§8░§5█§5█§8╔§5█§5█§8╗§5█§5█§8║§5█§5█§5█§5█§5█§8╗§8░§8░§5█§5█§8║§8░§8░§5█§5█§8║§5█§5█§8╔§5█§5█§8╗§5█§5█§8║");
        config.checkEntry("Core.Init.Line04","§5█§5█§8╔§8═§8═§8╝§8░§8░§8░§8░§8╚§5█§5█§8╔§8╝§8░§8░§5█§5█§8║§8╚§5█§5█§5█§5█§8║§5█§5█§8╔§8═§8═§8╝§8░§8░§5█§5█§8║§8░§8░§5█§5█§8║§5█§5█§8║§8╚§5█§5█§5█§5█§8║");
        config.checkEntry("Core.Init.Line05","§5█§5█§5█§5█§5█§5█§5█§8╗§8░§8░§8░§5█§5█§8║§8░§8░§8░§5█§5█§8║§8░§8╚§5█§5█§5█§8║§5█§5█§5█§5█§5█§5█§5█§8╗§8╚§5█§5█§5█§5█§5█§8╔§8╝§5█§5█§8║§8░§8╚§5█§5█§5█§8║");
        config.checkEntry("Core.Init.Line06","§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8╚§8═§8═§8═§8═§8╝§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝");

        //SAVE
        config.saveConfig();
        config.reloadConfig();
    }

    public static void sendInit(boolean withInformation) {
        config.reloadConfig();
        if(config.getCFG().contains("Core.Init.Line06")) {
            Zyneon.getZyneonServer().sendMessage(Objects.requireNonNull(config.getCFG().getString("Core.Init.Line01")));
            Zyneon.getZyneonServer().sendMessage(Objects.requireNonNull(config.getCFG().getString("Core.Init.Line02")));
            Zyneon.getZyneonServer().sendMessage(Objects.requireNonNull(config.getCFG().getString("Core.Init.Line03")));
            Zyneon.getZyneonServer().sendMessage(Objects.requireNonNull(config.getCFG().getString("Core.Init.Line04")));
            Zyneon.getZyneonServer().sendMessage(Objects.requireNonNull(config.getCFG().getString("Core.Init.Line05")));
            Zyneon.getZyneonServer().sendMessage(Objects.requireNonNull(config.getCFG().getString("Core.Init.Line06")));
        } else {
            Zyneon.getZyneonServer().sendMessage(" null ");
            Zyneon.getZyneonServer().sendMessage(" null ");
            Zyneon.getZyneonServer().sendMessage(" null ");
            Zyneon.getZyneonServer().sendMessage(" null ");
            Zyneon.getZyneonServer().sendMessage(" null ");
            Zyneon.getZyneonServer().sendMessage(" null ");
        }
        if(withInformation) {
            Zyneon.getZyneonServer().sendMessage("§7Plugin §a" + Main.getName() + " (v" + Main.getVersion() + ") by §c" + Main.getAuthor() + "§7!");
        }
    }

    public static void initCommand(String commandName, CommandExecutor executor) {
        Zyneon.getZyneonServer().sendMessage("§f  -> §7Lade Kommando §e"+commandName+"§8...");
        Objects.requireNonNull(Main.getInstance().getCommand(commandName)).setExecutor(executor);
    }

    public static boolean isNumeric(String check) {
        try {
            if(check == null) {
                return false;
            }
            double d = Double.parseDouble(check);
            return !(d > 999999999);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void setTablist() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setPlayerListHeader(Objects.requireNonNull(config.getCFG().getString("Core.Tablist.Header")).replace("&", "§"));
            all.setPlayerListFooter(Objects.requireNonNull(config.getCFG().getString("Core.Tablist.Footer")).replace("&", "§"));
            PlayerAPI.setScoreboard(all);
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
                    return string.contains("tiv");
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
            return !string.contains("weniger");
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
        } else return string.contains("atila");
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