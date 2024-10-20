package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.dto.ProductDTO;
import com.productApp.FirstProductApp.entity.Product;
import com.productApp.FirstProductApp.repository.ProductRepository;
import com.productApp.FirstProductApp.specification.ProductSpecification;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO createProduct(Product product) {
        // Save the product
        Product savedProduct = productRepository.save(product);

        // Create a ProductDTO to return with profit and discount calculations
        ProductDTO productDTO = new ProductDTO(savedProduct.getId(),
                savedProduct.getProductName(),
                savedProduct.getImageUrl(),
                savedProduct.getCostPrice(),
                savedProduct.getSellingPrice(),
                savedProduct.getMarkedPrice(),
                null,  // profit
                null,
                savedProduct.getCategory(),
                savedProduct.getBrand(),
                savedProduct.getGender());
        productDTO.setProfitAndDiscount();  // Calculate profit and discount percentage
        return productDTO;
    }

    // Method to update an existing product
    public ProductDTO updateProduct(Long id, Product productDetails) {
        // Find the existing product by ID
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            // Update the product fields
            existingProduct.setProductName(productDetails.getProductName());
            existingProduct.setImageUrl(productDetails.getImageUrl());
            existingProduct.setCostPrice(productDetails.getCostPrice());
            existingProduct.setSellingPrice(productDetails.getSellingPrice());
            existingProduct.setMarkedPrice(productDetails.getMarkedPrice());
            existingProduct.setCategory(productDetails.getCategory());
            existingProduct.setBrand(productDetails.getBrand());
            existingProduct.setGender(productDetails.getGender());

            // Save the updated product
            Product updatedProduct = productRepository.save(existingProduct);

            // Return updated ProductDTO
            ProductDTO productDTO = new ProductDTO(updatedProduct.getId(),
                    updatedProduct.getProductName(),
                    updatedProduct.getImageUrl(),
                    updatedProduct.getCostPrice(),
                    updatedProduct.getSellingPrice(),
                    updatedProduct.getMarkedPrice(),
                    null,
                    null,
                    updatedProduct.getCategory(),
                    updatedProduct.getBrand(),
                    updatedProduct.getGender());
            productDTO.setProfitAndDiscount();  // Calculate profit and discount percentage
            return productDTO;
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // Method to get all products with pagination
    public List<ProductDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        // Map the Page<Product> to a List<ProductDTO> and include profit and discount calculations
        return productPage.stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO(product.getId(),
                            product.getProductName(),
                            product.getImageUrl(),
                            product.getCostPrice(),
                            product.getSellingPrice(),
                            product.getMarkedPrice(),
                            null,  // profit
                            null,  // discount percentage
                            product.getCategory(),
                            product.getBrand(),
                            product.getGender());
                    productDTO.setProfitAndDiscount();  // Calculate profit and discount percentage
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    // Method to search products by name (no pagination here)
    public List<ProductDTO> getProductByName(String productName) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);

        return products.stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO(product.getId(),
                            product.getProductName(),
                            product.getImageUrl(),
                            product.getCostPrice(),
                            product.getSellingPrice(),
                            product.getMarkedPrice(),
                            null,  // profit
                            null, // discount percentage
                            product.getCategory(),
                            product.getBrand(),
                            product.getGender());
                    productDTO.setProfitAndDiscount();  // Calculate profit and discount percentage
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    // Method to get a product by its ID
    public ProductDTO getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            ProductDTO productDTO = new ProductDTO(product.getId(),
                    product.getProductName(),
                    product.getImageUrl(),
                    product.getCostPrice(),
                    product.getSellingPrice(),
                    product.getMarkedPrice(),
                    null,  // profit
                    null, // discount percentage
                    product.getCategory(),
                    product.getBrand(),
                    product.getGender());
            productDTO.setProfitAndDiscount();  // Calculate profit and discount percentage
            return productDTO;
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // Method to delete a product
    public boolean deleteProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // Method to get products based on filters with pagination
    public List<ProductDTO> getProductsWithFilters(Map<String, Object> filters, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        // Pass the filters to ProductSpecification
        ProductSpecification productSpecification = new ProductSpecification(filters);

        Page<Product> productPage = productRepository.findAll(productSpecification,pageable);
        // Pass the filters to ProductSpecification
        return productPage.stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO(product.getId(),
                            product.getProductName(),
                            product.getImageUrl(),
                            product.getCostPrice(),
                            product.getSellingPrice(),
                            product.getMarkedPrice(),
                            null,  // profit
                            null,
                            product.getCategory(),
                            product.getBrand(),
                            product.getGender());
                    productDTO.setProfitAndDiscount();
                    return productDTO;
                })
                .collect(Collectors.toList());

    }
}
