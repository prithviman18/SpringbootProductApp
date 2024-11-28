package com.productApp.FirstProductApp.repository;

import com.productApp.FirstProductApp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartReposiitory extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser_Id(Long id);

}
