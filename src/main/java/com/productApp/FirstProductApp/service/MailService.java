package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    UserRepository userRepository;

    // Method to send email to ROLE_CUSTOMER
    public void sendEmailToCustomers(String subject, String text){
        List<User> customers = userRepository.findByRoles_Name("ROLE_CUSTOMER");

        for(User customer:customers){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(customer.getEmail());
            message.setSubject(subject);
            message.setText(text);

            // Send the email
            mailSender.send(message);
        }
    }
    //Welcome mail
    public void sendWelcomeEmail(String toEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to Prithvi's Online Shop!");
        message.setText("Hello " + userName + ",\n\nWelcome to Prithvi's Online Shop! We're glad to have you with us.\n\nFeel free to explore our products and enjoy your shopping experience.");

        mailSender.send(message);
    }

    // Send password reset email
    public void sendPasswordResetEmail(String email, String resetLink) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText("<p>We received a request to reset your password. Please click the link below to reset your password:</p>" +
                    "<a href=\"" + resetLink + "\">Reset Password</a>", true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email.");
        }
    }

}
