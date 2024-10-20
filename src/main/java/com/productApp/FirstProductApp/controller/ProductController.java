package com.productApp.FirstProductApp.controller;

import com.productApp.FirstProductApp.dto.ProductDTO;
import com.productApp.FirstProductApp.entity.Product;
import com.productApp.FirstProductApp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody Product product) {
        ProductDTO createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<ProductDTO> products = productService.getAllProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{value}")
    public ResponseEntity<?> getProduct(@PathVariable String value) {
        try {
            Long id = Long.parseLong(value); // Try parsing the value as an ID
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(productService.getProductByName(value));
        }
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDetails);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        return new ResponseEntity<>(isDeleted,HttpStatus.NO_CONTENT);
    }

    //get products with filters
    @GetMapping("/filters")
    public ResponseEntity<List<ProductDTO>> getProductsWithFilters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Map<String, Object> filters = new HashMap<>();
        if(category!=null) filters.put("category",category);
        if (brand != null) filters.put("brand", brand);
        if (gender != null) filters.put("gender", gender);
        if (minPrice != null) filters.put("minPrice", minPrice);
        if (maxPrice != null) filters.put("maxPrice", maxPrice);
        if (productName != null) filters.put("productName", productName);

        // Fetch products using filters and pagination
        List<ProductDTO> products = productService.getProductsWithFilters(filters,page,size);
        return ResponseEntity.ok(products);
    }


}
