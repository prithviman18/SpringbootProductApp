package com.productApp.FirstProductApp.dto;

import com.productApp.FirstProductApp.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String productName;
    private String imageUrl;
    private Double costPrice;
    private Double sellingPrice;
    private Double markedPrice;
    private Double profit;
    private Double discountPercentage;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.imageUrl = product.getImageUrl();
        this.costPrice = product.getCostPrice();
        this.sellingPrice = product.getSellingPrice();
        this.markedPrice = product.getMarkedPrice();
        this.profit = calculateProfit();
        this.discountPercentage = calculateDiscountPercentage();
    }

    // Calculate profit
    public Double calculateProfit() {
        return this.sellingPrice - this.costPrice;
    }

    // Calculate discount percentage
    public Double calculateDiscountPercentage() {
        return ((this.markedPrice - this.sellingPrice) / this.markedPrice) * 100;
    }

    public void setProfitAndDiscount() {
        this.profit = calculateProfit();
        this.discountPercentage = calculateDiscountPercentage();
    }
}
