package com.example.luckyshop.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {
    @GetMapping("/admin-dashboard")
    public String adminPage(){
        return "admin/admin-dashboard";
    }
}
