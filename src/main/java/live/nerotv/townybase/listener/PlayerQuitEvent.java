package live.nerotv.townybase.listener;

import live.nerotv.townybase.api.API;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
        Player p = e.getPlayer();
        TownyUser u = API.townyUsers.get(p.getUniqueId());
        e.setQuitMessage(null);
        Bukkit.broadcastMessage("§8« §c"+p.getName());
        u.setBuild(false);
        API.townyUsers.remove(p.getUniqueId());
        u.destroy();
    }
}
