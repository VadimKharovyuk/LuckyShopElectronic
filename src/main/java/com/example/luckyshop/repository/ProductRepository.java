package com.example.luckyshop.repository;

import com.example.luckyshop.model.Category;
import com.example.luckyshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product>findByCategory(Category category);
}
