package live.nerotv.townybase.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.zyneonstudios.api.paper.utils.user.User;
import live.nerotv.townybase.economy.jobs.Jobs;
import org.bukkit.Bukkit;

import java.util.UUID;

public class TownyUser extends User {

    private Jobs.JobType jobType;
    private Resident resident;
    private boolean build;

    public TownyUser(UUID uuid) {
        super(uuid);
        this.jobType = Jobs.getJob(uuid);
        this.resident = TownyAPI.getInstance().getResident(uuid);
        this.build = false;
    }

    public Resident getResident() {
        return this.resident;
    }

    public boolean hasBuild() {
        return this.build;
    }

    public boolean hasJob() {
        return Jobs.hasJob(getUUID());
    }

    public Jobs.JobType getJob() {
        return this.jobType;
    }

    public void setJob(Jobs.JobType jobType) {
        Jobs.setJob(getUUID(),jobType);
        this.jobType = jobType;
    }

    public void setBuild(boolean build) {
        if(build) {
            if (getPlayer() != null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp u " + getPlayer().getName() + " permission set towny.*");
            }
        } else {
            if (getPlayer() != null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp u " + getPlayer().getName() + " permission unset towny.*");
            }
        }
        this.build = build;
    }

    public void destroy() {
        this.jobType = null;
        this.resident = null;
        this.build = false;
        disconnect();
    }
}