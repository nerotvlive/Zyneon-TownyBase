package live.nerotv.townybase.utils;

import com.zyneonstudios.api.utils.user.User;
import live.nerotv.townybase.economy.jobs.Job;
import java.util.UUID;

public class TownyUser extends User {

    Job.JobType jobType;

    public TownyUser(UUID uuid) {
        super(uuid);
        this.jobType = Job.getJob(uuid);
    }

    public boolean hasJob() {
        return Job.hasJob(getUUID());
    }

    public Job.JobType getJob() {
        return this.jobType;
    }

    public void setJob(Job.JobType jobType) {
        Job.setJob(getUUID(),jobType);
        this.jobType = jobType;
    }

    public void destroy() {
        this.jobType = null;
        disconnect();
    }
}