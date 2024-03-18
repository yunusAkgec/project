package com.example.project.repository;

import com.example.project.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<ShoppingCart, Long> {

    public Optional<ShoppingCart> findByUserId(String user_email);
}