package com.example.gc_coffee.batch.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusUpdateSchedule {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Scheduled(cron = "0 0 14 * * ?", zone = "Asia/Seoul") // 매일 오후 2시 실행
    public void runBatchJob() throws Exception {
        log.info("order update schedule start");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String startTime = LocalDateTime.now().minusDays(1).withHour(14).withMinute(0).withSecond(0).format(dateTimeFormatter);
        String endTime = LocalDateTime.now().withHour(14).withMinute(0).withSecond(0).format(dateTimeFormatter);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("startTime", startTime)
                .addString("endTime", endTime)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("orderStatusUpdateJob"), jobParameters);
    }
}

