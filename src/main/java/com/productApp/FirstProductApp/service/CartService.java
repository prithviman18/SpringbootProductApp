package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.entity.Cart;
import com.productApp.FirstProductApp.entity.CartItem;
import com.productApp.FirstProductApp.entity.Product;
import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.repository.CartReposiitory;
import com.productApp.FirstProductApp.repository.ProductRepository;
import com.productApp.FirstProductApp.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartReposiitory cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public Cart addToCart(Long userId, Long productId,int quantity){
        // Fetch the user from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the cart already exists, or create a new one if not
        Optional<Cart> cartOpt = cartRepository.findByUser_Id(userId);
        Cart cart = cartOpt.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);  // Set the user directly from the repository
            return newCart;
        });

        // Fetch the product and handle cases where the product does not exist
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create and populate the cart item
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setCart(cart);

        // Add the item to the cart
        cart.getCartItems().add(item);

        // Save the cart (this should also save the cart item due to cascading)
        return cartRepository.save(cart);
    }

    public Cart getCart(Long userId) {
        return cartRepository.findByUser_Id(userId).orElse(new Cart());
    }

    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

}
