package com.example.mobile_ecommerce.middlewares;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtAuthFilter extends HttpFilter {

    private final SecretKey secretKey;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    public JwtAuthFilter(@Value("${jwt.secret}") String secret) {
        // Use URL-safe Base64 decoder to handle characters like "-" and "_"
        this.secretKey = Keys.hmacShaKeyFor(Base64.getUrlDecoder().decode(secret));
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // String uri = request.getRequestURI();

        // Skip JWT validation for /register and /login routes
        // if (uri.startsWith("/api/auth/register") || uri.startsWith("/api/auth/login") || uri.startsWith("/api/products/**")) {
        //     chain.doFilter(request, response);  // Continue the filter chain
        //     return;
        // }

        String jwt = getJwtFromHeader(request);

        if (jwt != null && validateJwtToken(jwt)) {
            chain.doFilter(request, response);  // Continue the filter chain if JWT is valid
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing JWT token");
        }
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Extract the JWT token from Authorization header
        }
        return null;
    }

    private boolean validateJwtToken(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);  // Parse the JWT and validate

            // Optionally, check for token expiration (can be uncommented if needed)
            // Claims claims = Jwts.parserBuilder()
            // .setSigningKey(secretKey)
            // .build()
            // .parseClaimsJws(jwt)
            // .getBody();
            // return !claims.getExpiration().before(new Date());

            return true;
        } catch (Exception e) {
            logger.warn("JWT validation failed: " + e.getMessage(), e);
            return false;
        }
    }
}
