package com.example.luckyshop.repository;

import com.example.luckyshop.model.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken,Long> {
    ActivationToken findByToken(String token);
}
