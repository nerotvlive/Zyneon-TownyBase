package live.nerotv.townybase.economy.jobs.miner;

import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.economy.jobs.Jobs;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class MinerBlockEvent implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        Player p = e.getPlayer();
        TownyUser u = API.townyUsers.get(p.getUniqueId());
        if (u.getJob().equals(Jobs.JobType.Minenarbeiter)) {
            Material type = e.getBlock().getType();
            if (type == Material.STONE || type == Material.DEEPSLATE || type == Material.ANDESITE || type == Material.GRANITE || type == Material.DIORITE) {
                if (e.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("PICKAXE")) {
                    ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                    if (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                        int i = ThreadLocalRandom.current().nextInt(1, 3);
                        Main.getEco().set(p.getUniqueId(), Main.getEco().getBalance(p.getUniqueId()).getBalance() - i);
                        p.sendActionBar("§c-" + i + "§c Münzen §8(§7" + Main.getEco().getBalance(p.getUniqueId()).getBalance() + "§8)");
                        return;
                    }
                    int iI = ThreadLocalRandom.current().nextInt(0, 6);
                    if (iI == 5) {
                        Main.getEco().set(p.getUniqueId(), Main.getEco().getBalance(p.getUniqueId()).getBalance() + 1);
                        p.sendActionBar("§e+" + 1 + "§e Münzen §8(§7" + Main.getEco().getBalance(p.getUniqueId()).getBalance() + "§8)");
                    }
                }
            } else if (type.toString().toUpperCase().contains("_ORE")) {
                if (e.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("PICKAXE")) {
                    ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                    if (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                        int i = ThreadLocalRandom.current().nextInt(1, 3);
                        Main.getEco().set(p.getUniqueId(), Main.getEco().getBalance(p.getUniqueId()).getBalance() - i);
                        p.sendActionBar("§c-" + i + "§c Münzen §8(§7" + Main.getEco().getBalance(p.getUniqueId()).getBalance() + "§8)");
                        return;
                    }
                    int ii = ThreadLocalRandom.current().nextInt(4, 8);
                    Main.getEco().set(p.getUniqueId(), Main.getEco().getBalance(p.getUniqueId()).getBalance() + ii);
                    p.sendActionBar("§e+" + ii + "§e Münzen §8(§7" + Main.getEco().getBalance(p.getUniqueId()).getBalance() + "§8)");
                }
            }
        }
    }
}