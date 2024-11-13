package com.example.mobile_ecommerce.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.mobile_ecommerce.model.User;

import java.util.Date;

@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-time}")
    private long jwtExpirationTime;

    /**
     * Generate JWT token for the authenticated user.
     */
    public String generateToken(User user) {
        // Ensure the secret key is properly decoded
        var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

        return Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
            .signWith(secretKey)
            .compact();
    }
}
