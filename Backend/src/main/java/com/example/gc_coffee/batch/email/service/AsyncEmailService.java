package com.example.gc_coffee.batch.email.service;

import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.global.exceptions.BusinessException;
import com.example.gc_coffee.global.exceptions.constant.ErrorCode;
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
    /* Async는 프록시 기반 동작이므로, self-invocation(자가 호출) 불가->다른 클래스로 분리*/
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendOrderConfirmation(Order order) {
        log.info("Trying to send Email to {}", order.getEmail());
        if(order.getOrderStatus() != OrderStatus.ORDERED ) {
            throw new BusinessException(ErrorCode.COMPLETED_ORDER);
        }
        try {
            order.orderComplete(); // 상태 완료로 변경
            String email = order.getEmail(); // 주문 객체에 고객 이메일
            String subject = "Order Confirmation: " + order.getOrderNumber();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(email); // 메일 수신자
            helper.setSubject(subject); // 메일 제목
            helper.setText(setContext(order), true); // HTML 지원 여부 (true)
            javaMailSender.send(message);
            log.info("Order confirmation email sent to {} for order number: {}", email, order.getOrderNumber());
        } catch (Exception e) {
            log.error("Failed to send order confirmation email for order number: {}", order.getOrderNumber(), e);
            throw new RuntimeException("Email sending failed", e);
        }
    }

    /**
     * Thymeleaf를 사용하여 이메일 본문 HTML 생성
     */
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
