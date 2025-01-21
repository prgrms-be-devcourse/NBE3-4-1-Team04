package com.example.gc_coffee.batch.email.service;

import com.example.gc_coffee.domain.order.order.entity.Order;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableAsync
public class AsyncEmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendOrderConfirmation(Order order) {
        log.info("Attempting to send email to {}", order.getEmail());
        try {
            order.orderComplete(); // 상태 업데이트

            String email = order.getEmail();
            String subject = "Order Confirmation: " + order.getOrderNumber();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(setContext(order), true);
            javaMailSender.send(message);

            log.info("Email sent to {} for order {}", email, order.getOrderNumber());
        } catch (Exception e) {
            log.error("Failed to send email for order {}: {}", order.getOrderNumber(), e.getMessage(), e);
            throw new RuntimeException("Email sending failed", e);
        }
    }

    private String setContext(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = order.getOrderDate().format(formatter);

        Context context = new Context();
        context.setVariable("orderNumber", order.getOrderNumber());
        context.setVariable("orderDate", formattedDate);
        context.setVariable("address", order.getAddress());
        context.setVariable("totalPrice", order.getOrderPrice());
        context.setVariable("orderStatus", order.getOrderStatus().name());
        context.setVariable("items", order.getOrderItems());

        return templateEngine.process("email", context);
    }
}
