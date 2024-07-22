package com.example.luckyshop.service;

import com.example.luckyshop.model.Category;
import com.example.luckyshop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

     public Category save(Category category){
         return categoryRepository.save(category);
     }

     public List<Category> categoryList (){
         return categoryRepository.findAll();
     }

     public Category getCategoryById(Long id){
      return    categoryRepository.findById(id).orElseThrow();
     }
}

