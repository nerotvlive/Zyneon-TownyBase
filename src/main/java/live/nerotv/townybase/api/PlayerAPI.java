package live.nerotv.townybase.api;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import live.nerotv.townybase.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerAPI {

    public static String rankName(Player player) {
        if(player.hasPermission("zyneon.leading")) {
            return "Leitung";
        } else if(player.hasPermission("zyneon.team")) {
            return "Team";
        } else if(player.hasPermission("zyneon.creator")) {
            return "Creator";
        } else if(player.hasPermission("zyneon.premium")) {
            return "Premium";
        } else {
            return "Spieler";
        }
    }

    public static String getNation(Player player) {
        try {
            try {
                return TownyAPI.getInstance().getResident(player).getNation().getName();
            } catch (TownyException e) {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String getTown(Player player) {
        try {
            try {
                return TownyAPI.getInstance().getResident(player).getTown().getName();
            } catch (TownyException e) {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static void setScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective boardContent = board.getObjective("TOWNY");
        board.resetScores("TOWNY");
        Score utralo = boardContent.getScore("§fServer§7-§fIP§8:");
        boardContent.setDisplayName(API.animatedTowny());
        Score utraloContent = boardContent.getScore("§ezyneon.de");
        Score placeholder0 = boardContent.getScore("§0");
        Score placeholder1 = boardContent.getScore("§1");
        Score placeholder2 = boardContent.getScore("§2");
        Score placeholder3 = boardContent.getScore("§3");
        Score placeholder4 = boardContent.getScore("§4");
        Score placeholder5 = boardContent.getScore("§5");
        Score money = boardContent.getScore("§fGeld§8:");
        int moneyInt = (int) Main.getEco().getBalance(player.getUniqueId()).getBalance();
        Score moneyContent = boardContent.getScore("§e"+moneyInt);
        Score rank = boardContent.getScore("§fRang§8:");
        String rankString = "§e"+rankName(player);
        Score rankContent = boardContent.getScore("§e"+rankString);
        Score level = boardContent.getScore("§fZeit§8:");
        Score levelContent = boardContent.getScore("§e"+API.date);
        Score nation = boardContent.getScore("§fNation§8:");
        String nationstring;
        if(TownyAPI.getInstance().getResident(player).hasNation()) {
            nationstring = getNation(player);
        } else {
            nationstring = "Keine Nation";
        }
        Score town = boardContent.getScore("§fStadt§8:");
        String townstring;
        if(getTown(player)==null) {
            nationstring = "Keine Nation";
            townstring = "Keine Stadt";
        } else {
            try {
                if(!TownyAPI.getInstance().getResident(player).getTown().hasNation()) {
                    nationstring = "Keine Nation";
                }
            } catch (NotRegisteredException e) {
                nationstring = "Keine Nation";
            }
            townstring = getTown(player);
        }
        Score nationContent = boardContent.getScore("§e"+nationstring);
        Score townContent = boardContent.getScore("§e"+townstring);
        placeholder0.setScore(15);
        money.setScore(14);
        moneyContent.setScore(13);
        placeholder1.setScore(12);
        nation.setScore(11);
        nationContent.setScore(10);
        placeholder5.setScore(9);
        town.setScore(8);
        townContent.setScore(7);
        placeholder2.setScore(6);
        rank.setScore(5);
        rankContent.setScore(4);
        placeholder3.setScore(3);
        utralo.setScore(2);
        utraloContent.setScore(1);
        Main.setPrefix(player);
    }

    public static void renewScoreboard(Player player) {
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        player.setScoreboard(sm.getNewScoreboard());
        Scoreboard board = player.getScoreboard();
        if(board.getObjective("TOWNY")==null) {
            board.registerNewObjective("TOWNY", "TOWNY");
        }
        Objective boardContent = board.getObjective("TOWNY");
        boardContent.setDisplaySlot(DisplaySlot.SIDEBAR);
        setScoreboard(player);
    }
}