package live.nerotv.townybase.listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void onInteract(org.bukkit.event.player.PlayerInteractEvent e) {
        Player p = e.getPlayer();
        TownyUser u = API.townyUsers.get(p.getUniqueId());
        if(u.hasBuild()) {
            return;
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(e.getClickedBlock()!=null) {
                if(TownyAPI.getInstance().getTownBlock(e.getClickedBlock().getLocation())!=null) {
                    TownBlock townblock = TownyAPI.getInstance().getTownBlock(e.getClickedBlock().getLocation());
                    try {
                        if (townblock.getTownBlockOwner() != null) {
                            Resident owner = TownyUniverse.getInstance().getResident(TownyAPI.getInstance().getTownBlock(e.getClickedBlock().getLocation()).getTownBlockOwner().getName());
                            if (!p.getName().equals(owner.getName())) {
                                e.setCancelled(true);
                            } else if (!townblock.getTrustedResidents().contains(u.getResident())) {
                                e.setCancelled(true);
                            }
                        }
                    } catch (NullPointerException ignore) {}
                }
            }
        }
    }
}