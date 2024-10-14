package com.productApp.FirstProductApp.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    // Generate a secure key for HS512
    private static final SecretKey secretKeyHS256 = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final SecretKey secretKeyHS512 = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long expirationTime = 3600000; // 1 hour
    private static final long refreshExpirationMs = 86400000; // 24 hours

    // Method to generate token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKeyHS256)
                .compact();
    }

    // Generate Refresh Token using HS512
    public String generateRefreshToken(UserDetails userPrincipal) {
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshExpirationMs))
                .signWith(secretKeyHS512) // Use the secure HS512 key
                .compact();
    }

    // Validate Access Token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKeyHS256) // Validate using the key used for signing HS256 tokens
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }

    // Get Username from Token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyHS256)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract claims from token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyHS256)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate the token
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
