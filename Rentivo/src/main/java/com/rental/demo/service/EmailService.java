package com.rental.demo.service;

import com.rental.demo.model.CarBooking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final AmqpTemplate rabbitTemplate;

    private static final String EXCHANGE = "rentivo.email.exchange";
    private static final String ROUTING_KEY_PREFIX = "rentivo.email.";

    public EmailService(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sendWelcomeEmail(String to, String username) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_PREFIX + "welcome",
                new WelcomeEmailEvent(to, username));
        return "Enqueued welcome email";
    }

    public String sendUpdateEmail(String to, String username) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_PREFIX + "update",
                new UpdateEmailEvent(to, username));
        return "Enqueued update email";
    }

    public String sendNewBookingEmail(CarBooking booking) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_PREFIX + "newBooking",
                new BookingEmailEvent(booking));
        return "Enqueued booking confirmation email";
    }

    public String sendBookingUpdateEmail(CarBooking booking) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_PREFIX + "bookingUpdate",
                new BookingUpdateEmailEvent(booking));
        return "Enqueued booking update email";
    }

    public String sendBookingCancellationEmail(CarBooking booking) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_PREFIX + "cancellation",
                new BookingCancellationEmailEvent(booking));
        return "Enqueued booking cancellation email";
    }

    public String sendPaymentFinishedEmail(CarBooking booking) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_PREFIX + "paymentFinished",
                new PaymentFinishedEmailEvent(booking));
        return "Enqueued payment finished email";
    }

    public void sendOTPEmail(String toEmail, String subject, String body) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_PREFIX + "otp",
                new OtpEmailEvent(toEmail, subject, body));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WelcomeEmailEvent {
        private String to;
        private String username;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateEmailEvent {
        private String to;
        private String username;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingEmailEvent {
        private CarBooking booking;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingUpdateEmailEvent {
        private CarBooking booking;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingCancellationEmailEvent {
        private CarBooking booking;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentFinishedEmailEvent {
        private CarBooking booking;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtpEmailEvent {
        private String toEmail;
        private String subject;
        private String body;
    }
}
