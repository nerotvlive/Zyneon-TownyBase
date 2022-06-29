package live.nerotv.townybase.commands;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.utils.user.User;
import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
            if(args.length == 2) {
                if(API.isNumeric(args[1])) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                    double money = Main.getEco().getBalance(p.getUniqueId()).getBalance();
                    double payAmount = Double.parseDouble(args[1]);
                    if(money<payAmount) {
                        u.sendErrorMessage("§cDazu hast du nicht genug Geld§8!");
                    } else {
                        Main.getEco().set(p.getUniqueId(),money-payAmount);
                        Main.getEco().set(t.getUniqueId(),Main.getEco().getBalance(t.getUniqueId()).getBalance()+payAmount);
                        if(Bukkit.getPlayer(t.getUniqueId())!=null) {
                            User tU = Zyneon.getAPI().getOnlineUser(t.getUniqueId());
                            tU.sendMessage("§7Du hast von §a"+p.getName()+"§e "+payAmount+" Münzen §7erhalten§8!");
                        }
                        u.sendMessage("§7Du hast §a"+t.getName()+"§e "+payAmount+" Münzen §7gezahlt§8!");
                    }
                } else {
                    u.sendErrorMessage("§cGib eine korrekte Zahl an§8!");
                }
            } else {
                u.sendErrorMessage("§4Fehler§8: §c/pay [Spieler/in] [Wert]");
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }
}
