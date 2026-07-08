package com.jcooldevelopment.easybank_api.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Custom filter which extends from OncePerRequestFilter abstract class to secure one filter execution per http request
@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    // This method executes all filters related to JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String token = getTokenFromRequest(request);

        // If token is null, filterChain will take over
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    // It retrieves JWT from request header
    private String getTokenFromRequest(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        // In this case, StringUtils is better than String.isBlank or ternary operator (Ternary in java does not allow '??' and isBlank may
        // throw an error for a null value)
        // Auth Header from frontend must be like "Bearer {JWT_TOKEN}"
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }
    
}
