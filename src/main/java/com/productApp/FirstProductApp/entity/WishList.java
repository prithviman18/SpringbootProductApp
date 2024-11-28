package com.productApp.FirstProductApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class WishList {
    @Id
    @GeneratedValue
    private Long wishlistId;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false) // Foreign key column for User
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false) // Foreign key column for Product
    private Product product;

    private LocalDateTime createdAt = LocalDateTime.now();

    public WishList(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public WishList() {
    }
}
