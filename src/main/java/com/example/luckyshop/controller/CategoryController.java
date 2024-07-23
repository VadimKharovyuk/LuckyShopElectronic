package com.example.luckyshop.controller;

import com.example.luckyshop.model.Category;
import com.example.luckyshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.categoryList());
        return "category/list";
    }

    @GetMapping("/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }

    @PostMapping("/new")
    public String addCategory(Category category) {
        categoryService.save(category);
        return "redirect:/category/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryBuId(id);
        return "redirect:/category/all";
    }
}
