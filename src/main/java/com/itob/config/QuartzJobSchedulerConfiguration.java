package com.itob.config;

import com.itob.jobs.AbsComplexJobService;
import com.itob.jobs.AbsComplexScheduledJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.*;

import java.util.HashMap;
import java.util.Map;

//todo uncomment these to run this configuration and also uncomment the entry in WebConfigurationInitializer
@Configuration
@ComponentScan("com.itob.jobs")
public class QuartzJobSchedulerConfiguration {
    //http://www.concretepage.com/spring-4/spring-4-quartz-2-scheduler-integration-annotation-example-using-javaconfig
    //todo make common function for simple jobs to set attributes
    //todo make common function for complex jobs to set attributes

    @Value("${job.cron.expression.sample}")
    private String cronExpSample;

    @Bean //this is simple job1
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("jobone");
        obj.setTargetMethod("myTask");
        return obj;
    }
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(){
        //This trigger will schedule the job after 3 seconds and repeat after every 30 seconds for 3+1 times.
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setRepeatInterval(30000);
        stFactory.setRepeatCount(3);//todo repeat counter remove or use
        return stFactory;
    }

    @Bean //this is simple job2
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean2() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("jobtwo");
        obj.setTargetMethod("myTask");
        return obj;
    }
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean2(){
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(methodInvokingJobDetailFactoryBean2().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setRepeatInterval(30000);
        stFactory.setRepeatCount(3);//todo repeat counter remove or use
        return stFactory;
    }
    @Bean//this is complex job1
    public JobDetailFactoryBean jobDetailFactoryBean(AbsComplexJobService absComplexJobService){
        /*To pass the parameter to job by JavaConfig, we can have setter method and
          the property should be configured with setJobDataAsMap() in JobDetailFactoryBean
          configuration in JavaConfig*/
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(AbsComplexScheduledJob.class);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("absComplexJobService", absComplexJobService);
        factory.setJobDataAsMap(map);
        factory.setDurability(true);
       /* factory.setGroup("SettlementGroup");
        factory.setName("Day End");*/
        return factory;
    }
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(AbsComplexJobService absComplexJobService){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailFactoryBean(absComplexJobService).getObject());
        stFactory.setCronExpression(cronExpSample);
        stFactory.setStartDelay(3000);
        /*
        stFactory.setGroup("SettlementGroupTrigger");
        stFactory.setName("SettlementGroupTriggerName");
        */
        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(AbsComplexJobService absComplexJobService) {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setConfigLocation(new ClassPathResource("properties/quartz.properties"));
        scheduler.setTriggers(simpleTriggerFactoryBean().getObject(),
            simpleTriggerFactoryBean2().getObject(),
            cronTriggerFactoryBean(absComplexJobService).getObject()
        );
        return scheduler;
    }
}
