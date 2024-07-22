package com.example.luckyshop.controller;

import com.example.luckyshop.model.Category;
import com.example.luckyshop.model.Product;
import com.example.luckyshop.service.CategoryService;
import com.example.luckyshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    private  final CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.productList());
        return "product/list";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductBid(id);
        model.addAttribute("product", product);
        return "product/details";
    }

    @GetMapping("/category/{categoryId}")
    public String productsByCategory(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        model.addAttribute("products", productService.getProductsByCategory(category));
        return "product/category";
    }
}
