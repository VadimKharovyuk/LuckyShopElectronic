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
    public String handleForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        boolean reset = passwordService.resetPassword(email);
        if (reset) {
            redirectAttributes.addFlashAttribute("message", "A new password has been sent to your email.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No account found with that email.");
        }
        return "redirect:/forgot-password";
    }

}
