package com.jcooldevelopment.easybank_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // This annotation will make Spring to see the beans
@EnableWebSecurity
public class SecurityConfig {

    // This @Bean makes Spring execute the method, and it will use it when necesary. This method will be executed first because Spring
    // Security configuration. With SecurityFilterChain class in this method, Spring Security will use it for http filter.
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()); // By default, if not disabled, API will return 403 status code

        return http.build();
    }
}
