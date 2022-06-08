package live.nerotv.townybase.commands;

import live.nerotv.townybase.api.API;
import live.nerotv.townybase.api.PlayerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Check implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            API.sendMessage(p,"Du bist in der Stadt §e"+PlayerAPI.getTown(p)+"§7 aus der Nation §a"+PlayerAPI.getNation(p)+"§8!");
        }
        return false;
    }
}