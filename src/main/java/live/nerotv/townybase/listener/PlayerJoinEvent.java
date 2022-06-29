package live.nerotv.townybase.listener;

import live.nerotv.townybase.api.API;
import live.nerotv.townybase.api.PlayerAPI;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Player p = e.getPlayer();
        API.townyUsers.remove(p.getUniqueId());
        API.townyUsers.put(p.getUniqueId(),new TownyUser(p.getUniqueId()));
        TownyUser u = API.townyUsers.get(p.getUniqueId());
        PlayerAPI.renewScoreboard(p);
        e.setJoinMessage(null);
        for(Player all:Bukkit.getOnlinePlayers()) {
            if(all.getUniqueId()!=p.getUniqueId()) {
                all.sendMessage("§8» §a"+p.getName());
            }
        }
        API.setTablist();
    }
}
