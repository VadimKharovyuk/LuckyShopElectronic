package com.example.luckyshop.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {


    private final JavaMailSender emailSender;

    public void sendPasswordReset(String recipientEmail, String newPassword) {
        // Создаем сообщение
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Сброс пароля");
        message.setText("Ваш новый пароль: " + newPassword);

        // Отправляем сообщение
        emailSender.send(message);
    }
    public void sendVerificationEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Activation");
        message.setText("Please activate your account using the following link: "
                + "http://localhost:3045/activate?token=" + token);

        emailSender.send(message);
    }
    }
