package com.example.luckyshop.service;

import com.example.luckyshop.model.Category;
import com.example.luckyshop.model.Product;
import com.example.luckyshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


  public List<Product> productList(){
      return productRepository.findAll();
  }
  public Product save(Product product){
     return productRepository.save(product);
  }
  public Product getProductBid(Long id){
    return   productRepository.findById(id).orElseThrow();

  }
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public Product getProductById(Long id) {
     return productRepository.findById(id).orElseThrow();
    }
}
