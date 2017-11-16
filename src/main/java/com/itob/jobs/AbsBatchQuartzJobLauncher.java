package com.itob.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Map;
/*If we want to persist the changes in JobDataMap, we will annotate our class by @PersistJobDataAfterExecution */
@PersistJobDataAfterExecution
/*if there is more than one trigger which are scheduling same job then to avoid race condition,
we have to annotate our job with @DisallowConcurrentExecution.*/
@DisallowConcurrentExecution
@Component
public class AbsBatchQuartzJobLauncher extends QuartzJobBean {

    private static final Logger LOG = LoggerFactory.getLogger(AbsBatchQuartzJobLauncher.class);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobLocator jobLocator;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map<String, Object> jobMap = context.getMergedJobDataMap();
        String jobName = (String) jobMap.get("jobName");
        try {
            JobExecution execution = jobLauncher.run(jobLocator.getJob(jobName),
                new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters());
            LOG.info("Job Status : " + execution.getStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        LOG.info("Done");

    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public void setJobLocator(JobLocator jobLocator) {
        this.jobLocator = jobLocator;
    }

}
