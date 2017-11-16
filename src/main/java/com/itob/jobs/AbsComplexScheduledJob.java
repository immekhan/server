package com.itob.jobs;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/*If we want to persist the changes in JobDataMap, we will annotate our class by @PersistJobDataAfterExecution */
@PersistJobDataAfterExecution
/*if there is more than one trigger which are scheduling same job then to avoid race condition,
we have to annotate our job with @DisallowConcurrentExecution.*/
@DisallowConcurrentExecution
@Component
public class AbsComplexScheduledJob extends QuartzJobBean {

    @Autowired
    private AbsComplexJobService absComplexJobService;
    private static int count;

    @Override
    protected void executeInternal(JobExecutionContext jobContext)
            throws JobExecutionException {


        /*System.out.println("--------------------------------------------------------------------");
        System.out.println("MyJob start: " + jobContext.getFireTime());
        JobDetail jobDetail = jobContext.getJobDetail();

        AbsSampleComplexJob absSampleComplexJob = (AbsSampleComplexJob)jobDetail.getJobDataMap()
        .get("absSampleComplexJob");
        */
        absComplexJobService.execute();
//        System.out.println("Example name is: " + absSampleComplexJob.getSomeStr());

       /* System.out.println("MyJob end: " + jobContext.getJobRunTime() + ", key: " + jobDetail.getKey());

        System.out.println("MyJob next scheduled time: " + jobContext.getNextFireTime());

        System.out.println("--------------------------------------------------------------------");*/

        count++;
        System.out.println("Job count " + count);
    }

    public void setAbsComplexJobService(AbsComplexJobService absComplexJobService) {
        this.absComplexJobService = absComplexJobService;
    }
}
