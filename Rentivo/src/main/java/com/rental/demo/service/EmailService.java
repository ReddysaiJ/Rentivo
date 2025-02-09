package com.rental.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.rental.demo.model.CarBooking;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;
    
    public String sendWelcomeEmail(String to, String username) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rentivo.team@gmail.com");
            helper.setTo(to);
            helper.setSubject("Welcome to Rentivo, " + username + "!");

            String htmlContent = new String(Files.readAllBytes(
                    Paths.get(new ClassPathResource("templates/mail/welcome.html").getURI())),
                    StandardCharsets.UTF_8
            );
            htmlContent = htmlContent.replace("{{USERNAME}}", username);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
    public String sendUpdateEmail(String to, String username) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rentivo.team@gmail.com");
            helper.setTo(to);
            helper.setSubject("Your Rentivo Profile Has Been Updated");

            String htmlContent = new String(Files.readAllBytes(
                    Paths.get(new ClassPathResource("templates/mail/updated.html").getURI())),
                    StandardCharsets.UTF_8
            );

            htmlContent = htmlContent.replace("{{username}}", username);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            return "Update email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
    
    public String sendNewBookingEmail(CarBooking booking) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setFrom("rentivo.team@gmail.com");
			helper.setTo(booking.getCustomer().getEmail());
			helper.setSubject("Your Rentivo Car Booking Confirmation");
			
			String htmlContent = new String(Files.readAllBytes(
				Paths.get(new ClassPathResource("templates/mail/booking.html").getURI())),
				StandardCharsets.UTF_8);
			
			htmlContent = htmlContent.replace("{{username}}", booking.getCustomer().getUsername())
									.replace("{{carModel}}", booking.getCar().getModel())
									.replace("{{carType}}", booking.getCar().getType())
									.replace("{{startDate}}", booking.getStartDate().toString())
									.replace("{{endDate}}", booking.getEndDate().toString())
									.replace("{{amountDue}}", String.valueOf(booking.getAmountDue()))
									.replace("{{paymentStatus}}", booking.getPaymentStatus());
			
			helper.setText(htmlContent, true);
			mailSender.send(message);
			return "Booking confirmation email sent successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send email: " + e.getMessage();
		}
    }
    
    public String sendBookingUpdateEmail(CarBooking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rentivo.team@gmail.com");
            helper.setTo(booking.getCustomer().getEmail());
            helper.setSubject("Your Rentivo Car Booking Has Been Updated");

            String htmlContent = new String(Files.readAllBytes(
                Paths.get(new ClassPathResource("templates/mail/bookingUpdate.html").getURI())),
                StandardCharsets.UTF_8
            );

            htmlContent = htmlContent.replace("{{username}}", booking.getCustomer().getUsername())
                                     .replace("{{carModel}}", booking.getCar().getModel())
                                     .replace("{{carType}}", booking.getCar().getType())
                                     .replace("{{startDate}}", booking.getStartDate().toString())
                                     .replace("{{endDate}}", booking.getEndDate().toString())
                                     .replace("{{amountDue}}", String.valueOf(booking.getAmountDue()))
                                     .replace("{{paymentStatus}}", booking.getPaymentStatus());

            helper.setText(htmlContent, true);
            mailSender.send(message);
            return "Booking update email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
    
    public String sendBookingCancellationEmail(CarBooking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rentivo.team@gmail.com");
            helper.setTo(booking.getCustomer().getEmail());
            helper.setSubject("Your Rentivo Car Booking Has Been Canceled");

            String htmlContent = new String(Files.readAllBytes(
                Paths.get(new ClassPathResource("templates/mail/bookingCancellation.html").getURI())),
                StandardCharsets.UTF_8
            );

            htmlContent = htmlContent.replace("{{username}}", booking.getCustomer().getUsername())
                                     .replace("{{carModel}}", booking.getCar().getModel())
                                     .replace("{{carType}}", booking.getCar().getType())
                                     .replace("{{startDate}}", booking.getStartDate().toString())
                                     .replace("{{endDate}}", booking.getEndDate().toString())
                                     .replace("{{amountDue}}", String.valueOf(1000))
                                     .replace("{{paymentStatus}}", booking.getPaymentStatus());

            helper.setText(htmlContent, true);
            mailSender.send(message);

            return "Booking cancellation email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
    public String sendPaymentFinishedEmail(CarBooking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rentivo.team@gmail.com");
            helper.setTo(booking.getCustomer().getEmail());
            helper.setSubject("Payment Successful for Your Car Booking");

            String htmlContent = new String(Files.readAllBytes(
                Paths.get(new ClassPathResource("templates/mail/paymentFinished.html").getURI())),
                StandardCharsets.UTF_8
            );

            htmlContent = htmlContent.replace("{{username}}", booking.getCustomer().getUsername())
                                     .replace("{{carModel}}", booking.getCar().getModel())
                                     .replace("{{carType}}", booking.getCar().getType())
                                     .replace("{{startDate}}", booking.getStartDate().toString())
                                     .replace("{{endDate}}", booking.getEndDate().toString())
                                     .replace("{{amountPaid}}", String.valueOf(booking.getAmountDue()))
                                     .replace("{{paymentStatus}}", "PAID");

            helper.setText(htmlContent, true);
            mailSender.send(message);

            return "Payment finished email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }


}
