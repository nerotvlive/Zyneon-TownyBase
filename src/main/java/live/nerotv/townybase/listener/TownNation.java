package live.nerotv.townybase.listener;

import com.palmergames.bukkit.towny.event.NationAddTownEvent;
import com.palmergames.bukkit.towny.event.NationRemoveTownEvent;
import com.palmergames.bukkit.towny.event.nation.NationTownLeaveEvent;
import com.palmergames.bukkit.towny.object.Resident;
import live.nerotv.townybase.api.PlayerAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownNation implements Listener {

    @EventHandler
    public void onNationJoin(NationAddTownEvent e) {
        for(Resident r:e.getNation().getResidents()) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
        for(Resident r:e.getTown().getResidents()) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
    }

    @EventHandler
    public void onNationLeave(NationTownLeaveEvent e) {
        for(Resident r:e.getNation().getResidents()) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
        for(Resident r:e.getTown().getResidents()) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
    }

    @EventHandler
    public void onNationRemove(NationRemoveTownEvent e) {
        for(Resident r:e.getNation().getResidents()) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
        for(Resident r:e.getTown().getResidents()) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
    }
}
