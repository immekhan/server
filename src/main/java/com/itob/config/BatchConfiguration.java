package com.itob.config;

import com.itob.domain.DummyForm;
import com.itob.jobs.AbsBatchQuartzJobLauncher;
import com.itob.jobs.batch.AbsDummyFormItemProcessor;
import com.itob.jobs.batch.AbsDummyFormItemReader;
import com.itob.jobs.batch.AbsDummyFormJobCompletion;
import com.itob.service.dto.DummyFormDTO;
import com.itob.service.dto.DummyFormDetailDTO;
import org.quartz.JobDataMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/*For More information please see
* https://examples.javacodegeeks.com/enterprise-java/spring/batch/quartz-spring-batch-example/
* https://examples.javacodegeeks.com/enterprise-java/spring/batch/spring-batch-etl-job-example/
* */
@Configuration
@EnableBatchProcessing
@ComponentScan("com.itob.jobs")
public class BatchConfiguration {

    @Value("${job.cron.expression.dummy_form}")
    private String cronExpressionDummyForm;

    @Autowired public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;
    @Autowired public DataSource dataSource;
    @Autowired private JobRepository jobRepository;

    //Configuration Beans to trigger Batch as Quartz Job #Start
    @Bean
    SimpleJobLauncher jobLauncher() {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return launcher;
    }

    @Bean
    JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }

    @Bean
    JobExplorerFactoryBean jobExplorer() {
        final JobExplorerFactoryBean jobExplorer = new JobExplorerFactoryBean();
        jobExplorer.setDataSource(dataSource);
        return jobExplorer;
    }

    @Bean
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        final JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
        processor.setJobRegistry(jobRegistry());
        return processor;
    }
    //Configuration Beans to trigger Batch as Quartz Job #End

    //ITOBDummyForm Batch Job Details #Start
    @Bean
    public DummyFormDetailDTO dummyFormDetailDTO() {
        return new DummyFormDetailDTO();
    }

    @Bean
    public AbsDummyFormItemReader dummyFormEventReader() {
        return new AbsDummyFormItemReader();
    }

    @Bean
    public AbsDummyFormItemProcessor dummyFormEventProcessor() {
        return new AbsDummyFormItemProcessor();
    }

    /*@Bean
    public AbsDummyFormItemWriter dummyFormItemWriter() {
        return new AbsDummyFormItemWriter();
    }*/

    @Bean
    public JdbcBatchItemWriter<DummyForm> dummyFormItemWriter() {
        JdbcBatchItemWriter<DummyForm> writer = new JdbcBatchItemWriter<DummyForm>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<DummyForm>());
        writer.setSql("INSERT INTO ITOB_DUMMY_FORM (id_entity, first_name, last_name,email,image_file,IMAGE_FILE_CONTENT_TYPE,BINARY_FILE,BINARY_FILE_CONTENT_TYPE,CLOB_TEXT_FIELD) VALUES (HIBERNATE_SEQUENCE.nextval, :firstName, :lastName, :email, :imageFile, :imageFileContentType , :binaryFile, :binaryFileContentType, :clobTextField)");
        writer.setDataSource(dataSource);
        return writer;
    }

    // JobCompletionNotificationListener For DummyFormJob (File loader)
    @Bean
    public JobExecutionListener listener() {
        return new AbsDummyFormJobCompletion();
    }

    // Configure job step
    @Bean
    public Job dummyFormJob() {
        return jobBuilderFactory.get("dummy_form_job").incrementer(new RunIdIncrementer()).listener(listener())
            .flow(dummyFormDetailStep()).end().build();
    }

    @Bean
    public Step dummyFormDetailStep() {
        return stepBuilderFactory.get("Extract -> Transform -> Aggregate -> Load").allowStartIfComplete(true)
            .<DummyFormDTO, DummyForm> chunk(100000)
            .reader(dummyFormEventReader()).processor(dummyFormEventProcessor())
            .writer(dummyFormItemWriter())
            .build();
    }
    //ITOBDummyForm Batch Job Details #End

    //Configuring dummy_form_job to run in Quartz
    @Bean
    JobDetailFactoryBean jobDetailFactoryBeanForDummyForm() throws Exception {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(AbsBatchQuartzJobLauncher.class);
        bean.setName("cron_trigger");
        bean.setGroup("cron_group");
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "dummy_form_job");
        jobDataMap.put("jobLocator", jobRegistry());
        jobDataMap.put("jobLauncher", jobLauncher());
        bean.setJobDataMap(jobDataMap);
        return bean;
    }

    @Bean
    CronTriggerFactoryBean cronTriggerBeanForDummyForm() throws Exception {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(jobDetailFactoryBeanForDummyForm().getObject());
        bean.setCronExpression(cronExpressionDummyForm);
        return bean;
    }

    @Bean
    @Conditional(EnableSchedulerCondition.class)
    SchedulerFactoryBean schedulerFactoryBeanForBatch() throws Exception {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setExposeSchedulerInRepository(true); // for java melody
        factoryBean.setTriggers(cronTriggerBeanForDummyForm().getObject());
        factoryBean.setConfigLocation(new ClassPathResource("properties/quartz.properties"));
        return factoryBean;
    }
    //Configuring dummy_form_job to run in Quartz Ends

//  Enable the Quartz scheduler only when a certain property is true.
    private static class EnableSchedulerCondition implements Condition {

        @Override
        public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
            return context.getEnvironment() != null && "true".equalsIgnoreCase(context
                .getEnvironment().getProperty("job.enable.scheduling", "false"));
        }

    }

}
