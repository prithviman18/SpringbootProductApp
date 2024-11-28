package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.entity.Product;
import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.entity.WishList;
import com.productApp.FirstProductApp.repository.ProductRepository;
import com.productApp.FirstProductApp.repository.UserRepository;
import com.productApp.FirstProductApp.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    //Add product to wishlist
    public WishList addToWishList(Long userId, Long productId){
        // Check if the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product is already in the wishlist
        if (wishlistRepository.existsByUserIdAndProductId(user.getId(), product.getId())) {
            throw new RuntimeException("Product already exists in the wishlist");
        }

        // Create and save wishlist entry
        WishList wishList = new WishList(user, product);
        return wishlistRepository.save(wishList);
    }

    // Get all wishlist items for a user
    public List<WishList> getWishlistForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return wishlistRepository.findByUserId(userId);
    }

    @Transactional
    public boolean removeFromWishList(Long userId, Long productId) {
        // Fetch user and product or throw exceptions if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));

        // Delete the product from the wishlist
        int rowsDeleted = wishlistRepository.deleteByUserIdAndProductId(user.getId(), product.getId());

        // Return true if rows were deleted, otherwise false
        return rowsDeleted > 0;
    }

}
