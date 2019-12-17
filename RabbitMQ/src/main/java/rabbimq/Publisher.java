package rabbimq;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Publisher {

    public static void main(String[] args) {
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail jobDetail = JobBuilder.newJob()
                    .withIdentity("job", "job")
                    .ofType(SendMessageJob.class)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}