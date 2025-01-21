package com.example.gc_coffee.batch.config;

import com.example.gc_coffee.batch.email.service.AsyncEmailService;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderStatusUpdateBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final OrderRepository orderRepository;
    private final AsyncEmailService asyncEmailService;

    @Bean
    public Job orderStatusUpdateJob(Step orderStatusUpdateStep) {
        log.info("Order Status Update Job Initialized");

        return new JobBuilder("orderStatusUpdateJob", jobRepository)
                .listener(jobExecutionListener())
                .start(orderStatusUpdateStep)
                .build();
    }

    @Bean
    public Step orderStatusUpdateStep() {
        return new StepBuilder("orderStatusUpdateStep", jobRepository)
                .<Order, Order>chunk(100, platformTransactionManager)
                .reader(orderReader())
                .processor(orderUpdateProcessor())
                .writer(orderUpdateWriter(orderRepository))
                .build();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("Job [{}] starting...", jobExecution.getJobInstance().getJobName());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("Job [{}] completed with status: {}",
                        jobExecution.getJobInstance().getJobName(),
                        jobExecution.getStatus());
            }
        };
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Order> orderReader() {
        LocalDateTime now = LocalDateTime.now();

        // 현재 시간이 오후 2시 기준으로 이전인지 이후인지에 따라 처리
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (now.getHour() >= 14) {
            // 오후 2시 이후인 경우: 오늘 오후 2시 ~ 내일 오후 2시
            startTime = now.withHour(14).withMinute(0).withSecond(0).withNano(0);
            endTime = startTime.plusDays(1);
        } else {
            // 오후 2시 이전인 경우: 어제 오후 2시 ~ 오늘 오후 2시
            endTime = now.withHour(14).withMinute(0).withSecond(0).withNano(0);
            startTime = endTime.minusDays(1);
        }

        log.info("Reading orders between {} and {}", startTime, endTime);

        return new RepositoryItemReaderBuilder<Order>()
                .name("orderReader")
                .pageSize(10)
                .methodName("findByOrderTimeBetweenAndOrderStatus")
                .arguments(startTime, endTime, OrderStatus.ORDERED)
                .repository(orderRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }


    @Bean
    public ItemProcessor<Order, Order> orderUpdateProcessor() {
        return order -> {
            log.info("Processing order: {}", order.getOrderNumber());
            asyncEmailService.sendOrderConfirmation(order); // 이메일 발송
            return order;
        };
    }

    @Bean
    public RepositoryItemWriter<Order> orderUpdateWriter(OrderRepository orderRepository) {
        return new RepositoryItemWriterBuilder<Order>()
                .repository(orderRepository)
                .methodName("save")
                .build();
    }
}
