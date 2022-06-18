package live.nerotv.townybase.manager;

import com.zyneonstudios.api.Zyneon;
import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class BroadcastManager {

    public static void start() {
        sendScoreboard(Bukkit.getScheduler());
        autoRenew(Bukkit.getScheduler());
    }

    private static void sendScoreboard(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for(Player all : Bukkit.getOnlinePlayers()) {
                PlayerAPI.setScoreboard(all);
            }
            if(API.townyInt == 5) {
                API.townyInt = 0;
            } else if(API.townyInt == 0) {
                API.townyInt = 1;
            } else if(API.townyInt == 1) {
                API.townyInt = 2;
            } else if(API.townyInt == 2) {
                API.townyInt = 3;
            } else if(API.townyInt == 3) {
                API.townyInt = 4;
            } else if(API.townyInt == 4) {
                API.townyInt = 5;
            }
            sendScoreboard(scheduler);
        },15);
    }

    private static void autoRenew(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(Main.getInstance(), () -> {
            if(!API.date.equals(Zyneon.getAPI().getTime())) {
                API.date = Zyneon.getAPI().getTime();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    PlayerAPI.renewScoreboard(all);
                }
            }
            autoRenew(scheduler);
        },15*20);
    }
}
