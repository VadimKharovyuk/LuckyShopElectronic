package com.example.luckyshop.controller;

import com.example.luckyshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    @GetMapping("admin-dashboard")
    public String adminPage(){
        return "admin/admin-dashboard";
    }
    @GetMapping("/userList")
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        return "admin/userList";

    }
    @PostMapping("/blockUser")
    public String blockUser(@RequestParam("userId") Long userId) {
        userService.blockUser(userId);
        return "redirect:/admin/userList";
    }

    @PostMapping("/unblockUser")
    public String unblockUser(@RequestParam("userId") Long userId) {
        userService.unblockUser(userId);
        return "redirect:/admin/userList";
    }
}
