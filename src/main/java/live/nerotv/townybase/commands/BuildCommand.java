package live.nerotv.townybase.commands;

import com.zyneonstudios.api.utils.Strings;
import live.nerotv.townybase.api.API;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            TownyUser u = API.townyUsers.get(p.getUniqueId());
            if(p.hasPermission("zyneon.team")) {
                u.setBuild(!u.hasBuild());
                u.sendMessage("§7Dein §aBuild§8-§aMode§7 steht nun auf§8: "+u.hasBuild());
            } else {
                u.sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }
}