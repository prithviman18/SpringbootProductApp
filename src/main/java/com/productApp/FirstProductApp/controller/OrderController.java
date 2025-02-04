package com.productApp.FirstProductApp.controller;

import com.productApp.FirstProductApp.dto.ApiResponse;
import com.productApp.FirstProductApp.dto.ErrorResponse;
import com.productApp.FirstProductApp.dto.OrderDTO;
import com.productApp.FirstProductApp.entity.Order;
import com.productApp.FirstProductApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestParam Long userId){
        try {
            Order order = orderService.checkout(userId);
            return ResponseEntity.ok("Order placed successfully! Order ID: " + order.getOrderId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to place order: " + e.getMessage());
        }
    }

    @PostMapping("/buyNow")
    public ResponseEntity<String> buyNow(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity){
        try {
            Order order = orderService.buyNow(userId, productId, quantity);
            return ResponseEntity.ok("Successfully bought "+order.getOrderItems().get(0).getQuantity()+""+order.getOrderItems().get(0).getProduct().getProductName());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to buy the product: " + e.getMessage());
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getOrders(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, orders, null));
    }

   @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable Long orderId){
//        try {
//            OrderDTO order = orderService.getOrderById(orderId);
//            if (order != null) {
//                return ResponseEntity.ok(new ApiResponse<>(true, order, null));
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ApiResponse<>(false, null, new ErrorResponse(404, "Order not found")));
//            }
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(false,null, new ErrorResponse(500,"Unable to find the order")));
//        }
       OrderDTO order = orderService.getOrderById(orderId);
       return ResponseEntity.ok(new ApiResponse<>(true,order,null));
   }
}
