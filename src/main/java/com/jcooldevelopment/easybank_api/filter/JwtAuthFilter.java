package com.jcooldevelopment.easybank_api.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.jcooldevelopment.easybank_api.service.Jwt.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Custom filter which extends from OncePerRequestFilter abstract class to secure one filter execution per http request
@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    @Qualifier("handlerExceptionResolver") // It allows to indicate the specific class to inject.
    private HandlerExceptionResolver resolver;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.resolver = resolver;
    }

    // This method executes all filters related to JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String token = getTokenFromRequest(request);
        final String usercode;

        // If token is null, filterChain will take over
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Global Error Handler can´t catch exceptions in filter level, it must be handled by HandlerExceptionResolver in filter directly.
        // In other words, HandlerExceptionResolver will catch the exception with try/catch and passes to GlobalErrorHandler.
        // https://stackoverflow.com/questions/76386768/how-do-i-catch-exceptions-in-jwtauthfilter-and-handle-with-global-error-handler
        // https://keepcoding.io/blog/que-es-spring-qualifier-y-como-funciona/
        try {

            usercode = this.jwtService.getUsernameFromToken(token);

            // First we must search Username in SecurityContext. If not found, it must found in database.
            if (usercode != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(usercode); // Search user in database 

                // Validate JWT. If success, it must be saved in SecurityContextHolder
                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities()
                    );
                    
                    // Set details on authentication token
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Save authenticationToken in SecurityContextHolder
                }
            }

            filterChain.doFilter(request, response);
        } catch (SignatureException exception) {
            resolver.resolveException(request, response, null, exception);
        } catch (ExpiredJwtException exception) {
            resolver.resolveException(request, response, null, exception);
        }
  
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
