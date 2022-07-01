package live.nerotv.townybase.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.paper.utils.user.User;
import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player p) {
            User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
            u.sendErrorMessage("§4Fehler§8: §c/balance §7[Spieler§f/set/add/remove§7] §f(Spieler) (Wert)");
        } else {
            Zyneon.getZyneonServer().sendErrorMessage("§4Fehler§8: §c/balance [set/add/remove/§fSpieler§c] §c[Spieler] [Wert]");
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
            if(args.length == 0) {
                u.sendMessage(Strings.prefix("Towny")+"§7Du hast momentan §e"+Main.getEco().getBalance(p.getUniqueId()).getBalance()+" Münzen§8!");
            } else {
                if(args.length == 1) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                    u.sendMessage(Strings.prefix("Towny")+"§e" + t.getName() + "§7 hat momentan §e" + Main.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
                } else if(args.length == 2) {
                    sendSyntax(p);
                } else if(args.length == 3) {
                    if(p.hasPermission("zyneon.team")) {
                        OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
                        double i;
                        if(API.isNumeric(args[2])) {
                            i = Integer.parseInt(args[2]);
                        } else {
                            i = 0;
                            u.sendErrorMessage("§cGebe eine korrekte Zahl an§8,§c "+i+"/"+args[2]+" ist falsch§8!");
                            return false;
                        }
                        if(args[0].equalsIgnoreCase("set")) {
                            Main.getEco().set(t.getUniqueId(),i);
                            Bukkit.dispatchCommand(p,"money "+t.getName());
                        } else if(args[0].equalsIgnoreCase("add")) {
                            Main.getEco().set(t.getUniqueId(),Main.getEco().getBalance(t.getUniqueId()).getBalance()+i);
                            Bukkit.dispatchCommand(p,"money "+t.getName());
                        } else if(args[0].equalsIgnoreCase("remove")) {
                            Main.getEco().set(t.getUniqueId(),Main.getEco().getBalance(t.getUniqueId()).getBalance()-i);
                            Bukkit.dispatchCommand(p,"money "+t.getName());
                        } else {
                            sendSyntax(p);
                        }
                    } else {
                        u.sendErrorMessage(Strings.noPerms());
                    }
                } else {
                    sendSyntax(p);
                }
            }
        } else {
            if(args.length == 1) {
                OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                s.sendMessage(Strings.prefix("Towny")+"§e"+t.getName()+"§7 hat momentan §e"+Main.getEco().getBalance(t.getUniqueId()).getBalance()+" Münzen§8!");
            } else if(args.length == 3) {
                if(s.hasPermission("zyneon.team")) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
                    double i;
                    if(API.isNumeric(args[2])) {
                        i = Integer.parseInt(args[2]);
                    } else {
                        i = 0;
                        s.sendMessage("§cGebe eine korrekte Zahl an§8,§c "+i+"/"+args[2]+" ist falsch§8!");
                        return false;
                    }
                    if(args[0].equalsIgnoreCase("set")) {
                        Main.getEco().set(t.getUniqueId(),i);
                        Bukkit.dispatchCommand(s,"money "+t.getName());
                    } else if(args[0].equalsIgnoreCase("add")) {
                        Main.getEco().set(t.getUniqueId(),Main.getEco().getBalance(t.getUniqueId()).getBalance()+i);
                        Bukkit.dispatchCommand(s,"money "+t.getName());
                    } else if(args[0].equalsIgnoreCase("remove")) {
                        Main.getEco().set(t.getUniqueId(),Main.getEco().getBalance(t.getUniqueId()).getBalance()-i);
                        Bukkit.dispatchCommand(s,"money "+t.getName());
                    } else {
                        sendSyntax(s);
                    }
                } else {
                    s.sendMessage(Strings.noPerms());
                }
            } else {
                sendSyntax(s);
            }
        }
        return false;
    }
}