package com.example.luckyshop.repository;

import com.example.luckyshop.model.Order;
import com.example.luckyshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
