package com.productApp.FirstProductApp.controller;

import com.productApp.FirstProductApp.entity.WishList;
import com.productApp.FirstProductApp.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    // Add product to wishlist
    @PostMapping("/add")
    public ResponseEntity<WishList> addToWishList(@RequestParam Long userId, @RequestParam Long productId){
        WishList wishList = wishlistService.addToWishList(userId, productId);
        return ResponseEntity.ok(wishList);
    }

    // Get all wishlist items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<WishList>> getWishlistForUser(@PathVariable Long userId){
        List<WishList> wishList = wishlistService.getWishlistForUser(userId);
        return ResponseEntity.ok(wishList);
    }

    // Remove product from wishlist
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWishList(@RequestParam Long userId, @RequestParam Long productId) {
        boolean isDeleted = wishlistService.removeFromWishList(userId, productId);

        if (isDeleted) {
            return ResponseEntity.ok("Product removed from wishlist successfully");
        } else {
            return ResponseEntity.status(404).body("Product not found in wishlist");
        }
    }
}
