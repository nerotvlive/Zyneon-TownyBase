package live.nerotv.townybase.economy.jobs.woodcutter;

import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.economy.jobs.Jobs;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.util.concurrent.ThreadLocalRandom;

public class WoodcutterBlockEvent implements Listener {

    @EventHandler
    public void onWood(BlockBreakEvent e) {
        Player p = e.getPlayer();
        TownyUser u = API.townyUsers.get(p.getUniqueId());
        if(u.getJob().equals(Jobs.JobType.Holzfäller)) {
            Material type = e.getBlock().getType();
            if(type.equals(Material.OAK_LOG)||type.equals(Material.BIRCH_LOG)||type.equals(Material.SPRUCE_LOG)||type.equals(Material.JUNGLE_LOG)||type.equals(Material.ACACIA_LOG)||type.equals(Material.DARK_OAK_LOG)||type.equals(Material.MANGROVE_LOG)||type.equals(Material.MANGROVE_ROOTS)||type.equals(Material.MUDDY_MANGROVE_ROOTS)||type.equals(Material.WARPED_STEM)||type.equals(Material.CRIMSON_STEM)||type.toString().toUpperCase().contains("STRIPPED")) {
                int i = ThreadLocalRandom.current().nextInt(1,2);
                Main.getEco().set(p.getUniqueId(),Main.getEco().getBalance(p.getUniqueId()).getBalance()+i);
                p.sendActionBar("§e+"+i+"§e Münzen §8(§7"+Main.getEco().getBalance(p.getUniqueId()).getBalance()+"§8)");
            }
        }
    }
}