package live.nerotv.townybase.commands;

import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Balance implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player) {
            API.sendErrorMessage(s,"§4Fehler: §c/balance §7[Spieler§f/set/add/remove§7] §f(Spieler) (Wert)");
        } else {
            API.sendErrorMessage(s,"§4Fehler: §c/balance [set/add/remove/§fSpieler§c] §c[Spieler] [Wert]");
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            if(args.length == 0) {
                API.sendMessage(p,"§7Du hast momentan §e"+Main.getEco().getBalance(p.getUniqueId()).getBalance()+" Münzen§8!");
            } else {
                if(args.length == 1) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                    API.sendMessage(p, "§e" + t.getName() + "§7 hat momentan §e" + Main.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
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
                            API.sendErrorMessage(p,"§cGebe eine korrekte Zahl an§8,§c "+i+"/"+args[2]+" ist falsch§8!");
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
                        API.sendErrorMessage(p,API.noPerms);
                    }
                } else {
                    sendSyntax(p);
                }
            }
        } else {
            if(args.length == 1) {
                OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                API.sendMessage(s,"§e"+t.getName()+"§7 hat momentan §e"+Main.getEco().getBalance(t.getUniqueId()).getBalance()+" Münzen§8!");
            } else if(args.length == 3) {
                if(s.hasPermission("zyneon.team")) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
                    double i;
                    if(API.isNumeric(args[2])) {
                        i = Integer.parseInt(args[2]);
                    } else {
                        i = 0;
                        API.sendErrorMessage(s,"§cGebe eine korrekte Zahl an§8,§c "+i+"/"+args[2]+" ist falsch§8!");
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
                    API.sendErrorMessage(s,API.noPerms);
                }
            } else {
                sendSyntax(s);
            }
        }
        return false;
    }
}