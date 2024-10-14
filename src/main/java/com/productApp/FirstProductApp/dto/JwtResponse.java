package com.productApp.FirstProductApp.dto;

import lombok.Data;

@Data
public class JwtResponse {

    private String token;
    private String tokenType = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }
}
