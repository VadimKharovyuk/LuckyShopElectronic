package com.example.luckyshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @Column(name = "blocked")
    private boolean blocked = false;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        ADMIN ,USER

    }
}
