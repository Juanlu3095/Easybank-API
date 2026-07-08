package com.jcooldevelopment.easybank_api.config;

import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Encode password to save in database
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication manager defines how Spring Security filters execute authentication. This authentication is set in
    // SecurityContextHolder. Authentication manager is the one which delegates the authentication and returns the result
    // to Spring Security Filters
    @Bean // Since it has Bean annotation, Spring will inject the necessary parameter   
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) { // config allows access to instance
        return config.getAuthenticationManager();
    }

    // The provider service for authentication, it validates credentials. It knows JWT, OAuth2, etc.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider retrieves the user details from a simple, read-only user DAO, the UserDetailsService.
        // This User Details Service only has access to the username in order to retrieve the full user entity.
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailService() {
        // Lambda expressions returns a function, in this case a method returning UserDetails. Since UserDetailsService is an interface
        // which has a method loadUserByUsername with UserDetails as return, this lambda implements that by return User which implements
        // UserDetails. With a lambda expression the name of the method to implement is not necessary, only the returning value. Also,
        // the interface must have only one method to implement, because this function only returns a function directly.
        return usercode -> this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

}
