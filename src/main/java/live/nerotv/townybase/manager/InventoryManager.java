package live.nerotv.townybase.manager;

import live.nerotv.townybase.api.API;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static Inventory jobSelectInventory(Player player) {
        TownyUser u = API.townyUsers.get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST,"§9Job auswählen§8...");
        inventory.addItem(ItemManager.woodcutterItem(u));
        inventory.addItem(ItemManager.minerItem(u));
        inventory.setItem(17,ItemManager.quitJobItem(u));
        return inventory;
    }
}