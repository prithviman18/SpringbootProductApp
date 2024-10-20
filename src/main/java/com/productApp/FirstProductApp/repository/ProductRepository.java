package com.productApp.FirstProductApp.repository;

import com.productApp.FirstProductApp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // Method to find products by product name, ignoring case
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    // Method to get products with pagination
    Page<Product> findAll(Pageable pageable);

}
