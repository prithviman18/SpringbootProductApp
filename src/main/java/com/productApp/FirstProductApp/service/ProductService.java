package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.dto.ProductDTO;
import com.productApp.FirstProductApp.entity.Product;
import com.productApp.FirstProductApp.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                null); // discount percentage
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

            // Save the updated product
            Product updatedProduct = productRepository.save(existingProduct);

            // Return updated ProductDTO
            ProductDTO productDTO = new ProductDTO(updatedProduct.getId(),
                    updatedProduct.getProductName(),
                    updatedProduct.getImageUrl(),
                    updatedProduct.getCostPrice(),
                    updatedProduct.getSellingPrice(),
                    updatedProduct.getMarkedPrice(),
                    null,  // profit
                    null); // discount percentage
            productDTO.setProfitAndDiscount();  // Calculate profit and discount percentage
            return productDTO;
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // Method to get all products
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO(product.getId(),
                            product.getProductName(),
                            product.getImageUrl(),
                            product.getCostPrice(),
                            product.getSellingPrice(),
                            product.getMarkedPrice(),
                            null,  // profit
                            null); // discount percentage
                    productDTO.setProfitAndDiscount();  // Calculate profit and discount percentage
                    return productDTO;
                })
                .toList();
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
                    null); // discount percentage
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
}
