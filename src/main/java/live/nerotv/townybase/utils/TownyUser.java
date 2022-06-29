package live.nerotv.townybase.utils;

import com.zyneonstudios.api.utils.user.User;
import live.nerotv.townybase.economy.jobs.Jobs;
import java.util.UUID;

public class TownyUser extends User {

    Jobs.JobType jobType;

    public TownyUser(UUID uuid) {
        super(uuid);
        this.jobType = Jobs.getJob(uuid);
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

    public void destroy() {
        this.jobType = null;
        disconnect();
    }
}