package live.nerotv.townybase.economy.jobs;

import com.zyneonstudios.api.sql.SQLite;
import live.nerotv.townybase.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class Job {

    public static SQLite jobbase;

    public static void init() {
        jobbase = new SQLite("plugins/TownyBase/jobs.sql");
        checkTable();
    }

    public static void checkTable() {
        try {
            PreparedStatement ps = jobbase.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS users (UUID VARCHAR(100),JOBTYPE VARCHAR(100))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JobType getJob(Player player) {
        return getJob(player.getUniqueId());
    }

    public static boolean hasJob(Player player) {
        return hasJob(player.getUniqueId());
    }

    public static void setJob(Player player,JobType jobType) {
        setJob(player.getUniqueId(),jobType);
    }

    public static boolean hasJob(UUID uuid) {
        if(getJob(uuid)==null) {
            return false;
        } else {
            return !getJob(uuid).equals(JobType.Arbeitslos);
        }
    }

    public static JobType getJob(UUID uuid) {
        checkTable();
        String data;
        try {
            PreparedStatement ps = jobbase.getConnection().prepareStatement("SELECT JOBTYPE FROM users WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getString("JOBTYPE");
            } else {
                data = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            data = null;
        }
        if(data != null) {
            return JobType.valueOf(data);
        }
        return JobType.Arbeitslos;
    }

    public static void setJob(UUID uuid, JobType jobType) {
        checkTable();
        String SID = uuid.toString();

        try {
            if (hasJob(uuid)) {
                PreparedStatement ps = jobbase.getConnection().prepareStatement("DELETE FROM users WHERE UUID = ?");
                ps.setString(1, SID);
                ps.executeUpdate();
            }
            PreparedStatement ps = jobbase.getConnection().prepareStatement("INSERT INTO users (UUID,JOBTYPE) VALUES (?,?)");
            ps.setString(1, SID);
            ps.setString(2, jobType.toString());
            ps.executeUpdate();
            if (Bukkit.getPlayer(uuid) != null) {
                PlayerAPI.renewScoreboard(Objects.requireNonNull(Bukkit.getPlayer(uuid)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public enum JobType {
        Holzfäller,
        Minenarbeiter,
        Arbeitslos
    }
}