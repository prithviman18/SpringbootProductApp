package com.productApp.FirstProductApp.repository;

import com.productApp.FirstProductApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
