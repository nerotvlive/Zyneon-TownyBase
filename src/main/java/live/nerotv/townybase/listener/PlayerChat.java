package live.nerotv.townybase.listener;

import com.zyneonstudios.api.Zyneon;
import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.api.PlayerAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(e.getMessage().equals("A")) {
            e.setCancelled(true);
            p.sendMessage("B");
            return;
        }
        if(e.getMessage().equals("a")) {
            e.setCancelled(true);
            p.sendMessage("b");
            return;
        }
        Main.setPrefix(p);
        String Name;
        if(PlayerAPI.getTown(p)!=null) {
            if(PlayerAPI.getNation(p)!=null) {
                Name = "§c"+PlayerAPI.getNation(p)+" §8● §e"+PlayerAPI.getTown(p)+" §8● §f" + p.getName();
            } else {
                Name = "§e"+PlayerAPI.getTown(p)+" §8● §f" + p.getName();
            }
        } else {
            Name = "§eObdachtlos §8● §f" + p.getName();
        }
        String MSG;
        if(p.hasPermission("zyneon.team")) {
            MSG = e.getMessage().replace("&","§");
        } else {
            MSG = e.getMessage();
        }
        MSG = MSG.replace("%","%%");
        if(API.isStringBlocked(MSG)) {
            e.setCancelled(true);
            p.sendMessage("§4Achtung:§c Achte auf deine Wortwahl, oder es wird eine Strafe mit sich führen.");
            p.playSound(p.getLocation(),Sound.ENTITY_BAT_DEATH,100,100);
            p.playSound(p.getLocation(),Sound.ENTITY_BLAZE_DEATH,100,100);
            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
            Zyneon.getZyneonServer().sendErrorMessage("§4" + p.getName() + "§c hat versucht §4\"" + MSG + "§4\"§c zu schreiben, die Nachricht wurde aber blockiert!");
        } else {
            e.setFormat("%name%§8 » §7%msg%".replace("%name%", Name).replace("%msg%", MSG));
        }
    }
}
