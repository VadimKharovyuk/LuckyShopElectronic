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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.productList());
        return "product/list";
    }
    @GetMapping("/category/{categoryId}")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
        // Получение категории по ID
        Category category = categoryService.getCategoryById(categoryId);

        // Проверка, что категория существует
        if (category == null) {
            // Обработка случая, если категория не найдена
            return "error/404"; // Страница ошибки 404 или перенаправление на другую страницу
        }

        // Получение списка продуктов по категории
        List<Product> products = productService.getProductsByCategory(category);

        // Добавление данных в модель
        model.addAttribute("category", category);
        model.addAttribute("products", products);

        // Возврат имени шаблона для отображения продуктов
        return "product/category";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.categoryList()); // Добавляем список категорий
        return "product/product-form";
    }

    @PostMapping("/add")
    public String addProduct(Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/details";
    }

//    @GetMapping("/category/{categoryId}")
//    public String productsByCategory(@PathVariable Long categoryId, Model model) {
//        Category category = categoryService.getCategoryById(categoryId);
//        model.addAttribute("products", productService.getProductsByCategory(category));
//        return "product/category";
//    }
}
