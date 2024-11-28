package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.dto.OrderDTO;
import com.productApp.FirstProductApp.dto.ProductDTO;
import com.productApp.FirstProductApp.entity.*;
import com.productApp.FirstProductApp.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;

    public Order buyNow(Long userId, Long productId, int quantity){
        Order order = new Order();
        order.setUser(new User(userId));
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setProduct(new Product(productId));
        item.setQuantity(quantity);
        orderItems.add(item);
        order.setOrderItems(orderItems);
        order.setStatus("Ordered");
        return orderRepository.save(order);
    }
    public Order checkout(Long userId){
        Cart cart = cartService.getCart(userId);
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("Ordered");
        // Create a list to hold OrderItems
        List<OrderItem> orderItems = new ArrayList<>();
        // Loop through cart items and create corresponding order items
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setOrder(order); // Associate OrderItem with Order
            orderItems.add(item); // Add to OrderItem list
        }
        order.setOrderItems(orderItems);

        return orderRepository.save(order);

    }

    public List<OrderDTO> getOrders(Long userId) {
        List<Order> orders = orderRepository.findByUser_Id(userId);

        if (orders.isEmpty()) {
            throw new EntityNotFoundException("No orders found for user with ID: " + userId);
        }

        return orders.stream().map(order -> {
            User user = order.getUser();

            // Map products via orderItems
            List<ProductDTO> productDTOs = order.getOrderItems().stream()
                    .map(orderItem -> new ProductDTO(orderItem.getProduct())) // Assuming OrderItem has getProduct()
                    .collect(Collectors.toList());

            return new OrderDTO(
                    order.getOrderId(),
                    order.getOrderDateTime().toString(),
                    user.getId(),
                    user.getUsername(),
                    productDTOs
            );
        }).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }

        User user = order.getUser();

        // Map each Product entity to ProductDTO
        List<ProductDTO> productDTOs = order.getOrderItems().stream()
                .map(orderItem -> new ProductDTO(orderItem.getProduct())) // Using your existing ProductDTO constructor
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getOrderId(),
                order.getOrderDateTime().toString(),
                user.getId(),
                user.getUsername(),
                productDTOs
        );
    }

    }
