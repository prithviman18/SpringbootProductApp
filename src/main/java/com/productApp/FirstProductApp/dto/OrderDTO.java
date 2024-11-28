package com.productApp.FirstProductApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private String orderDate;
    private Long userId;
    private String userName;
    private List<ProductDTO> products;
}
