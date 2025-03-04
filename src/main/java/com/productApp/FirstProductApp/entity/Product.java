package com.productApp.FirstProductApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String imageUrl;
    private Double costPrice;
    private Double sellingPrice;
    private Double markedPrice;
    private String category;
    private String brand;
    @Column(nullable = true)
    private String gender;

    public Product(Long id){
        this.id = id;
    }

}
