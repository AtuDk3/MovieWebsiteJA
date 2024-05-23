package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class EmailService {

    @Value("${app.base.url}")
    private String baseUrl;

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user) {
        String token = UUID.randomUUID().toString();
        String verificationUrl = baseUrl + "/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Click the link to verify your email: " + verificationUrl);

        mailSender.send(message);

        // Lưu token vào cơ sở dữ liệu hoặc bộ nhớ đệm
        // userService.saveVerificationToken(user, token);
    }
}
