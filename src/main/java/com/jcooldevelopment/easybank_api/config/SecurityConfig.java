package com.jcooldevelopment.easybank_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jcooldevelopment.easybank_api.filter.JwtAuthFilter;

@Configuration // This annotation will make Spring to see the beans
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authProvider = authProvider;
    }

    // This @Bean makes Spring execute the method, and it will use it when necesary. This method will be executed first because Spring
    // Security configuration. With SecurityFilterChain class in this method, Spring Security will use it for http filter.
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()); // By default, if not disabled, API will return 403 status code if there is no CSRF token in requests

        return http
            .authorizeHttpRequests(authRequest -> // Lambda expression for more than one configuration for authorize requests
                authRequest
                    .requestMatchers(HttpMethod.POST, 
                        "/api/message",
                        "/api/auth/**",
                        "/api/activate/**"
                    ).permitAll() // This routes will be public
                    
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                    ).permitAll()

                    .requestMatchers(HttpMethod.GET,
                        "/error"
                    ).permitAll()

                    .anyRequest().authenticated() // The rest of requests must be authenticated
            )
            .sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ) // Disables sessions
            .authenticationProvider(authProvider)
            .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Executes the filter when a request is received
            .build();
            // JWT for authentication not authorization
    }
}
