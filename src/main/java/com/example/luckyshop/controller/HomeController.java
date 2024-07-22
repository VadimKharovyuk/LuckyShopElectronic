package com.example.luckyshop.controller;

import com.example.luckyshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("category", categoryService.categoryList());
        return "homePage";
    }
}
