package com.example.luckyshop.controller;

import com.example.luckyshop.model.User;
import com.example.luckyshop.repository.UserRepository;
import com.example.luckyshop.service.EmailService;
import com.example.luckyshop.service.PasswordGenerator;
import com.example.luckyshop.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class PasswordResetController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordService passwordService;



    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "Auth/forgot-password";
    }
    @PostMapping("/forgot-password")
    public String resetPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с таким email не найден");
            return "redirect:/forgot-password";
        }

        // Генерируем новый пароль
        String newPassword = PasswordGenerator.generateNewPassword(8); // Пример: генерация пароля длиной 5 символов

        // Сохраняем новый пароль в базе данных (с использованием шифрования)
        user.setPassword(passwordService.encodePassword(newPassword));
        userRepository.save(user);

        // Отправляем пользователю новый пароль по email
        emailService.sendPasswordReset(email, newPassword);
        redirectAttributes.addFlashAttribute("successMessage", "Новый пароль отправлен на указанный email");

        return "redirect:/forgot-password";
    }

}
