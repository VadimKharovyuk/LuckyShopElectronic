package com.example.luckyshop.repository;

import com.example.luckyshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
