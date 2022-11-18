package com.flightright.scheduler;

import com.flightright.process.VisitsCSVFileReader;
import com.flightright.process.VisitCounterJobListener;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class VisitsCounterScheduler {
    private final VisitsCSVFileReader visitsCsvFileReader;
    private final JobBuilderFactory jobBuilder;
    private final VisitCounterJobListener visitCounterJobListener;

    private final JobLauncher jobLauncher;

    @Scheduled(cron = "${cron.job.run}")
    public void run() {
        System.out.println("Start cron job ...");
        Job job = jobBuilder.get("job-1")
                .flow(visitsCsvFileReader.step())
                .end()
                .listener(visitCounterJobListener)
                .build();
        try {
            jobLauncher.run(job, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }
}
