package live.nerotv.townybase.listener;

import live.nerotv.townybase.api.API;
import live.nerotv.townybase.economy.jobs.Jobs;
import live.nerotv.townybase.manager.ItemManager;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventoryEvent implements Listener {

    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        TownyUser u = API.townyUsers.get(p.getUniqueId());
        if(e.getCurrentItem()!=null) {
            if(e.getCurrentItem().getItemMeta()!=null) {
                ItemStack item = e.getCurrentItem();
                ItemMeta itemMeta = item.getItemMeta();
                if(itemMeta.getDisplayName().equals(ItemManager.woodcutterItem(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setJob(Jobs.JobType.Holzfäller);
                    u.sendMessage("§7Du hast nun den Job §e"+u.getJob()+"§8!");
                } else if(itemMeta.getDisplayName().equals(ItemManager.minerItem(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setJob(Jobs.JobType.Minenarbeiter);
                    u.sendMessage("§7Du hast nun den Job §e"+u.getJob()+"§8!");
                } else if(itemMeta.getDisplayName().equals(ItemManager.quitJobItem(u).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setJob(Jobs.JobType.Arbeitslos);
                    u.sendErrorMessage("§cDu hast deinen Job gekündigt§8!");
                }
            }
        }
    }
}