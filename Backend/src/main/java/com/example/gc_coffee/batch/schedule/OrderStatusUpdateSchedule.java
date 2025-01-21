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

    @Scheduled(cron = "0 0 14 * * ?", zone = "Asia/Seoul") // 매일 오후 2시에 실행
    public void runBatchJob() throws Exception {
        log.info("Order update schedule started");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        /**
         * 테스팅을 위해서 배치 시간 설정
         */
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (now.getHour() > 14) {
            // 오후 2시 이후인 경우: 오늘 오후 2시 ~ 내일 오후 2시
            startTime = now.withHour(14).withMinute(0).withSecond(0).withNano(0);
            endTime = startTime.plusDays(1);
        } else {
            // 오후 2시 이전인 경우: 어제 오후 2시 ~ 오늘 오후 2시
            endTime = now.withHour(14).withMinute(0).withSecond(0).withNano(0);
            startTime = endTime.minusDays(1);
        }
        /**
         * 오후 두시 배치 시간 설정(실제 코드)
         */
        // startTime = LocalDateTime.now().minusDays(1).withHour(14).withMinute(0).withSecond(0);
        // endTime = LocalDateTime.now().withHour(14).withMinute(0).withSecond(0);

        // 고유한 JobParameters 추가 (현재 시간을 포함)
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("startTime", startTime.format(dateTimeFormatter))
                .addString("endTime", endTime.format(dateTimeFormatter))
                .addString("runTime", now.format(dateTimeFormatter)) // 고유 파라미터 추가
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("orderStatusUpdateJob"), jobParameters);
    }
}


