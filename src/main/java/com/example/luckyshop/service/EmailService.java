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
    }
