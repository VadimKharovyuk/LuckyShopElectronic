package com.example.luckyshop.controller;

import com.example.luckyshop.model.User;
import com.example.luckyshop.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final EntityManager entityManager;
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "Auth/password-change";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        if (userService.changePassword(username, currentPassword, newPassword)) {
            redirectAttributes.addFlashAttribute("successMessage", "Пароль успешно изменен!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось изменить пароль. Пожалуйста, попробуйте снова.");
        }

        return "redirect:/change-password";
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/block-user/{userId}")
    public String blockUser(@PathVariable Long userId) {
        userService.blockUser(userId);
        entityManager.clear(); // Очистка кэша сущностей Hibernate
        System.out.println("Пользователь  заблокирован с  id" + userId);
        return "redirect:/userList"; // Перенаправление на страницу администратора после блокировки
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/unblock-user/{userId}")
    public String unblockUser(@PathVariable Long userId) {
        userService.unblockUser(userId);
        entityManager.clear(); // Очистка кэша сущностей Hibernate
        return "redirect:/userList";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userList")
    public String findAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("user", userList);
        return "userList";
    }

}
