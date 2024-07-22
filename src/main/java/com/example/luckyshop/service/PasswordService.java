package com.example.luckyshop.service;
import com.example.luckyshop.model.User;
import com.example.luckyshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordService {

    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false; // Потребитель с данным email не найден
        }

        String newPassword = PasswordGenerator.generateNewPassword(8);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        sendNewPasswordEmail(email, newPassword);
        return true;
    }

    private void sendNewPasswordEmail(String to, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Password");
        message.setText("Your new password is: " + newPassword);
        emailSender.send(message);
    }
}
