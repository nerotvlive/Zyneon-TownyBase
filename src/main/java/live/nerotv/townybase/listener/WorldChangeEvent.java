package live.nerotv.townybase.listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.utils.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WorldChangeEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        if(TownyAPI.getInstance().isWilderness(e.getBlock().getLocation())) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
            u.sendErrorMessage("Das darfst du in der Wildnis nicht tun§8!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockPlaceEvent e) {
        if(TownyAPI.getInstance().isWilderness(e.getBlock().getLocation())) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
            u.sendErrorMessage("Das darfst du in der Wildnis nicht tun§8!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock() != null) {
                if (TownyAPI.getInstance().isWilderness(e.getClickedBlock().getLocation())) {
                    e.setCancelled(true);
                    Player p = e.getPlayer();
                    User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
                    u.sendErrorMessage("Das darfst du in der Wildnis nicht tun§8!");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplosion(ExplosionPrimeEvent e) {
        e.setCancelled(true);
    }
}
