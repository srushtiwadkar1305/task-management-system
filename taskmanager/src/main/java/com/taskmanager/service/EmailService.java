package com.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendResetPasswordMail(String to, String token) {

        String resetPasswordUrl = String.format("http://localhost:8080/api/auth/reset_password?token=%s", token);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("MarketMatrix - Reset Password");
        message.setText("Click the link to reset password:\n" + resetPasswordUrl);
        javaMailSender.send(message);

    }

}
