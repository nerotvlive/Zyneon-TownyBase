package live.nerotv.townybase.economy;

import com.zyneonstudios.api.sql.SQLite;
import live.nerotv.townybase.Main;
import live.nerotv.townybase.api.PlayerAPI;
import org.bukkit.Bukkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class Ecosystem implements Economy {

    private static SQLite sql;

    public static void checkTable() {
        sql = new SQLite("plugins/TownyBase/eco.sql");
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ecodatabase (UUID VARCHAR(100),Eco DOUBLE(30,2))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkPayment(UUID uuid, double price) {
        if(Main.getEco().hasAccount(uuid)) {
            double balance = Main.getEco().getBalance(uuid).getBalance();
            if(balance-price >= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean createAccount(UUID uuid) {
        set(uuid,0);
        return true;
    }

    public boolean hasAccount(UUID uuid) {
        return hasAccountSQL(uuid);
    }

    public boolean hasAccountSQL(UUID uuid) {
        checkTable();
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(UUID uuid) {
        return deleteSQL(uuid);
    }

    public boolean deleteSQL(UUID uuid) {
        try {
            if (hasAccountSQL(uuid)) {
                PreparedStatement ps = sql.getConnection().prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(UUID uuid, double amount) {
        return set(uuid, getBalance(uuid).getBalance() - amount);
    }

    public boolean deposit(UUID uuid, double amount) {
        return set(uuid, getBalance(uuid).getBalance() + amount);
    }

    public boolean set(UUID uuid, double amount) {
        return setSQL(uuid,amount);
    }

    public boolean setSQL(UUID uuid, double amount) {
        String SID = uuid.toString();
        checkTable();
        if (amount < 0.0D) {
            return false;
        } else {
            try {
                if (hasAccountSQL(uuid)) {
                    PreparedStatement ps = sql.getConnection().prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                    ps.setString(1, SID);
                    ps.executeUpdate();
                }
                PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO ecodatabase (UUID,Eco) VALUES (?,?)");
                ps.setString(1, SID);
                ps.setDouble(2, amount);
                ps.executeUpdate();
                if(Bukkit.getPlayer(uuid)!=null) {
                    PlayerAPI.renewScoreboard(Bukkit.getPlayer(uuid));
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean has(UUID uuid, double amount) {
        return (getBalance(uuid).getBalance() >= amount);
    }

    public PlayerBalance getBalance(UUID uuid) {
        return getBalanceSQL(uuid);
    }

    public PlayerBalance getBalanceSQL(UUID uuid) {
        checkTable();
        double data;
        if (hasAccount(uuid)) {
            try {
                PreparedStatement ps = sql.getConnection().prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    data = rs.getDouble("Eco");
                } else {
                    data = 0.0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                data = 0.0;
            }
        } else {
            data = 0.0;
        }
        return new PlayerBalance(uuid, data);
    }

    public List<PlayerBalance> getPlayers() {
        return null;
    }
}