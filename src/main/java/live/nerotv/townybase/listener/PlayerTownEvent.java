package live.nerotv.townybase.listener;

import com.palmergames.bukkit.towny.event.*;
import com.palmergames.bukkit.towny.event.town.TownRuinedEvent;
import com.palmergames.bukkit.towny.object.Resident;
import live.nerotv.townybase.api.PlayerAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.ArrayList;

public class PlayerTownEvent implements Listener {

    @EventHandler
    public void onTownJoin(PlayerEnterTownEvent e) {
        PlayerAPI.renewScoreboard(e.getPlayer());
    }

    @EventHandler
    public void onTownLeave(PlayerLeaveTownEvent e) {
        PlayerAPI.renewScoreboard(e.getPlayer());
    }

    @EventHandler
    public void onTownQuit(TownRemoveResidentEvent e) {
        Player p = e.getResident().getPlayer();
        PlayerAPI.renewScoreboard(p);
    }

    @EventHandler
    public void onTownEnter(TownAddResidentEvent e) {
        Player p = e.getResident().getPlayer();
        PlayerAPI.renewScoreboard(p);
    }

    @EventHandler
    public void onTownRuin(TownRuinedEvent e) {
        for(Resident r:e.getTown().getResidents()) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
    }

    ArrayList<Resident> residents = new ArrayList<>();
    @EventHandler
    public void preTownDelete(PreDeleteTownEvent e) {
        residents.addAll(e.getTown().getResidents());
    }

    @EventHandler
    public void onTownDelete(DeleteTownEvent e) {
        if(residents.isEmpty()) {
            return;
        }
        for(Resident r:residents) {
            Player p = r.getPlayer();
            PlayerAPI.renewScoreboard(p);
        }
        residents.clear();
    }
}
