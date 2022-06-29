package live.nerotv.townybase.economy.jobs.woodcutter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class WoodcutterBlockEvent implements Listener {

    @EventHandler
    public void onWood(BlockBreakEvent e) {
        Player p = e.getPlayer();
    }
}