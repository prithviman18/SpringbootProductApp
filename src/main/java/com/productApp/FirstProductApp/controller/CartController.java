package com.productApp.FirstProductApp.controller;

import com.productApp.FirstProductApp.dto.CartDTO;
import com.productApp.FirstProductApp.dto.CartItemDTO;
import com.productApp.FirstProductApp.entity.Cart;
import com.productApp.FirstProductApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
            ){
            try {
                cartService.addToCart(userId,productId,quantity);
                return ResponseEntity.ok("Product added to cart Successfully");
            } catch (Exception e){
                return ResponseEntity.badRequest().body("Failed to add product to cart: " + e.getMessage());
            }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable long userId){
        Cart cart = cartService.getCart(userId);  // Fetch the cart by userId

        // Convert Cart to CartDTO
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setUserName(cart.getUser().getUsername());

        // Convert CartItems to CartItemDTOs
        List<CartItemDTO> cartItems = cart.getCartItems().stream().map(item -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setId(item.getCartItemId());
            dto.setProductId(item.getProduct().getId());
            dto.setQuantity(item.getQuantity());
            dto.setProductName(item.getProduct().getProductName());
            return dto;
        }).collect(Collectors.toList());

        cartDTO.setCartItems(cartItems);

        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(
            @RequestParam Long cartItemId
    ) {
        try {
            cartService.removeFromCart(cartItemId);
            return ResponseEntity.ok("Product removed from cart successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove product from cart: " + e.getMessage());
        }
    }

}
