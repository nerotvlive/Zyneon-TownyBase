package live.nerotv.townybase.manager;

import live.nerotv.townybase.Main;
import live.nerotv.townybase.economy.jobs.Job;
import live.nerotv.townybase.utils.Glow;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

public class ItemManager {

    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List) Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    @NotNull
    public static ItemStack getGlowStack(ItemStack RI) {
        ItemMeta RM = RI.getItemMeta();
        NamespacedKey key = new NamespacedKey(Main.getInstance(),Main.getInstance().getDescription().getName());
        Glow glow = new Glow(key);
        RM.addEnchant(glow,1,true);
        RI.setItemMeta(RM);
        return RI;
    }

    public static ItemStack woodcutterItem(TownyUser u) {
        ItemStack item;
        if(u.getJob().equals(Job.JobType.Holzfäller)) {
            item = getGlowStack(createGuiItem(Material.OAK_LOG,"§bHolzfäller", "§0","§8» §aDu hast diesen Job§8","§0","§7Erhalte Geld für das Fällen von Bäumen.","§0"));
        } else {
            item = createGuiItem(Material.OAK_LOG,"§bHolzfäller", "§0","§7Erhalte Geld für das Fällen von Bäumen.","§0");
        }
        return item;
    }

    public static ItemStack minerItem(TownyUser u) {
        ItemStack item;
        if(u.getJob().equals(Job.JobType.Minenarbeiter)) {
            item = getGlowStack(createGuiItem(Material.COAL_ORE,"§bMinenarbeiter", "§0","§8» §aDu hast diesen Job§8","§0","§7Erhalte Geld für das Abbauen von Erzen und Steinen.","§0"));
        } else {
            item = createGuiItem(Material.OAK_LOG,"§bMinenarbeiter", "§0","§7Erhalte Geld für das Abbauen von Erzen und Steinen.","§0");
        }
        return item;
    }

    public static ItemStack quitJobItem(TownyUser u) {
        ItemStack item = createGuiItem(Material.BARRIER,"§4Job kündigen","§0","§7Dies macht dich Arbeitslos§8.","§0");
        return item;
    }
}