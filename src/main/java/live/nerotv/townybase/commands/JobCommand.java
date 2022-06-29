package live.nerotv.townybase.commands;

import live.nerotv.townybase.api.API;
import live.nerotv.townybase.utils.TownyUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JobCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("Job")) {
            if(s instanceof Player p) {
                TownyUser u = API.townyUsers.get(p.getUniqueId());
                if(args.length == 0) {
                    if(u.hasJob()) {
                        u.sendMessage("§7Du hast zurzeit den Beruf §e"+u.getJob()+"§8!");
                    } else {
                        u.sendErrorMessage("§cDu bist arbeitslos§8! §cMache §4/job select§c um einen Job zu wählen§8.");
                    }
                } else {
                    if(args[0].equalsIgnoreCase("select")) {

                    } else {
                        u.sendErrorMessage("Meintest du §4/job select§8?");
                    }
                }
            } else {

            }
        }
        return false;
    }
}